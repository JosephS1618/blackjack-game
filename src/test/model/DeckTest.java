package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DeckTest {
    private Deck deck1;
    private Deck deck2;

    @BeforeEach
    void runBefore(){
        deck1 = new Deck(1);
        deck2 = new Deck(2);
    }

    @Test
    void deckConstructorTest() {
        assertEquals(52, deck1.cardDeckSize()); // single deck 52
        assertEquals(312, deck2.cardDeckSize()); // six deck 312
    }

    @Test
    void makeClassicDeckFirstCardTest() {
        assertEquals(11, deck1.getCardDeck().get(0).getNumber());
        assertEquals("A", deck1.getCardDeck().get(0).getSymbol());
        assertEquals('D', deck1.getCardDeck().get(0).getSuit());
    }

    @Test
    void makeClassicDeckLastDiamondTest() {
        assertEquals(10, deck1.getCardDeck().get(12).getNumber());
        assertEquals("K", deck1.getCardDeck().get(12).getSymbol());
        assertEquals('D', deck1.getCardDeck().get(12).getSuit());
    }

    @Test
    void makeClassicDeckEveryAceTest() {
        assertEquals(11, deck1.getCardDeck().get(0).getNumber());
        assertEquals("A", deck1.getCardDeck().get(0).getSymbol());
        assertEquals('D', deck1.getCardDeck().get(0).getSuit());

        assertEquals(11, deck1.getCardDeck().get(13).getNumber());
        assertEquals("A", deck1.getCardDeck().get(13).getSymbol());
        assertEquals('C', deck1.getCardDeck().get(13).getSuit());

        assertEquals(11, deck1.getCardDeck().get(26).getNumber());
        assertEquals("A", deck1.getCardDeck().get(26).getSymbol());
        assertEquals('H', deck1.getCardDeck().get(26).getSuit());

        assertEquals(11, deck1.getCardDeck().get(39).getNumber());
        assertEquals("A", deck1.getCardDeck().get(39).getSymbol());
        assertEquals('S', deck1.getCardDeck().get(39).getSuit());
    }

    @Test
    void makeClassicDeckEveryDiamondTest() {
        assertEquals(11, deck1.getCardDeck().get(0).getNumber());
        assertEquals("A", deck1.getCardDeck().get(0).getSymbol());
        assertEquals('D', deck1.getCardDeck().get(0).getSuit());

        assertEquals(2, deck1.getCardDeck().get(1).getNumber());
        assertEquals("2", deck1.getCardDeck().get(1).getSymbol());
        assertEquals('D', deck1.getCardDeck().get(1).getSuit());

        assertEquals(3, deck1.getCardDeck().get(2).getNumber());
        assertEquals("3", deck1.getCardDeck().get(2).getSymbol());
        assertEquals('D', deck1.getCardDeck().get(2).getSuit());

        assertEquals(4, deck1.getCardDeck().get(3).getNumber());
        assertEquals("4", deck1.getCardDeck().get(3).getSymbol());
        assertEquals('D', deck1.getCardDeck().get(3).getSuit());
    }

    @Test
    void deckSizeClassicTest() {
        assertEquals(52, deck1.cardDeckSize());
    }
}