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
    public void testOutcomeStandoffStand() {
        g.setUpNewGame(2);
        g.setStand(true);
        g.playGame(20, 20);
        assertEquals("tie", g.getOutcome());
    }

    @Test
    public void testOutcomeStandoffTie() {
        g.setUpNewGame(2);
        g.playGame(21, 21);
        assertEquals("tie", g.getOutcome());
    }

    @Test
    public void testOutcomePlayerWinsBlackJack() {
        g.setUpNewGame(2);
        g.playGame(21, 18);
        assertEquals("win", g.getOutcome());
        assertTrue(g.isBlackjack());
    }

    @Test
    public void testOutcomePlayerWinsLarger() {
        g.setUpNewGame(2);
        g.setStand(true);
        g.playGame(19, 18);
        assertEquals("win", g.getOutcome());
    }

    @Test
    public void testOutcomePlayerWins() {
        g.setUpNewGame(1);
        g.setStand(true);
        g.playGame(20, 18);
        assertEquals("win", g.getOutcome());
    }

    @Test
    public void testOutcomePlayerLoses() {
        g.setUpNewGame(2);
        g.setStand(true);
        g.playGame(19, 20);
        assertEquals("lose", g.getOutcome());
    }

    @Test
    public void testOutcomePlayerLosesBust() {
        g.setUpNewGame(2);
        g.playGame(22, 20);
        assertEquals("lose", g.getOutcome());
    }

}
