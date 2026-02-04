package it.unibo.javapoly.controller.impl;

import it.unibo.javapoly.controller.impl.MatchControllerImpl;
import it.unibo.javapoly.view.impl.MainView;
import javafx.application.Application;
import javafx.stage.Stage;
import java.util.List;

public class JavaPoly extends Application {

    /**
     * The main entry point for all JavaFX applications.
     * @param primaryStage the primary stage for this application.
     */
    @Override
    public void start(final Stage primaryStage) {
        // 1. Crea i finti dati (o quelli veri se li hai)
        // MatchControllerImpl controller = new MatchControllerImpl(players, board, bank);
        MatchControllerImpl controllerImpl = new MatchControllerImpl(null, null, null);
        
        // 2. Crea la View passandogli il controller
        // MainView view = new MainView(controller);
        MainView view = new MainView(controllerImpl);
        
        // 3. Mostra la finestra
        // view.start(primaryStage);
        view.start(primaryStage);

        // Qui inizializzerai il controller e la view quando avrai i dati pronti
        // Per ora la lasciamo vuota o con un log per testare se parte
        System.out.println("JavaFX is running!");
    }

    /**
     * Main method to launch the application.
     * @param args command line arguments.
     */
    public static void main(final String[] args) {
        launch(args);
    }
}
