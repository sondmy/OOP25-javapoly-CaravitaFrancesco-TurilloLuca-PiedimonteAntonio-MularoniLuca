package it.unibo.javapoly.view.impl;

import java.util.Objects;

import it.unibo.javapoly.model.api.board.Board;
import it.unibo.javapoly.model.api.board.Tile;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * BoardPanel handles the visual representation of the board.
 */
public class BoardPanel {

    private final GridPane root;
    private final Board board;

    public BoardPanel(final Board board){
        this.board = Objects.requireNonNull(board);
        this.root = new GridPane();

        this.root.setStyle("-fx-background-color: #CDE6D0; -fx-padding: 5; -fx-border-color: black;");
        this.root.setAlignment(Pos.CENTER);
        this.root.setGridLinesVisible(false);

        this.renderBoard();
    }

    private VBox createTileUI(final Tile tile, final int index){
        final VBox box = new VBox();
        box.setMinSize(100, 50);
        box.setStyle("-fx-border-color: black; -fx-background-color: white;");
        box.setAlignment(Pos.CENTER);

        if(tile != null){
            Label nameLabel = new Label(tile.getName());
            nameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 10px;");

            // 2. Per aggiungere la fascia colorata, in caso di una proprieta'
            // if (tile instanceof PropertyTile) { ... }
            box.getChildren().add(nameLabel);
        }else{
            box.getChildren().add(new Label("Errore" + index));
        }
        return box;
    }

    private int calculateX(int i){
        if(i <= 10) return 10 - i;
        if(i <= 20) return 0;
        if(i <= 30) return i - 20;
        return 10;
    }

    private int calculateY(int i){
        if(i <= 10) return 10;
        if(i <= 20) return 10 - (i - 10);
        if(i <= 30) return 0;
        return i - 30;
    }

    private void renderBoard(){
        this.root.getChildren().clear();
        this.root.getRowConstraints().clear();
        this.root.getColumnConstraints().clear();

        // Definiamo 11 colonne e 11 righe identiche (quadrate)
        for (int i = 0; i < 11; i++) {
            javafx.scene.layout.ColumnConstraints col = new javafx.scene.layout.ColumnConstraints();
            col.setPercentWidth(100.0 / 11); // Ogni colonna è 1/11 del totale
            
            javafx.scene.layout.RowConstraints row = new javafx.scene.layout.RowConstraints();
            row.setPercentHeight(100.0 / 11); // Ogni riga è 1/11 del totale
            
            this.root.getColumnConstraints().add(col);
            this.root.getRowConstraints().add(row);
        }

        final int size = board.size();

        for(int i = 0; i < size; i++){
            final Tile tile = board.getTileAt(i);
            final VBox tileUI = createTileUI(tile, i);

            // Espandi la VBox per riempire tutta la cella del GridPane
            tileUI.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

            final int x = calculateX(i);
            final int y = calculateY(i);

            this.root.add(tileUI, x, y);
        }
    }

    /** @return the visual root of the board. */
    public Pane getRoot() {
        return this.root;
    }

    /** Updates the view based on current model state. */
    public void update() {
        renderBoard();
    }
}
