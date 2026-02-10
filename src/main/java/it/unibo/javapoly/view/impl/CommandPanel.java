package it.unibo.javapoly.view.impl;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import it.unibo.javapoly.controller.impl.MatchControllerImpl;
import it.unibo.javapoly.controller.impl.MatchSnapshotter;
import it.unibo.javapoly.controller.impl.MatchSnapshot;
import it.unibo.javapoly.utils.JsonUtils;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import it.unibo.javapoly.controller.api.MatchController;
import it.unibo.javapoly.model.api.Player;
import it.unibo.javapoly.model.impl.JailedState;

/**
 * CommandPanel contains the buttons for player actions,
 * such as throwing the dice and ending the turn.
 */
public class CommandPanel {

    private final HBox root;
    private final MatchController matchController;
    private final Button throwDice;
    private final Button endTurnButton;
    private final Button payJailButton;
    private final Button saveButton;

    /**
     * Constructor: creates the panel and its buttons.
     *
     * @param matchController the controller that handles game logic
     */
    public CommandPanel(final MatchController matchController){
        this.matchController = Objects.requireNonNull(matchController);

        this.root = new HBox(15); 

        this.throwDice = new Button("Lancia dadi");
        this.endTurnButton = new Button("Fine turno");
        this.payJailButton = new Button("Paga 50€");
        this.saveButton = new Button("Save");

        this.payJailButton.setStyle("-fx-base: #e74c3c; -fx-text-fill: white;");

        this.throwDice.setOnAction(e -> {
            this.matchController.handleDiceThrow();
            updateState();
        });
        this.payJailButton.setOnAction(e -> {
            this.matchController.payToExitJail();
            updateState();
        });
        this.endTurnButton.setOnAction(e -> {
            this.matchController.nextTurn();
            updateState();
            saveStateGame();
        });
        this.saveButton.setOnAction(e -> {
            saveStateGame();
        });
        this.root.getChildren().addAll(this.throwDice, this.payJailButton, this.endTurnButton, this.saveButton);

        //updateState();
    }

    public void updateState() {
        Player current = matchController.getCurrentPlayer();
        boolean canRoll = matchController.canCurrentPlayerRoll();
        this.throwDice.setDisable(!canRoll);
        this.endTurnButton.setDisable(canRoll);

        boolean isJailed = current.getState() instanceof JailedState;

        this.payJailButton.setVisible(isJailed);
        this.payJailButton.setManaged(isJailed);
        this.payJailButton.setDisable(!canRoll);
    }

    /**
     * Returns the root node of this command panel.
     *
     * @return the {@link HBox} containing the action buttons.
     */
    public HBox getRoot() {
        return this.root;
    }

    /**
     * Method to save the game state on javapoly_save.json file in user directory.
     */
    public void saveStateGame() {
        try {
            final String userHome = System.getProperty("user.home");
            final Path saveDir = Paths.get(userHome);
            final Path saveFile = saveDir.resolve("javapoly_save.json");
            if (matchController instanceof MatchControllerImpl impl) {
                final MatchSnapshot snapshot = MatchSnapshotter.toSnapshot(impl);
                JsonUtils.getInstance().mapper().writeValue(saveFile.toFile(), snapshot);
            }
        } catch (final IOException ex) {
            System.err.println("Failed to save game " + ex.getMessage());
        }
    }
}
