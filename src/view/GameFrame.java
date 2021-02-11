package view;

import javax.swing.*;

import controller.*;
import model.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.Scanner;

public class GameFrame extends JFrame implements KeyListener {
    public final JPanel northMenu;
    public final JPanel southMenu;
    public GameMap centerGameMap;
    public Tile[][] board;
    public final CheeseCounter cheeseCounter = new CheeseCounter();
    public final LifeCounter lifeCounter = new LifeCounter();
    public Mouse mouse;
    public Cat_1 cat_1;
    public Cat_2 cat_2;
    public Cat_3 cat_3;
    public Cat_4 cat_4;
    public Cat_5 cat_5;
    private final JButton right = new JButton("RIGHT");
    private final JButton down = new JButton("DOWN");
    private final JButton up = new JButton("UP");
    private final JButton left = new JButton("LEFT");
    private int fps = 3;
    private int elapsedTime = 0;
    final Timer gameRunner = new Timer(1000 / (fps * 3), e -> doStep());
    final JLabel clock = new JLabel("Time 00:00");
    final Timer clockTimer = new Timer(1000, e -> {
        elapsedTime += 1000;
        int minutes = (elapsedTime / 60000) % 60;
        int seconds = (elapsedTime / 1000) % 60;
        String min = String.format("%02d", minutes);
        String sec = String.format("%02d", seconds);
        clock.setText("Time " + min + ":" + sec);
    });
    final JSlider frameSlide = new JSlider(1, 10, 2);
    private int stepCount = 3; // starts at 3 so that all cats move on first step

    public GameFrame(File inputFile) {
        setTitle("Mortal Combat X");

        frameSlide.setFocusable(false);

        up.setFocusable(false);
        down.setFocusable(false);
        left.setFocusable(false);
        right.setFocusable(false);

        JButton start = new JButton("Start");
        start.setFocusable(false);
        JButton stop = new JButton("Stop");
        stop.setFocusable(false);
        JButton step = new JButton("Step");
        step.setFocusable(false);
        JButton reset = new JButton("Reset");
        reset.setFocusable(false);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        northMenu = new JPanel();
        northMenu.add(clock);


        northMenu.add(start);
        northMenu.add(stop);
        northMenu.add(step);
        northMenu.add(reset);


        northMenu.add(lifeCounter);
        northMenu.add(cheeseCounter);


        southMenu = new JPanel();


        frameSlide.setPaintTicks(true);
        frameSlide.setMinorTickSpacing(1);
        frameSlide.setPaintTrack(true);
        frameSlide.setMajorTickSpacing(3);
        frameSlide.setPaintLabels(true);
        frameSlide.addChangeListener(e -> {
            fps = frameSlide.getValue() * 3;
            gameRunner.setDelay(1000 / fps);
        });

        southMenu.add(new JLabel("FPS: "));
        southMenu.add(frameSlide);

        southMenu.add(up);
        southMenu.add(down);
        southMenu.add(left);
        southMenu.add(right);

        // adding movement buttons
        {
            right.addActionListener(e -> {
                mouse.move(Entity.Cardinal.EAST);
                centerGameMap.repaint();
                checkWinOrLose();
                repaint();
            });
            down.addActionListener(e -> {
                mouse.move(Entity.Cardinal.SOUTH);
                centerGameMap.repaint();
                checkWinOrLose();
                repaint();
            });
            up.addActionListener(e -> {
                mouse.move(Entity.Cardinal.NORTH);
                centerGameMap.repaint();
                checkWinOrLose();
                repaint();
            });
            left.addActionListener(e -> {
                mouse.move(Entity.Cardinal.WEST);
                centerGameMap.repaint();
                checkWinOrLose();
                repaint();
            });
        }

        var gameInfo = new GameInfo(this, inputFile);
        populateMap(gameInfo);

        add(northMenu, BorderLayout.NORTH);
        add(centerGameMap, BorderLayout.CENTER);
        add(southMenu, BorderLayout.SOUTH);

        addKeyListener(this);
        setFocusable(true);
        requestFocusInWindow();
        pack();
        setLocationRelativeTo(null);
        setResizable(false);

        {
            start.addActionListener(e -> {
                clockTimer.start();
                gameRunner.start();
            });
            stop.addActionListener(e -> {
                gameRunner.stop();
                clockTimer.stop();
            });
            step.addActionListener(e -> doStep());
            reset.addActionListener(e -> {
                dispose();
                new Choose();
            });
        }

        setVisible(true);

        clockTimer.start();
        gameRunner.start();
    }

