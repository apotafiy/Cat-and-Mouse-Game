package view;

import model.Border;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.jar.JarFile;

public class Choose extends JFrame {
    public Choose() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JButton maze_1 = new JButton("Maze 1");
        JButton maze_2 = new JButton("Maze 2");
        File one = new File("maze_1.txt");
        File two = new File("maze_2.txt");

        maze_1.addActionListener(e -> {
            new GameFrame(one);
        });
        maze_2.addActionListener(e -> {
            new GameFrame(two);
        });

        add(maze_1, BorderLayout.NORTH);
        add(maze_2, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Choose();
    }
}
