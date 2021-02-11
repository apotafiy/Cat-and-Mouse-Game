package model;

import view.GameFrame;

import javax.swing.*;
import java.awt.*;

/**
 * Cat_4 moves horizontally always
 */
public class Cat_4 extends Entity {
    private boolean isOnCheese = false;

    public Cat_4(GameFrame gameFrame, int col, int row) {
        super(gameFrame, col, row);
        setLayout(new GridLayout());
        JLabel label = new JLabel();
        ImageIcon imageIcon = new ImageIcon("cat4.jpg");
        Image img1 = imageIcon.getImage();
        Image img2 = img1.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon im = new ImageIcon(img2);
        label.setIcon(im);
        add(label);
        setCardinal(Cardinal.WEST);
    }

    /**
     * Says whether a move is valid*.
     * *If mouse is present, it will be considered valid,
     * but cat should only take mouse life, and not be allowed to move.
     *
     * @param cardinal direction
     * @return whether space is valid
     */
    @Override
    public boolean isValidSpace(Cardinal cardinal) {// TODO: 12/18/2020 if space has cheese, then it is valid
        try {
            switch (cardinal) {
                case EAST: {
                    return (getBoard()[getRow()][getCol() + 1].isEmpty() || isMouse(cardinal) || isCheese(cardinal)) && getBoard()[getRow()][getCol() + 1].getTileType() != Tile.TileType.TUNNEL;
                }
                case WEST: {
                    return (getBoard()[getRow()][getCol() - 1].isEmpty() || isMouse(cardinal) || isCheese(cardinal)) && getBoard()[getRow()][getCol() - 1].getTileType() != Tile.TileType.TUNNEL;
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
        if (!isValidSpace(cardinal) && cardinal == Cardinal.WEST) {
            setCardinal(Cardinal.EAST);
            cardinal = Cardinal.EAST;
        } else if (!isValidSpace(cardinal)) {
            setCardinal(Cardinal.WEST);
            cardinal = Cardinal.WEST;
        }
        if (isMouse(cardinal)) {// if finds mouse, deals with it
            getLifeCounter().removeLife();
            try {
                Thread.sleep(500); // gives mouse a chance to run away
            } catch (Exception e) {
                System.out.println("Cat_$: Thread Issue.");
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
                case EAST: {
                    if (isCheese(cardinal)) isOnCheese = true;
                    getBoard()[getRow()][getCol() + 1].setBoardPiece(this);
                    setCol(getCol() + 1);
                    break;
                }
                case WEST: {
                    if (isCheese(cardinal)) isOnCheese = true;
                    getBoard()[getRow()][getCol() - 1].setBoardPiece(this);
                    setCol(getCol() - 1);
                    break;
                }
            }
        }

    }
}