    /**
     * Does one iteration of movement
     */
    private void doStep() {
        if (stepCount % 3 == 0) {
            cat_1.move(cat_1.getCardinal());
            cat_2.move(cat_2.getCardinal());
            cat_3.move(cat_3.getCardinal());
            cat_4.move(cat_4.getCardinal());
        }
        cat_5.move(cat_5.getCardinal());
        checkWinOrLose();
        repaint();
        stepCount++;
    }

    /**
     * Checks end-game conditions
     */
    private void checkWinOrLose() {
        if (cheeseCounter.getCheeses() <= 0) {
            JPanel gameWin = new JPanel();
            gameWin.setLayout(new GridLayout());
            gameWin.setBackground(Color.GREEN);
            JLabel label = new JLabel("WINNER!");
            label.setFont(new Font("IDK", Font.BOLD, 100));
            gameWin.add(label);
            remove(centerGameMap);
            add(gameWin, BorderLayout.CENTER);
            gameRunner.stop();
            clockTimer.stop();
        } else if (lifeCounter.getLives() <= 0) {
            JPanel gameOver = new JPanel();
            gameOver.setLayout(new GridLayout());
            gameOver.setBackground(Color.RED);
            JLabel label = new JLabel("GAME OVER!");
            label.setFont(new Font("IDK", Font.BOLD, 100));
            gameOver.add(label);
            remove(centerGameMap);
            add(gameOver, BorderLayout.CENTER);
            gameRunner.stop();
            clockTimer.stop();
        }
    }

    /**
     * Places Mouse, Cheese, Cats, and does other stuff
     *
     * @param gameInfo Object that contains info about game
     */
    private void populateMap(GameInfo gameInfo) {
        board = new Tile[gameInfo.getRow()][gameInfo.getCol()];
        centerGameMap = new GameMap(this);

        // add tiles
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j] = new Tile();
                //board[i][j].add(new JLabel(""+i+", "+j));
                centerGameMap.add(board[i][j]);
            }
        }

        // create and add mouse
        mouse = gameInfo.getMouse();
        mouse.setBoard(board);
        board[mouse.getRow()][mouse.getCol()].setBoardPiece(mouse);

        // add terrain
        Scanner scan = new Scanner(gameInfo.getBoardArrangement().toString());
        //noinspection ForLoopReplaceableByForEach
        for (int i = 0; i < board.length; i++) {
            char[] chars = scan.nextLine().toLowerCase().trim().toCharArray();
            for (int j = 0; j < board[0].length; j++) {
                switch (chars[j]) {
                    case 'b': {
                        board[i][j].setBoardPiece(new Border());
                        break;
                    }
                    case 'g': {
                        board[i][j].setBoardPiece(new Bush());
                        break;
                    }
                    case 'h': {
                        board[i][j].setTileType(Tile.TileType.TUNNEL);
                        break;
                    }
                    default:
                }
            }
        }

        //add cheese
        board[gameInfo.getCheese1().getRow()][gameInfo.getCheese1().getCol()].setBoardPiece(gameInfo.getCheese1());
        board[gameInfo.getCheese2().getRow()][gameInfo.getCheese2().getCol()].setBoardPiece(gameInfo.getCheese2());
        board[gameInfo.getCheese3().getRow()][gameInfo.getCheese3().getCol()].setBoardPiece(gameInfo.getCheese3());

        // add cats
        // cat_1 = new Cat_1(this, 1, 2);
        cat_1 = gameInfo.getCat1();
        cat_1.setBoard(board);
        board[cat_1.getRow()][cat_1.getCol()].setBoardPiece(cat_1);


        //cat_2 = new Cat_2(this, 7, 2);
        cat_2 = gameInfo.getCat2();
        cat_2.setBoard(board);
        board[cat_2.getRow()][cat_2.getCol()].setBoardPiece(cat_2);

        cat_3 = gameInfo.getCat3();
        board[cat_3.getRow()][cat_3.getCol()].setBoardPiece(cat_3);
        cat_3.setBoard(board);

        cat_4 = gameInfo.getCat4();
        board[cat_4.getRow()][cat_4.getCol()].setBoardPiece(cat_4);
        cat_4.setBoard(board);

        cat_5 = gameInfo.getCat5();
        board[cat_5.getRow()][cat_5.getCol()].setBoardPiece(cat_5);
        cat_5.setBoard(board);

    }


    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case 38: {
                up.doClick();
                break;
            }
            case 40: {
                down.doClick();
                break;
            }
            case 37: {
                left.doClick();
                break;
            }
            case 39: {
                right.doClick();
                break;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }


}
