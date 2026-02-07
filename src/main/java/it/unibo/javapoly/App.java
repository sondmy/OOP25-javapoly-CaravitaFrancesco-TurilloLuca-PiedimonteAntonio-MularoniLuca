package it.unibo.javapoly;

import java.util.ArrayList;
import java.util.List;

import it.unibo.javapoly.controller.api.MatchController;
import it.unibo.javapoly.controller.impl.MatchControllerImpl;
import it.unibo.javapoly.model.api.TokenType;
import it.unibo.javapoly.model.api.board.Board;
import it.unibo.javapoly.model.api.board.Tile;
import it.unibo.javapoly.model.api.economy.Bank;
import it.unibo.javapoly.model.impl.PlayerImpl;
import it.unibo.javapoly.model.impl.board.BoardImpl;
import it.unibo.javapoly.model.impl.economy.BankImpl;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Main application entry-point's class.
 */
public final class App extends Application{

    @Override
    public void start(Stage primaryStage) {
        List<Tile> tiles = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            final int pos = i;
            // Usiamo una classe anonima per non dover creare 40 file diversi ora
            tiles.add(new it.unibo.javapoly.model.impl.board.tile.AbstractTile(pos, 
                it.unibo.javapoly.model.api.board.TileType.PROPERTY, "Casella " + pos) {
                // Classe astratta implementata al volo per il test
            });
        }
        Board board = new BoardImpl(tiles); 
        Bank bank = new BankImpl();   
        List<PlayerImpl> players = List.of(
            new PlayerImpl("Gigi", 1500, TokenType.CAR),
            new PlayerImpl("Mario", 1500, TokenType.DOG)
        );

        MatchController controller = new MatchControllerImpl(players, board, bank);
        
        controller.getMainView().start(primaryStage);


        controller.startGame(); 
    }

    public static void main(final String[] args) {
        launch(args); 
    }
}