package it.unibo.javapoly.controller.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

import it.unibo.javapoly.controller.api.EconomyController;
import it.unibo.javapoly.controller.api.MatchController;
import it.unibo.javapoly.controller.api.PropertyController;
import it.unibo.javapoly.model.api.Player;
import it.unibo.javapoly.model.api.PlayerState;
import it.unibo.javapoly.model.api.board.Board;
import it.unibo.javapoly.model.api.board.Tile;
import it.unibo.javapoly.model.api.board.TileType;
import it.unibo.javapoly.model.api.property.Property;
import it.unibo.javapoly.model.impl.DiceImpl;
import it.unibo.javapoly.model.impl.DiceThrow;
import it.unibo.javapoly.model.impl.FreeState;
import it.unibo.javapoly.model.impl.JailedState;
import it.unibo.javapoly.model.impl.board.BoardImpl;
import it.unibo.javapoly.view.impl.MainView;

/**
 * MatchControllerImpl manages the flow of the game, including turns, 
 * movement, and GUI updates.
 */
public class MatchControllerImpl implements MatchController {

    private static final int MAX_DOUBLES = 3;
    private static final int GO_SALARY = 200;
    private static final int JAIL_EXIT_FEE = 50;
    
    private final List<Player> players;  
    private final DiceThrow diceThrow;
    private final Board gameBoard;    
    private final MainView gui;
    private final Map<Player, Integer> jailTurnCounter = new HashMap<>();

    private final EconomyController economyController;
    private final PropertyController propertyController;

    private int currentPlayerIndex;
    private int consecutiveDoubles;
    private int lastDiceResult;
    private boolean hasRolled = false;

    /**
     * Constructor
     *
     * @param players   list of players (already created)
     * @param gameBoard the game board implementation
     * @param bank      the bank implementation
     */
    public MatchControllerImpl(final List<Player> players, final Board gameBoard, final EconomyController economyController, final PropertyController propertyController){
        this.players = List.copyOf(players);
        this.gameBoard = Objects.requireNonNull(gameBoard);
        this.economyController = Objects.requireNonNull(economyController);
        this.propertyController = Objects.requireNonNull(propertyController);
        this.diceThrow = new DiceThrow(new DiceImpl(), new DiceImpl());
        this.gui = new MainView(this);
        this.currentPlayerIndex = 0;
        this.consecutiveDoubles = 0;

        for (Player p : this.players) {
            p.addObserver(this); 
        }
    }

    public MatchControllerImpl(final List<Player> players){
        this(players, new BoardImpl(new ArrayList<>()), new EconomyControllerImpl(), new PropertyControllerImpl(new HashMap<>()));
    }

    /**
     * Starts the game.
     * Updates GUI and announces the first player.
     */
    @Override
    public void startGame() {
        updateGui(g -> {
            g.addLog("Partita avviata");
            g.refreshAll();
            g.addLog("E' il turno di: " + getCurrentPlayer().getName());
        });
    }

    /**
     * Advances to the next player's turn.
     * Updates GUI and logs new turn.
     */
    @Override
    public void nextTurn() {
        this.currentPlayerIndex = (this.currentPlayerIndex + 1) % this.players.size();
        this.hasRolled = false;
        this.consecutiveDoubles = 0;

        final Player current = getCurrentPlayer();

        updateGui(g -> {
            g.addLog("Ora e' il turno di: " + current.getName());
            g.refreshAll();
        });
    }

    /**
     * Handles the logic when the current player throws the dice.
     */
    @Override
    public void handleDiceThrow() {
        if(this.hasRolled){
            return;
        }

        final Player currentPlayer = getCurrentPlayer();
        this.lastDiceResult = diceThrow.throwAll();
        final boolean isDouble = diceThrow.isDouble();

        if(currentPlayer.getState() instanceof JailedState){
            int turns = jailTurnCounter.getOrDefault(currentPlayer, 0);
            if(isDouble){
                updateGui(g -> g.addLog(currentPlayer.getName() + " esce col DOPPIO (" + this.lastDiceResult + ")!"));
                currentPlayer.setState(FreeState.getInstance());
                jailTurnCounter.remove(currentPlayer);
            }else if(turns >= 2){
                updateGui(g -> g.addLog(currentPlayer.getName() + " fallisce il 3° tentativo. Paga 50€ ed esce!"));
                economyController.withdrawFromPlayer(currentPlayer, JAIL_EXIT_FEE);
                currentPlayer.setState(FreeState.getInstance());
                jailTurnCounter.remove(currentPlayer);
            }else{
                jailTurnCounter.put(currentPlayer, turns + 1);
                updateGui(g -> g.addLog(currentPlayer.getName() + " resta in prigione (Tentativo " + (turns + 1) + "/3)"));
                this.hasRolled = true;
                return;
                }
            }

        updateGui(g -> g.addLog(currentPlayer.getName() + " lancia: " + this.lastDiceResult + (isDouble ? " (DOPPIO!)" : "")));
        
        if(isDouble && !(currentPlayer.getState() instanceof JailedState)){
            this.consecutiveDoubles++;
            if(this.consecutiveDoubles == MAX_DOUBLES){
                updateGui(g -> g.addLog("3 doppi! In prigione."));
                handlePrison(); 
                this.hasRolled = true;
                return;
            }
        } else {
            this.consecutiveDoubles = 0;
            this.hasRolled = true;
        }

        int potentialPos = gameBoard.normalizePosition(currentPlayer.getCurrentPosition() + this.lastDiceResult);
        currentPlayer.playTurn(potentialPos, isDouble);
    }

