package controller;

import model.*;
import view.GameFrame;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class GameInfo {
    private int col;
    private int row;
    private Mouse mouse;
    private Cheese cheese1;
    private Cheese cheese2;
    private Cheese cheese3;
    private Cat_1 cat1;
    private Cat_2 cat2;
    private Cat_3 cat3;
    private Cat_4 cat4;
    private Cat_5 cat5;
    final StringBuilder boardArrangement = new StringBuilder();

    public GameInfo(GameFrame gameFrame,File inputFile) {

        //inputFile = new File("maze_1.txt");
        Scanner s;
        try {
            s = new Scanner(inputFile);
            // getting dimensions for board
            String[] dimensionTemp = s.nextLine().trim().split("\\s+");
            String dimensionString = dimensionTemp[0] + dimensionTemp[1];
            String[] dimensions = dimensionString.split(",");
            String columns = dimensions[0];
            String rows = dimensions[1];
            col = Integer.parseInt(columns);
            row = Integer.parseInt(rows);

            // getting actual board layout/arrangement
            for (int i = 0; i < row; i++) {
                boardArrangement.append(s.nextLine() + "\n");
            }

            //getting mouse
            String[] mouseDetails = s.nextLine().trim().split("\\s+");
            columns = mouseDetails[1];
            rows = mouseDetails[2];
            mouse = new Mouse(gameFrame, Integer.parseInt(columns), Integer.parseInt(rows)); // gameFrame initialized later

            // getting cheeses
            String[] cheese = s.nextLine().trim().split("\\s+");
            cheese1 = new Cheese(Integer.parseInt(cheese[1]), Integer.parseInt(cheese[2]));

            cheese = s.nextLine().trim().split("\\s+");
            cheese2 = new Cheese(Integer.parseInt(cheese[1]), Integer.parseInt(cheese[2]));

            cheese = s.nextLine().trim().split("\\s+");
            cheese3 = new Cheese(Integer.parseInt(cheese[1]), Integer.parseInt(cheese[2]));


            // getting cats
            String[] cat = s.nextLine().trim().split("\\s+");
            cat1 = new Cat_1(gameFrame, Integer.parseInt(cat[1]), Integer.parseInt(cat[2]));

            cat = s.nextLine().trim().split("\\s+");
            cat2 = new Cat_2(gameFrame, Integer.parseInt(cat[1]), Integer.parseInt(cat[2]));

            cat = s.nextLine().trim().split("\\s+");
            cat3 = new Cat_3(gameFrame, Integer.parseInt(cat[1]), Integer.parseInt(cat[2]));

            cat = s.nextLine().trim().split("\\s+");
            cat4 = new Cat_4(gameFrame, Integer.parseInt(cat[1]), Integer.parseInt(cat[2]));

            cat = s.nextLine().trim().split("\\s+");
            cat5 = new Cat_5(gameFrame, Integer.parseInt(cat[1]), Integer.parseInt(cat[2]), mouse);


        } catch (FileNotFoundException ignored) {
        }
    }

    /**
     * Lets user choose file for input
     * @return input fie
     */
    private File chooseFile() {
        Scanner scan = new Scanner(System.in);

        File inputFile = null;
        while (inputFile == null) {
            System.out.println("Please choose a file.");
            int response;
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Text File", "txt");
            JFileChooser fileChooser = new JFileChooser(".");
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.setFileFilter(filter);

            response = fileChooser.showOpenDialog(null);

            if (response == JFileChooser.APPROVE_OPTION) {
                inputFile = fileChooser.getSelectedFile();
                try {
                    if (!inputFile.isFile()) {
                        System.out.println("Please try again:");
                    }
                } catch (Exception ex) {
                    System.out.println("An error occurred.");
                }
            }
        }
        return inputFile;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    public Mouse getMouse() {
        return mouse;
    }

    public Cheese getCheese1() {
        return cheese1;
    }

    public Cheese getCheese2() {
        return cheese2;
    }

    public Cheese getCheese3() {
        return cheese3;
    }

    public Cat_1 getCat1() {
        return cat1;
    }

    public Cat_2 getCat2() {
        return cat2;
    }

    public Cat_3 getCat3() {
        return cat3;
    }

    public Cat_4 getCat4() {
        return cat4;
    }

    public Cat_5 getCat5() {
        return cat5;
    }

    public StringBuilder getBoardArrangement() {
        return boardArrangement;
    }
}
