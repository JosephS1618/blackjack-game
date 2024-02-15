package model;

import model.Card;
import model.Deck;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DeckTest {
    private Deck deck1;

    @BeforeEach
    void runBefore(){
        deck1 = new Deck(1);
    }

    @Test
    void makeClassicDeckFirstCardTest() {
        assertEquals(11, deck1.getDeck().get(0).getNumber());
        assertEquals("A", deck1.getDeck().get(0).getSymbol());
        assertEquals('D', deck1.getDeck().get(0).getSuit());
    }

    @Test
    void makeClassicDeckLastDiamondTest() {
        assertEquals(10, deck1.getDeck().get(12).getNumber());
        assertEquals("K", deck1.getDeck().get(12).getSymbol());
        assertEquals('D', deck1.getDeck().get(12).getSuit());
    }

    @Test
    void makeClassicDeckEveryAceTest() {
        assertEquals(11, deck1.getDeck().get(0).getNumber());
        assertEquals("A", deck1.getDeck().get(0).getSymbol());
        assertEquals('D', deck1.getDeck().get(0).getSuit());

        assertEquals(11, deck1.getDeck().get(13).getNumber());
        assertEquals("A", deck1.getDeck().get(13).getSymbol());
        assertEquals('C', deck1.getDeck().get(13).getSuit());

        assertEquals(11, deck1.getDeck().get(26).getNumber());
        assertEquals("A", deck1.getDeck().get(26).getSymbol());
        assertEquals('H', deck1.getDeck().get(26).getSuit());

        assertEquals(11, deck1.getDeck().get(39).getNumber());
        assertEquals("A", deck1.getDeck().get(39).getSymbol());
        assertEquals('S', deck1.getDeck().get(39).getSuit());
    }

    @Test
    void makeClassicDeckEveryDiamondTest() {
        assertEquals(11, deck1.getDeck().get(0).getNumber());
        assertEquals("A", deck1.getDeck().get(0).getSymbol());
        assertEquals('D', deck1.getDeck().get(0).getSuit());

        assertEquals(2, deck1.getDeck().get(1).getNumber());
        assertEquals("2", deck1.getDeck().get(1).getSymbol());
        assertEquals('D', deck1.getDeck().get(1).getSuit());

        assertEquals(3, deck1.getDeck().get(2).getNumber());
        assertEquals("3", deck1.getDeck().get(2).getSymbol());
        assertEquals('D', deck1.getDeck().get(2).getSuit());

        assertEquals(4, deck1.getDeck().get(3).getNumber());
        assertEquals("4", deck1.getDeck().get(3).getSymbol());
        assertEquals('D', deck1.getDeck().get(3).getSuit());
    }

    @Test
    void deckSizeClassicTest() {
        assertEquals(52, deck1.deckSize());
    }
}