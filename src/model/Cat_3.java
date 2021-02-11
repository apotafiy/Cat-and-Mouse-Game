package model;

import view.GameFrame;

import javax.swing.*;
import java.awt.*;

/**
 * Cat_3 moves north and south only
 */
public class Cat_3 extends Entity {
    private boolean isOnCheese = false;

    public Cat_3(GameFrame gameFrame, int col, int row) {
        super(gameFrame, col, row);
        setLayout(new GridLayout());
        JLabel label = new JLabel();
        ImageIcon imageIcon = new ImageIcon("cat3.jpg");
        Image img1 = imageIcon.getImage();
        Image img2 = img1.getScaledInstance(35, 35, Image.SCALE_SMOOTH);
        ImageIcon im = new ImageIcon(img2);
        label.setIcon(im);
        add(label);
        setCardinal(Cardinal.NORTH);
    }

    /**
     * Says whether a move is valid*.
     * *If mouse is present, it will be considered valid,
     * but cat should only take mouse life, and not be allowed to move.
     *
     * @param cardinal direction
     * @return whether move is valid
     */
    @Override
    public boolean isValidSpace(Cardinal cardinal) {// TODO: 12/18/2020 if space has cheese, then it is valid
        try {
            switch (cardinal) {
                case NORTH: {
                    return (getBoard()[getRow() - 1][getCol()].isEmpty() || isMouse(cardinal) || isCheese(cardinal)) && getBoard()[getRow() - 1][getCol()].getTileType() != Tile.TileType.TUNNEL;
                }
                case SOUTH: {
                    return (getBoard()[getRow() + 1][getCol()].isEmpty() || isMouse(cardinal) || isCheese(cardinal)) && getBoard()[getRow() + 1][getCol()].getTileType() != Tile.TileType.TUNNEL;
                }
                default: {
                    return false;
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Exception Cat_3 isValidSpace()");
            return false;
        }
    }

    /**
     * @param cardinal desired cardinal movement
     * @return whether desired move has a mouse
     */
    private boolean isMouse(Cardinal cardinal) {
        switch (cardinal) {
            case NORTH: {
                return getBoard()[getRow() - 1][getCol()].hasMouse() && getBoard()[getRow() - 1][getCol()].getTileType() != Tile.TileType.TUNNEL;
            }
            case EAST: {
                return getBoard()[getRow()][getCol() + 1].hasMouse() && getBoard()[getRow()][getCol() + 1].getTileType() != Tile.TileType.TUNNEL;
            }
            case SOUTH: {
                return getBoard()[getRow() + 1][getCol()].hasMouse() && getBoard()[getRow() + 1][getCol()].getTileType() != Tile.TileType.TUNNEL;
            }
            case WEST: {
                return getBoard()[getRow()][getCol() - 1].hasMouse() && getBoard()[getRow()][getCol() - 1].getTileType() != Tile.TileType.TUNNEL;
            }
            default: {
                return false;
            }
        }
    }

    /**
     * @param cardinal direction
     * @return whether desired move has a cheese
     */
    public boolean isCheese(Cardinal cardinal) {
        switch (cardinal) {
            case NORTH: {
                return getBoard()[getRow() - 1][getCol()].hasCheese();
            }
            case EAST: {
                return getBoard()[getRow()][getCol() + 1].hasCheese();
            }
            case SOUTH: {
                return getBoard()[getRow() + 1][getCol()].hasCheese();
            }
            case WEST: {
                return getBoard()[getRow()][getCol() - 1].hasCheese();
            }
            default: {
                return false;
            }
        }
    }

    /**
     * @param cardinal current direction
     */
    public void move(Cardinal cardinal) {
        if (!isValidSpace(cardinal) && cardinal == Cardinal.NORTH) {
            setCardinal(Cardinal.SOUTH);
            cardinal = Cardinal.SOUTH;
        } else if (!isValidSpace(cardinal)) {
            setCardinal(Cardinal.NORTH);
            cardinal = Cardinal.NORTH;
        }
        if (isMouse(cardinal)) {// if finds mouse, deals with it
            getLifeCounter().removeLife();
            try {
                Thread.sleep(500); // gives mouse a chance to run away
            } catch (Exception e) {
                System.out.println("Cat_3: Thread Issue.");
            }
            return;
        }
        if (isValidSpace(cardinal) && isInBounds(cardinal)) {
            getBoard()[getRow()][getCol()].remove(this);
            if (isOnCheese) {
                Cheese c = new Cheese(getCol(), getRow());
                getBoard()[getRow()][getCol()].setBoardPiece(c);
                isOnCheese = false;
            } else getBoard()[getRow()][getCol()].setBoardPiece(null);
            switch (cardinal) {
                case NORTH: {
                    if (isCheese(cardinal)) isOnCheese = true;
                    getBoard()[getRow() - 1][getCol()].setBoardPiece(this);
                    setRow(getRow() - 1);
                    break;
                }
                case SOUTH: {
                    if (isCheese(cardinal)) isOnCheese = true;
                    getBoard()[getRow() + 1][getCol()].setBoardPiece(this);
                    setRow(getRow() + 1);
                    break;
                }
            }
        }
    }
}