    /**
     * Moves the current player by 'steps' spaces on the board.
     * Updates board and info panels.
     *
     * @param steps number of spaces to move
     */
    @Override
    public void handleMove(int steps) {
        final Player currentPlayer = getCurrentPlayer();
        int newPosition = gameBoard.normalizePosition(currentPlayer.getCurrentPosition() + steps);
        currentPlayer.move(newPosition);
    }

    /**
     * Handles the logic when a player is in prison.
     * For simplicity, we can just log it here.
     */
    @Override
    public void handlePrison() {
        final Player currentPlayer = getCurrentPlayer();
        currentPlayer.move(10);
        currentPlayer.setState(new JailedState());
    }

    /**
     * Handles actions when a player lands on a property.
     * For now, just logs the event.
     */
    @Override
    public void handlePropertyLanding() {
        final Player currentPlayer = getCurrentPlayer();
        final Tile currentTile = gameBoard.getTileAt(currentPlayer.getCurrentPosition());
        if(currentTile.getType() == TileType.PROPERTY || currentTile.getType() == TileType.RAILROAD || currentTile.getType() == TileType.UTILITY){
            Property prop = (Property) currentTile;
            if(propertyController.checkPayRent(currentPlayer, prop.getId())){
                economyController.payRent(currentPlayer, prop.getIdOwner(), prop, this.lastDiceResult);
                updateGui(g -> g.addLog(currentPlayer.getName() + " ha pagato l'affitto su " + prop.getId()));
            }
        }else if(currentTile.getType() == TileType.TAX){
            int tax = (currentPlayer.getCurrentPosition() == 4) ? 200 : 100;
            economyController.withdrawFromPlayer(currentPlayer, tax);
            updateGui(g -> g.addLog(currentPlayer.getName() + " paga tassa di " + tax + "€"));
        }else if(currentTile.getType() == TileType.GO_TO_JAIL){
            handlePrison();
        }
    }

    @Override
    public void onPlayerMoved(Player player, int oldPosition, int newPosition) {
        if(newPosition < oldPosition && newPosition != 10){
            economyController.depositToPlayer(player, GO_SALARY);
            updateGui(g -> g.addLog(player.getName() + " è passato dal VIA! +200€"));
        }
        updateGui(g -> {
            g.refreshAll();
            g.addLog(player.getName() + " si è spostato sulla casella " + gameBoard.getTileAt(newPosition).getName());
        });

        handlePropertyLanding();
    }

    @Override
    public void payToExitJail() {
        Player p = getCurrentPlayer();
        if(p.getState() instanceof JailedState && economyController.withdrawFromPlayer(p, JAIL_EXIT_FEE)){
            p.setState(FreeState.getInstance());
            jailTurnCounter.remove(p);
            updateGui(g -> {
                g.addLog(p.getName() + " paga 50€ ed è libero!");
                g.refreshAll();
            });
        }
    }

    public EconomyController getEconomyController() {
        return this.economyController;
    }

    public PropertyController getPropertyController() {
        return this.propertyController;
    }

    @Override
    public List<Player> getPlayers(){
        return this.players;
    }

    @Override
    public Player getCurrentPlayer() {
        return this.players.get(this.currentPlayerIndex);
    }

    @Override
    public Board getBoard(){
        return this.gameBoard;
    }
    @Override
    public MainView getMainView(){
        return this.gui;
    }

    public boolean canCurrentPlayerRoll(){ 
        return !hasRolled; 
    }

    private void updateGui(Consumer<MainView> action) {
        if (this.gui != null) action.accept(this.gui);
    }

    @Override
    public void onBalanceChanged(Player player, int newBalance) {
        updateGui(g -> g.refreshAll());
    }

    @Override
    public void onStateChanged(Player player, PlayerState oldState, PlayerState newState) {
        updateGui(g -> {
            g.addLog(player.getName() + " ora è in stato: " + newState.getClass().getSimpleName());
            g.refreshAll();;
        });
    }
}
