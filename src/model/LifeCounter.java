package model;

import javax.swing.*;
import java.awt.*;

/**
 * Counts and displays how may lives mouse has
 */
public class LifeCounter extends JPanel {
    private int lives = 3;
    private final JLabel lifeLabel = new JLabel("3");

    public LifeCounter() {
        setBackground(new Color(50, 200, 0));

        JLabel mouseLabel = new JLabel();
        ImageIcon imageIcon = new ImageIcon("mouse.png");
        Image img1 = imageIcon.getImage();
        Image img2 = img1.getScaledInstance(25, 25, Image.SCALE_SMOOTH);
        ImageIcon im = new ImageIcon(img2);
        mouseLabel.setIcon(im);
        add(mouseLabel);

        add(lifeLabel);
    }

    public int getLives() {
        return lives;
    }

    /**
     * Removes lives and displays lives accordingly
     */
    public void removeLife() {
        if (lives == 1) {
            lifeLabel.setText("0");
        } else if (lives == 2) {
            setBackground(new Color(255, 0, 0));
            lifeLabel.setText("1");
        } else if (lives == 3) {
            setBackground(new Color(255, 240, 0));
            lifeLabel.setText("2");
        }
        lives--;
    }

}
