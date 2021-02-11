package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LifeCounterTest {

    @Test
    void getLives() {
        LifeCounter l = new LifeCounter();
        assertEquals(3, l.getLives());
        l.removeLife();
        assertEquals(2, l.getLives());
        l.removeLife();
        assertEquals(1, l.getLives());
    }

    @Test
    void removeLife() {
        LifeCounter l = new LifeCounter();
        assertEquals(3, l.getLives());
        l.removeLife();
        assertEquals(2, l.getLives());
        l.removeLife();
        assertEquals(1, l.getLives());
        l.removeLife();
        assertEquals(0, l.getLives());
    }
}