package model;

import javax.swing.*;
import java.awt.*;

/**
 * Cheese that will be in maze
 */
public class Cheese extends JPanel {
    private final int col;
    private final int row;

    public Cheese(int col, int row){
        this.col = col;
        this.row = row;
        setLayout(new GridLayout());
        JLabel label = new JLabel();
        ImageIcon imageIcon = new ImageIcon("cheese.png");
        Image img1 = imageIcon.getImage();
        Image img2 = img1.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon im = new ImageIcon(img2);
        label.setIcon(im);
        add(label);
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }
}
