package model;

import view.GameFrame;

import javax.swing.*;
import java.awt.*;

/**
 * This is the maze
 */
public class GameMap extends JPanel {
    public GameMap(GameFrame gameFrame){
        //setBackground(new Color(0,0,200));
        int row = gameFrame.board.length;
        int col = gameFrame.board[0].length;
        setLayout(new GridLayout(row,col));
    }
}
