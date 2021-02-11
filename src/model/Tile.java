package model;

import javax.swing.*;
import java.awt.*;

/**
 * Tiles/Cells will contain all board pieces
 */
public class Tile extends JPanel {
    /**
     * What type of tile it is
     */
    public enum TileType {
        PLAIN, TUNNEL
    }

    private JPanel boardPiece = null;
    private TileType tileType = TileType.PLAIN;

    public Tile() {
        setBackground(new Color(170, 170, 170));
        setLayout(new GridLayout());
        setPreferredSize(new Dimension(30, 30));

    }

    public TileType getTileType() {
        return this.tileType;
    }

    public void setTileType(TileType tileType) {
        this.tileType = tileType;
        setBackground(new Color(90, 60, 0));
    }

    /**
     * Places Mouse, Cat, Cheese, etc, in proper place
     * @param boardPiece the board piece that needs to be placed
     */
    public void setBoardPiece(JPanel boardPiece) {
        if (boardPiece == null) {
            this.boardPiece = null;
            return;
        }
        if (this.boardPiece != null) remove(this.boardPiece);
        add(boardPiece);
        this.boardPiece = boardPiece;
    }

    /**
     *
     * @return whether Tile is housing a mouse
     */
    public boolean hasMouse() {
        return boardPiece instanceof Mouse;
    }

    /**
     *
     * @return whether Tile is housing cheese
     */
    public boolean hasCheese() {
        return boardPiece instanceof Cheese;
    }

    /**
     *
     * @return whether Tile is empty
     */
    public boolean isEmpty() {
        return boardPiece == null;
    }
}
