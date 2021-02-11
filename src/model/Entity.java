package model;

import view.GameFrame;

import javax.swing.*;

/**
 * Cat movement: generally only move to empty Tile
 * Mouse movement: generally move to empty Tile and Tunnel
 */
public abstract class Entity extends JPanel {
    /**
     * UP, RIGHT, DOWN, LEFT
     */
    public enum Cardinal {
        NORTH, EAST, SOUTH, WEST
    }

    private int col;// position on board
    private int row;
    private Cardinal cardinal;
    private Tile[][] board;
    private final CheeseCounter cheeseCounter;
    private final LifeCounter lifeCounter;

    public Entity(GameFrame gameFrame, int col, int row) {
        this.col = col;
        this.row = row;
        this.cheeseCounter = gameFrame.cheeseCounter;
        this.lifeCounter = gameFrame.lifeCounter;
        this.board = gameFrame.board;
        int startCardinal = (int) (Math.random() * 4);
        switch (startCardinal) {
            case 2:
                setCardinal(Cardinal.EAST);
                break;
            case 3:
                setCardinal(Cardinal.SOUTH);
                break;
            case 4:
                setCardinal(Cardinal.WEST);
                break;
            default:
                setCardinal(Cardinal.NORTH);
                break;
        }

    }

    public void setCol(int col) {
        this.col = col;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setBoard(Tile[][] board) {
        this.board = board;
    }

    public void setCardinal(Cardinal cardinal) {
        this.cardinal = cardinal;
    }

    public abstract boolean isValidSpace(Cardinal cardinal);

    /**
     * Checks whether potential move is in the bounds of the board/Tile[][]
     *
     * @param direction cardinal direction of potential move
     * @return whether move is in bounds
     */
    public boolean isInBounds(Cardinal direction) {
        switch (direction) {
            case NORTH: {
                if (row == 0) return false;
                break;
            }
            case EAST: {
                if (col == board[0].length - 1) return false;
                break;
            }
            case SOUTH: {
                if (row == board.length - 1) return false;
                break;
            }
            case WEST: {
                if (col == 0) return false;
                break;
            }
            default: {
                return true;
            }
        }
        return true;
    }

    public CheeseCounter getCheeseCounter() {
        return cheeseCounter;
    }

    public LifeCounter getLifeCounter() {
        return lifeCounter;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    public Tile[][] getBoard() {
        return board;
    }

    public Cardinal getCardinal() {
        return cardinal;
    }

}
