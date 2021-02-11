package model;

import javax.swing.*;
import java.awt.*;

/**
 * Black border piece
 */
public class Border extends JPanel {
    public Border() {
        setBackground(new Color(50, 50, 50));
        setBorder(BorderFactory.createBevelBorder(1));
    }
}
