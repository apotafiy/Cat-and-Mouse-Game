package model;

import view.GameFrame;

import javax.swing.*;
import java.awt.*;

/**
 * Cat_2 moves in clockwise
 */
public class Cat_2 extends Entity {
    private boolean isOnCheese = false;

    public Cat_2(GameFrame gameFrame, int col, int row) {
        super(gameFrame, col, row);
        setLayout(new GridLayout());
        JLabel label = new JLabel();
        ImageIcon imageIcon = new ImageIcon("cat2.jpg");
        Image img1 = imageIcon.getImage();
        Image img2 = img1.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
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
     * @return whether space is valid
     */
    @Override
    public boolean isValidSpace(Cardinal cardinal) {// TODO: 12/18/2020 if space has cheese, then it is valid
        try {
            switch (cardinal) {
                case NORTH: {
                    return (getBoard()[getRow() - 1][getCol()].isEmpty() || isMouse(cardinal) || isCheese(cardinal)) && getBoard()[getRow() - 1][getCol()].getTileType() != Tile.TileType.TUNNEL;
                }
                case EAST: {
                    return (getBoard()[getRow()][getCol() + 1].isEmpty() || isMouse(cardinal) || isCheese(cardinal)) && getBoard()[getRow()][getCol() + 1].getTileType() != Tile.TileType.TUNNEL;
                }
                case SOUTH: {
                    return (getBoard()[getRow() + 1][getCol()].isEmpty() || isMouse(cardinal) || isCheese(cardinal)) && getBoard()[getRow() + 1][getCol()].getTileType() != Tile.TileType.TUNNEL;
                }
                case WEST: {
                    return (getBoard()[getRow()][getCol() - 1].isEmpty() || isMouse(cardinal) || isCheese(cardinal)) && getBoard()[getRow()][getCol() - 1].getTileType() != Tile.TileType.TUNNEL;
                }
                default: {
                    return true;
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    /**
     * determines the clockwise direction
     *
     * @return clockwise cardinal direction
     */
    private Cardinal clockwiseDirection(Cardinal cardinal) {
        switch (cardinal) {
            case NORTH:
                //setCardinal(Cardinal.EAST);
                return Cardinal.EAST;
            case SOUTH:
                //setCardinal(Cardinal.WEST);
                return Cardinal.WEST;
            case WEST:
                //setCardinal(Cardinal.NORTH);
                return Cardinal.NORTH;
            case EAST:
                //setCardinal(Cardinal.SOUTH);
                return Cardinal.SOUTH;
        }
        //setCardinal(Cardinal.NORTH);
        return Cardinal.NORTH;
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
    private boolean isCheese(Cardinal cardinal) {
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
        cardinal = getCardinal();
        if (!isValidSpace(cardinal)) {// if hits obstacle
            int temp = 0;
            do {
                setCardinal(clockwiseDirection(cardinal)); //changes direction for field
                cardinal = clockwiseDirection(cardinal); // assignment to new direction
            } while (!isValidSpace(cardinal) && temp++ < 3);
        } else if (isValidSpace(clockwiseDirection(cardinal))) {
            cardinal = clockwiseDirection(cardinal);
        }
        if (isMouse(cardinal)) {// if finds mouse, deals with it
            getLifeCounter().removeLife();
            try {
                Thread.sleep(500); // gives mouse a chance to run away
            } catch (Exception e) {
                System.out.println("Cat_2: Thread Issue.");
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
                case EAST: {
                    if (isCheese(cardinal)) isOnCheese = true;
                    getBoard()[getRow()][getCol() + 1].setBoardPiece(this);
                    setCol(getCol() + 1);
                    break;
                }
                case SOUTH: {
                    if (isCheese(cardinal)) isOnCheese = true;
                    getBoard()[getRow() + 1][getCol()].setBoardPiece(this);
                    setRow(getRow() + 1);
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
