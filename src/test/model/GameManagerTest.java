package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class GameManagerTest {
    private GameManager g;

    @BeforeEach
    void runBefore() {
        g = new GameManager();
    }

    @Test
    void testSetUpNewGame() {
        assertNull(g.getGameDeck());
        assertNull(g.getPlayer());
        assertNull(g.getDealer());

        g.setUpNewGame(1);
        assertFalse(g.getStand());
        assertTrue(g.getPlay());
        assertEquals(0, g.getBettingAmount());
        assertEquals(500, g.getCash());
        assertFalse(g.isBlackjack());
    }

    @Test
    void testFirstDeal() {
        g.setUpNewGame(1);
        assertEquals(2, g.getPlayer().getPlayerCards().size());
        assertEquals(2, g.getDealer().getDealerCards().size());
    }

    @Test
    void testOutcomeStandoffStand() {
        g.setUpNewGame(2);
        g.setStand(true);
        g.playGame(20, 20);
        assertEquals("tie", g.getOutcome());
    }

    @Test
    void testOutcomeStandoffTie() {
        g.setUpNewGame(2);
        g.playGame(21, 21);
        assertEquals("tie", g.getOutcome());
    }

    @Test
    void testOutcomePlayerWinsBlackJack() {
        g.setUpNewGame(2);
        g.playGame(21, 18);
        assertEquals("win", g.getOutcome());
        assertTrue(g.isBlackjack());
    }

    @Test
    void testOutcomePlayerWinsLarger() {
        g.setUpNewGame(2);
        g.setStand(true);
        g.playGame(19, 18);
        assertEquals("win", g.getOutcome());
    }

    @Test
    void testOutcomePlayerWins() {
        g.setUpNewGame(1);
        g.setStand(true);
        g.playGame(20, 18);
        assertEquals("win", g.getOutcome());
    }

    @Test
    void testOutcomePlayerWinsDealerBusts() {
        g.setUpNewGame(1);
        g.setStand(true);
        g.playGame(20, 22);
        assertEquals("win", g.getOutcome());
    }

    @Test
    void testOutcomePlayerLoses() {
        g.setUpNewGame(2);
        g.setStand(true);
        g.playGame(19, 20);
        assertEquals("lose", g.getOutcome());
    }

    @Test
    void testOutcomePlayerLosesBust() {
        g.setUpNewGame(2);
        g.playGame(22, 20);
        assertEquals("lose", g.getOutcome());
    }

    @Test
    void testRunGame() {
        g.setUpNewGame(1);
        g.runGame();
        assertNull(g.getOutcome());
    }

    @Test
    void testHit() {
        g.setUpNewGame(1);
        g.hit();
        assertEquals(3, g.getPlayer().getPlayerCards().size());
        assertEquals(47, g.getGameDeck().getCardDeck().size());
    }

    @Test
    void testStand() {
        g.setUpNewGame(1);
        assertFalse(g.getStand());
    }

    @Test
    void testSetPlay() {
        g.setPlay(true);
        assertTrue(g.getPlay());
    }


}
