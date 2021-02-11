package model;

import view.GameFrame;

import javax.swing.*;
import java.awt.*;

/**
 * Mouse that is controlled by user
 */
public class Mouse extends Entity {
    public Mouse(GameFrame gameFrame, int col, int row) {
        super(gameFrame, col, row);
        setLayout(new GridLayout());
        JLabel label = new JLabel();
        ImageIcon imageIcon = new ImageIcon("mouse.png");
        Image img1 = imageIcon.getImage();
        Image img2 = img1.getScaledInstance(25, 25, Image.SCALE_SMOOTH);
        ImageIcon im = new ImageIcon(img2);
        label.setIcon(im);
        add(label);
    }

    /**
     * @param cardinal direction
     * @return whether desired place is empty
     */
    @Override
    public boolean isValidSpace(Cardinal cardinal) {
        try {
            switch (cardinal) {
                case NORTH: {
                    return getBoard()[getRow() - 1][getCol()].isEmpty() || getBoard()[getRow() - 1][getCol()].hasCheese();
                }
                case EAST: {
                    return getBoard()[getRow()][getCol() + 1].isEmpty() || getBoard()[getRow()][getCol() + 1].hasCheese();
                }
                case SOUTH: {
                    return getBoard()[getRow() + 1][getCol()].isEmpty() || getBoard()[getRow() + 1][getCol()].hasCheese();
                }
                case WEST: {
                    return getBoard()[getRow()][getCol() - 1].isEmpty() || getBoard()[getRow()][getCol() - 1].hasCheese();
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
     * moves mouse if space is available
     *
     * @param cardinal direction
     */
    public void move(Cardinal cardinal) {
        if (isCheese(cardinal)) {
            getCheeseCounter().removeCheese();
        }
        if (isInBounds(cardinal) && isValidSpace(cardinal)) {
            getBoard()[getRow()][getCol()].remove(this);
            getBoard()[getRow()][getCol()].setBoardPiece(null);
            switch (cardinal) {
                case NORTH: {
                    getBoard()[getRow() - 1][getCol()].setBoardPiece(this);
                    setRow(getRow() - 1);
                    break;
                }
                case EAST: {
                    getBoard()[getRow()][getCol() + 1].setBoardPiece(this);
                    setCol(getCol() + 1);
                    break;
                }
                case SOUTH: {
                    getBoard()[getRow() + 1][getCol()].setBoardPiece(this);
                    setRow(getRow() + 1);
                    break;
                }
                case WEST: {
                    getBoard()[getRow()][getCol() - 1].setBoardPiece(this);
                    setCol(getCol() - 1);
                    break;
                }
            }
        }

    }


}
