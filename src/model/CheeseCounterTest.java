package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class CheeseCounterTest {

    @Test
    void getCheeses() {
        CheeseCounter c = new CheeseCounter();
        assertEquals(3, c.getCheeses());
        c.removeCheese();
        assertEquals(2, c.getCheeses());
        c.removeCheese();
        assertEquals(1, c.getCheeses());
    }

    @Test
    void removeCheese() {
        CheeseCounter c = new CheeseCounter();
        assertEquals(3, c.getCheeses());
        c.removeCheese();
        assertEquals(2, c.getCheeses());
        c.removeCheese();
        assertEquals(1, c.getCheeses());
        c.removeCheese();
        assertEquals(0, c.getCheeses());
    }
}