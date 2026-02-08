package it.unibo.javapoly.view.impl;

import java.util.Objects;
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
        });
        this.root.getChildren().addAll(this.throwDice, this.payJailButton, this.endTurnButton);

        updateState();
    }

    public void updateState(){
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
}
