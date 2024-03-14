package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DealerTest {
    private Dealer dealer1;
    private Card ace1;
    private Card ace2;
    private Card ten;
    private Card five;
    private Deck testDeck1;

    @BeforeEach
    void runBefore() {
        dealer1 = new Dealer();

        ace1 = new Card(11, "A", "D");
        ace2 = new Card(11, "A", "S");
        ten = new Card(10, "10", "C");
        five = new Card(5, "5", "D");

        testDeck1 = new Deck();
        testDeck1.addCard(ace1);
        testDeck1.addCard(ace2);
        testDeck1.addCard(ten);
        testDeck1.addCard(five);
    }

    @Test
    void dealerSumOneCardTest() {
        dealer1.addCard(ace1);
        assertEquals(11, dealer1.dealerSum());
    }

    @Test
    void dealerSumTwoCardTest() {
        dealer1.addCard(ace1);
        assertEquals(11, dealer1.dealerSum());
        dealer1.addCard(five);
        assertEquals(16, dealer1.dealerSum());
    }

    @Test
    void dealerSumTwentyOneTest() {
        dealer1.addCard(ace1);
        assertEquals(11, dealer1.dealerSum());
        dealer1.addCard(ten);
        assertEquals(21, dealer1.dealerSum());
    }

    @Test
    void dealerSumAceOver21Test() {
        dealer1.addCard(ace1);
        assertEquals(11, dealer1.dealerSum());
        dealer1.addCard(five);
        assertEquals(16, dealer1.dealerSum());
        dealer1.addCard(ten);
        assertEquals(16, dealer1.dealerSum()); //10+5+1

        assertEquals(1, dealer1.getDealerCards().get(0).getNumber());
    }

    @Test
    void dealerSumTwoAceOver21Test() {
        dealer1.addCard(ace1);
        assertEquals(11, dealer1.dealerSum());
        dealer1.addCard(five);
        assertEquals(16, dealer1.dealerSum());
        dealer1.addCard(ten);
        assertEquals(16, dealer1.dealerSum()); //10+5+1

        assertEquals(1, dealer1.getDealerCards().get(0).getNumber());

        dealer1.addCard(ace2);
        assertEquals(17, dealer1.dealerSum());
    }

    @Test
    void findAceExistsTest() {
        dealer1.addCard(ace1);
        assertEquals(ace1, dealer1.findAce());
    }

    @Test
    void findAceDoesNotExist() {
        dealer1.addCard(five);
        assertEquals(null, dealer1.findAce());
    }

    @Test
    void getCardSymbolTest() {
        dealer1.addCard(ace1);
        assertEquals("A", dealer1.getCardSymbol(0));
    }

    @Test
    void getCardSymbolMultipleTest() {
        dealer1.addCard(ace1);
        dealer1.addCard(five);
        assertEquals("A", dealer1.getCardSymbol(0));
        assertEquals("5", dealer1.getCardSymbol(1));
    }

    @Test
    void getCardValueTest() {
        dealer1.addCard(ace1);
        dealer1.addCard(five);
        assertEquals(11, dealer1.getCardValue(0));
        assertEquals(5, dealer1.getCardValue(1));
    }

    @Test
    void dealerPlayTest() {
        assertEquals(0, dealer1.dealerSum());
        dealer1.play(testDeck1);
        assertEquals(17, dealer1.dealerSum());
    }

    @Test
    void moveCardToHandTest() {
        dealer1.moveCardToHand(testDeck1);
        assertTrue(dealer1.getDealerCards().contains(ace1));
        assertFalse(testDeck1.getCardDeck().contains(ace1));
    }

}
