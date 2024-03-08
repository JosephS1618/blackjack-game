package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerTest {
    private Player player1;
    private Card ace1;
    private Card ace2;
    private Card ten;
    private Card five;

    @BeforeEach
    void runBefore() {
        player1 = new Player();

        ace1 = new Card(11, "A", "D");
        ace2 = new Card(11, "A", "S");
        ten = new Card(10, "10", "C");
        five = new Card(5, "5", "D");
    }

    @Test
    void playerSumOneCardTest() {
        player1.addCard(ace1);
        assertEquals(11, player1.playerSum());
    }

    @Test
    void playerSumTwoCardTest() {
        player1.addCard(ace1);
        assertEquals(11, player1.playerSum());
        player1.addCard(five);
        assertEquals(16, player1.playerSum());
    }

    @Test
    void playerSumTwentyOneTest() {
        player1.addCard(ace1);
        assertEquals(11, player1.playerSum());
        player1.addCard(ten);
        assertEquals(21, player1.playerSum());
    }

    @Test
    void playerSumAceOver21Test() {
        player1.addCard(ace1);
        assertEquals(11, player1.playerSum());
        player1.addCard(five);
        assertEquals(16, player1.playerSum());
        player1.addCard(ten);
        assertEquals(16, player1.playerSum()); //10+5+1

        assertEquals(1, player1.getPlayerCards().get(0).getNumber());
    }

    @Test
    void playerSumTwoAceOver21Test() {
        player1.addCard(ace1);
        assertEquals(11, player1.playerSum());
        player1.addCard(five);
        assertEquals(16, player1.playerSum());
        player1.addCard(ten);
        assertEquals(16, player1.playerSum()); //10+5+1

        assertEquals(1, player1.getPlayerCards().get(0).getNumber());

        player1.addCard(ace2);
        assertEquals(17, player1.playerSum());
    }

    @Test
    void findAceExistsTest() {
        player1.addCard(ace1);
        assertEquals(ace1, player1.findAce());
    }

    @Test
    void findAceDoesNotExist() {
        player1.addCard(five);
        assertEquals(null, player1.findAce());
    }


}
