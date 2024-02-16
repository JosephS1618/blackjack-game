package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LogTest {
    private Log log1;
    private Log log2;
    private Log log3;

    @BeforeEach
    void runBefore() {
        log1 = new Log(true,false, 100,10);
        log2 = new Log(false,true, 500,100);
        log3 = new Log(true,true, 200,20);
    }

    @Test
    void winLossStatusWinTest() {
        assertEquals("Win", log1.winLossStatus());
    }

    @Test
    void winLossStatusLossTest() {
        assertEquals("Lose", log2.winLossStatus());
    }

    @Test
    void winLossStatusTieTest() {
        assertEquals("Tie", log3.winLossStatus());
    }
}
