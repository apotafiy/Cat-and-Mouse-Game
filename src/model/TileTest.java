package model;

import org.junit.jupiter.api.Test;
import view.GameFrame;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class TileTest {
    GameFrame gameFrame = new GameFrame(new File("maze_1.txt"));

    @Test
    void getTileType() {
        Tile t = new Tile();
        assertEquals(Tile.TileType.PLAIN, t.getTileType());
        t.setTileType(Tile.TileType.TUNNEL);
        assertEquals(Tile.TileType.TUNNEL, t.getTileType());
    }

    @Test
    void setTileType() {
        Tile t = new Tile();
        assertEquals(Tile.TileType.PLAIN, t.getTileType());
        t.setTileType(Tile.TileType.TUNNEL);
        assertEquals(Tile.TileType.TUNNEL, t.getTileType());
        t.setTileType(Tile.TileType.PLAIN);
        assertEquals(Tile.TileType.PLAIN, t.getTileType());
    }

    @Test
    void setBoardPiece() {
        Tile t = new Tile();
        t.setBoardPiece(new Cheese(1, 1));
        assertEquals(true, t.hasCheese());
        t.setBoardPiece(new Mouse(gameFrame, 1, 2));
        assertEquals(false, t.hasCheese());
        t.setBoardPiece(new Cheese(3, 9));
        assertEquals(true, t.hasCheese());
    }

    @Test
    void hasMouse() {
        Tile t = new Tile();
        t.setBoardPiece(new Mouse(gameFrame, 5, 2));
        assertEquals(false, t.hasCheese());
        t.setBoardPiece(new Mouse(gameFrame, 2, 9));
        assertEquals(true, t.hasMouse());
        t.setBoardPiece(new Mouse(gameFrame, 6, 2));
        assertEquals(true, t.hasMouse());
    }

    @Test
    void hasCheese() {
        Tile t = new Tile();
        t.setBoardPiece(new Cheese(5, 2));
        assertEquals(true, t.hasCheese());
        t.setBoardPiece(new Mouse(gameFrame, 2, 9));
        assertEquals(false, t.hasCheese());
        t.setBoardPiece(new Cheese(6, 2));
        assertEquals(true, t.hasCheese());
    }

    @Test
    void isEmpty() {
        Tile t = new Tile();
        assertEquals(true, t.isEmpty());
        t.setBoardPiece(new Cheese(1, 1));
        assertEquals(false, t.isEmpty());
    }
}