package model;

import javax.swing.*;
import java.awt.*;

/**
 * Counts and displays how much cheese is in maze
 */
public class CheeseCounter extends JPanel {
    private int cheeses = 3;
    final JLabel cheeseLabel;

    public CheeseCounter() {
        setBackground(new Color(255, 240, 0));
        cheeseLabel = new JLabel("3");

        JLabel label = new JLabel();
        ImageIcon imageIcon = new ImageIcon("cheese.png");
        Image img1 = imageIcon.getImage();
        Image img2 = img1.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon im = new ImageIcon(img2);
        label.setIcon(im);
        add(label);
        add(cheeseLabel);
    }

    public int getCheeses() {
        return cheeses;
    }

    public void removeCheese() {
        cheeses--;
        cheeseLabel.setText(cheeses + "");
    }
}
