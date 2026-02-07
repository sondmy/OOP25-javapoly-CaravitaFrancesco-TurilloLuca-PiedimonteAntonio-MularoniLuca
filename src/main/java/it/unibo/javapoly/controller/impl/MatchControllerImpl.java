package it.unibo.javapoly.controller.impl;

import java.util.List;
import java.util.Objects;
import it.unibo.javapoly.controller.api.MatchController;
import it.unibo.javapoly.model.api.Player;
import it.unibo.javapoly.model.api.board.Board;
import it.unibo.javapoly.model.api.board.Tile;
import it.unibo.javapoly.model.api.economy.Bank;
import it.unibo.javapoly.model.impl.DiceImpl;
import it.unibo.javapoly.model.impl.DiceThrow;
import it.unibo.javapoly.model.impl.JailedState;
import it.unibo.javapoly.model.impl.PlayerImpl;
import it.unibo.javapoly.view.impl.MainView;

/**
 * MatchControllerImpl manages the flow of the game, including turns, 
 * movement, and GUI updates.
 */
public class MatchControllerImpl implements MatchController{

    private static final int MAX_DOUBLES = 3;

    private final List<PlayerImpl> players;  
    private final DiceThrow diceThrow;
    private final Board gameBoard;
    private final Bank bank;     
    private final MainView gui;

    private int currentPlayerIndex;
    private int consecutiveDoubles;

    private boolean hasRolled = false;

    /**
     * Constructor
     *
     * @param players   list of players (already created)
     * @param gameBoard the game board implementation
     * @param bank      the bank implementation
     */
    public MatchControllerImpl(final List<PlayerImpl> players, final Board gameBoard, final Bank bank){
        this.players = List.copyOf(players); //perche' la lista e' gia stata creata, cosi' la passo semplicemente
        this.currentPlayerIndex = 0;
        this.consecutiveDoubles = 0;
        this.diceThrow = new DiceThrow(new DiceImpl(), new DiceImpl());
        this.gameBoard = Objects.requireNonNull(gameBoard);
        this.bank = Objects.requireNonNull(bank);
        this.gui = new MainView(this);
    }

    //for test
    public MatchControllerImpl(final List<PlayerImpl> players, final Board gameBoard, final Bank bank, final MainView gui) {
        this.players = List.copyOf(players);
        this.gameBoard = Objects.requireNonNull(gameBoard);
        this.bank = Objects.requireNonNull(bank);
        this.diceThrow = new DiceThrow(new DiceImpl(), new DiceImpl());
        this.gui = gui; 
    }

    /**
     * Starts the game.
     * Updates GUI and announces the first player.
     */
    @Override
    public void startGame() {
        updateGui(g -> {
            g.addLog("Partita avviata");
            g.updateBoard();
            g.updateInfo();
        });

        final Player firstPlayer = getCurrentPlayer();
        gui.addLog("E' il turno di: " + firstPlayer.getName());
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
            g.updateInfo();
            g.refreshPagination();
        });
    }

    /**
     * Returns the current player whose turn it is.
     */
    @Override
    public PlayerImpl getCurrentPlayer() {
        return this.players.get(this.currentPlayerIndex);
    }

    /**
     * Handles the logic when the current player throws the dice.
     */
    @Override
    public void handleDiceThrow() {
        if(this.hasRolled){
            return;
        }

        final PlayerImpl currentPlayer = getCurrentPlayer();
        final int steps = diceThrow.throwAll();
        final boolean isDouble = diceThrow.isDouble();

        updateGui(g -> {
            g.addLog("Il giocatore: " + currentPlayer.getName() + " lancia i dadi: " + steps + (isDouble ? " (DOPPIO!)" : ""));
        });
        
        if(isDouble){
            this.consecutiveDoubles++;
            if(this.consecutiveDoubles == MAX_DOUBLES){
                updateGui(g -> g.addLog("3 doppi! Vai dritto in prigione."));
                handlePrison(); 
                this.hasRolled = true;
                return;
            }
        }else {
            this.consecutiveDoubles = 0;
            this.hasRolled = true;
        }

        int oldPos = currentPlayer.getCurrentPosition();
        currentPlayer.playTurn(steps, isDouble);
        int newPos = currentPlayer.getCurrentPosition();

        if (newPos < oldPos && !currentPlayer.getState().getClass().getSimpleName().contains("Jailed")) {
            bank.deposit(currentPlayer, 200);
            updateGui(g -> g.addLog("Passato dal VIA! +200$"));
        }

        handlePropertyLanding();

        updateGui(g -> {
            g.updateBoard();
            g.updateInfo();
            g.refreshPagination();
        });
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
        int oldPosition = currentPlayer.getCurrentPosition();

        currentPlayer.move(steps);
        int newPosition = currentPlayer.getCurrentPosition();


        if (newPosition < oldPosition) {
            bank.deposit(currentPlayer, 200);
            if (gui != null) {
                gui.addLog("Passato dal VIA! +200$");
            }
        }

        updateGui(g -> {
            if(newPosition < oldPosition){
                g.addLog("Passato dal VIA! +200$");
            }
            g.addLog(currentPlayer.getName() + " si e' spostato di " + steps + " spazi");
            g.updateBoard();
            g.updateInfo();
        });

        handlePropertyLanding();
    }

    /**
     * Handles the logic when a player is in prison.
     * For simplicity, we can just log it here.
     */
    @Override
    public void handlePrison() {
        final Player currentPlayer = getCurrentPlayer();
        // sposta il player sulla casella 10 (Prigione)
        // move() di PlayerImpl fa il modulo 40, quindi calcoliamo la distanza
        int dist = (10 + 40 - currentPlayer.getCurrentPosition()) % 40;
        currentPlayer.move(dist);

        currentPlayer.setState(new JailedState());
        
        updateGui(g -> g.addLog(currentPlayer.getName() + " è ora in GALERA."));
        updateGui(g -> g.updateBoard());
    }

    /**
     * Handles actions when a player lands on a property.
     * For now, just logs the event.
     */
    @Override
    public void handlePropertyLanding() {
        final Player currentPlayer = getCurrentPlayer();
        final Tile currentTile = gameBoard.getTileAt(currentPlayer.getCurrentPosition());

        if (currentTile.getType() == it.unibo.javapoly.model.api.board.TileType.TAX) {
            updateGui(g -> g.addLog("Tassa pagata!"));
        }
        if (currentTile.getType() == it.unibo.javapoly.model.api.board.TileType.GO_TO_JAIL) {
            handlePrison();
        }
    }

    /**
     * Returns the game board.
     */
    public Board getBoard() {
        return this.gameBoard;
    }

    //descrizione da inserire
    public boolean canCurrentPlayerRoll() {
        return !hasRolled;
    }

    @Override
    public MainView getMainView() {
        return this.gui;
    }

    private void updateGui(java.util.function.Consumer<it.unibo.javapoly.view.impl.MainView> action) {
        if (this.gui != null) {
            action.accept(this.gui);
        }
    }
}
