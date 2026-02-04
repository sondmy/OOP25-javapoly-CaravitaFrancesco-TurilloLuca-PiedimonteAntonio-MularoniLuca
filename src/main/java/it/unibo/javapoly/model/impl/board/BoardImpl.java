package it.unibo.javapoly.model.impl.board;

import java.util.List;

import it.unibo.javapoly.model.api.board.Board;
import it.unibo.javapoly.model.api.board.Tile;

public class BoardImpl implements Board{

    private final List<Tile> tiles;

    public BoardImpl(List<Tile> tiles) {
        this.tiles = List.copyOf(tiles); 
    }

    @Override
    public int size() {
        return tiles.size();
    }

    @Override
    public Tile getTileAt(int position) {
        return tiles.get(normalizePosition(position));
    }

    @Override
    public int normalizePosition(int position) {
        int size = size();
        return ((position % size) + size) % size;
    }
    
}
