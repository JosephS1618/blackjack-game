package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestClassOrder;

import static org.junit.jupiter.api.Assertions.*;

public class GameStatManagerTest {
    private GameStatManager gs;

    @BeforeEach
    void runBefore() {
        gs = new GameStatManager();
    }

    @Test
    void addGameLog() {
        gs.addGameLog(true, false, 100, 20);
        assertEquals(1, gs.getGameLog().size());
    }

    @Test
    void testAddWins() {
        gs.addWins();
        assertEquals(1, gs.getWins());
    }

    @Test
    void testAddLoss() {
        gs.addLosses();
        assertEquals(1, gs.getLosses());
    }



}
