package model;

import view.GameFrame;

import javax.swing.*;
import java.awt.*;

/**
 * At intersections, turns in general direction of Mouse
 */
public class Cat_5 extends Entity {
    private boolean isOnCheese = false;
    private final Mouse mouse;

    public Cat_5(GameFrame gameFrame, int col, int row, Mouse mouse) {
        super(gameFrame, col, row);
        this.mouse = mouse;
        setLayout(new GridLayout());
        JLabel label = new JLabel();
        ImageIcon imageIcon = new ImageIcon("cat5.jpg");
        Image img1 = imageIcon.getImage();
        Image img2 = img1.getScaledInstance(35, 35, Image.SCALE_SMOOTH);
        ImageIcon im = new ImageIcon(img2);
        label.setIcon(im);
        add(label);
        setCardinal(Cardinal.SOUTH);
    }

    public int getMouseCol() {
        return mouse.getCol();
    }

    public int getMouseRow() {
        return mouse.getRow();
    }

    /**
     * Says whether a move is valid*.
     * *If mouse is present, it will be considered valid,
     * but cat should only take mouse life, and not be allowed to move.
     *
     * @param cardinal direction
     * @return Says whether a move is valid
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
     * set mouse direction towards mouse
     */
    private void trackMouse() {
        if (getMouseCol() > getCol() && isValidSpace(Cardinal.EAST)) {
            setCardinal(Cardinal.EAST);
        } else if (getMouseCol() < getCol() && isValidSpace(Cardinal.WEST)) {
            setCardinal(Cardinal.WEST);
        } else if (getMouseRow() > getRow() && isValidSpace(Cardinal.SOUTH)) {
            setCardinal(Cardinal.SOUTH);
        } else if (getMouseRow() < getRow() && isValidSpace(Cardinal.NORTH)) {
            setCardinal(Cardinal.NORTH);
        }
    }

    /**
     * @return whether mouse is at intersection
     */
    private boolean isIntersection() {
        int roads = 0;
        if (isValidSpace(Cardinal.NORTH) && getCardinal() != Cardinal.SOUTH) {
            roads++;
        }
        if (isValidSpace(Cardinal.SOUTH) && getCardinal() != Cardinal.NORTH) {
            roads++;
        }
        if (isValidSpace(Cardinal.EAST) && getCardinal() != Cardinal.WEST) {
            roads++;
        }
        if (isValidSpace(Cardinal.WEST) && getCardinal() != Cardinal.EAST) {
            roads++;
        }
        return roads > 1;
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
     * chooses a random direction to go
     *
     * @return random cardinal direction
     */
    private Cardinal randomDirection() {
        switch ((int) (Math.random() * 4)) {
            case 0:
                setCardinal(Cardinal.NORTH);
                return Cardinal.NORTH;
            case 1:
                setCardinal(Cardinal.EAST);
                return Cardinal.EAST;
            case 2:
                setCardinal(Cardinal.WEST);
                return Cardinal.WEST;
        }
        setCardinal(Cardinal.SOUTH);
        return Cardinal.SOUTH;
    }

    /**
     * @param cardinal current direction
     */
    public void move(Cardinal cardinal) {
        if (isIntersection()) {
            trackMouse();
            cardinal = getCardinal();
        }
        int temp = 0;
        while (!isValidSpace(cardinal) && temp++ < 4) {
            cardinal = randomDirection();
        }
        if (isMouse(cardinal)) {// if finds mouse, deals with it
            getLifeCounter().removeLife();
            try {
                Thread.sleep(500); // gives mouse a chance to run away
            } catch (Exception e) {
                System.out.println("Cat_5: Thread Issue.");
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
