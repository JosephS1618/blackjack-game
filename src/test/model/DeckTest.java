package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class DeckTest {
    private Deck deck1;
    private Deck deck2;

    @BeforeEach
    void runBefore(){
        deck1 = new Deck();
        deck2 = new Deck();
    }

    @Test
    void deckConstructorTest() {
        assertEquals(0, deck1.cardDeckSize());
    }

    @Test
    void makeClassicDeckTest() {
        assertEquals(0, deck1.cardDeckSize());
        deck1.makeClassicDeck();
        assertEquals(52, deck1.cardDeckSize());
    }

    @Test
    void makePartyDeckTest() {
        assertEquals(0, deck2.cardDeckSize());
        deck2.makePartyDeck();
        assertEquals(312, deck2.cardDeckSize());
    }

    @Test
    void makeClassicDeckFirstCardTest() {
        deck1.makeClassicDeck();
        assertEquals(11, deck1.getCardDeck().get(0).getNumber());
        assertEquals("A", deck1.getCardDeck().get(0).getSymbol());
        assertEquals("D", deck1.getCardDeck().get(0).getSuit());
    }

    @Test
    void makeClassicDeckLastDiamondTest() {
        deck1.makeClassicDeck();
        assertEquals(10, deck1.getCardDeck().get(12).getNumber());
        assertEquals("K", deck1.getCardDeck().get(12).getSymbol());
        assertEquals("D", deck1.getCardDeck().get(12).getSuit());
    }

    @Test
    void makeClassicDeckEveryAceTest() {
        deck1.makeClassicDeck();
        assertEquals(11, deck1.getCardDeck().get(0).getNumber());
        assertEquals("A", deck1.getCardDeck().get(0).getSymbol());
        assertEquals("D", deck1.getCardDeck().get(0).getSuit());

        assertEquals(11, deck1.getCardDeck().get(13).getNumber());
        assertEquals("A", deck1.getCardDeck().get(13).getSymbol());
        assertEquals("C", deck1.getCardDeck().get(13).getSuit());

        assertEquals(11, deck1.getCardDeck().get(26).getNumber());
        assertEquals("A", deck1.getCardDeck().get(26).getSymbol());
        assertEquals("H", deck1.getCardDeck().get(26).getSuit());

        assertEquals(11, deck1.getCardDeck().get(39).getNumber());
        assertEquals("A", deck1.getCardDeck().get(39).getSymbol());
        assertEquals("S", deck1.getCardDeck().get(39).getSuit());
    }

    @Test
    void makeClassicDeckEveryDiamondTest() {
        deck1.makeClassicDeck();
        assertEquals(11, deck1.getCardDeck().get(0).getNumber());
        assertEquals("A", deck1.getCardDeck().get(0).getSymbol());
        assertEquals("D", deck1.getCardDeck().get(0).getSuit());

        assertEquals(2, deck1.getCardDeck().get(1).getNumber());
        assertEquals("2", deck1.getCardDeck().get(1).getSymbol());
        assertEquals("D", deck1.getCardDeck().get(1).getSuit());

        assertEquals(3, deck1.getCardDeck().get(2).getNumber());
        assertEquals("3", deck1.getCardDeck().get(2).getSymbol());
        assertEquals("D", deck1.getCardDeck().get(2).getSuit());

        assertEquals(4, deck1.getCardDeck().get(3).getNumber());
        assertEquals("4", deck1.getCardDeck().get(3).getSymbol());
        assertEquals("D", deck1.getCardDeck().get(3).getSuit());
    }

    @Test
    void makePartyDeckFirstCardTest() {
        deck2.makePartyDeck();
        assertEquals(11, deck2.getCardDeck().get(0).getNumber());
        assertEquals("A", deck2.getCardDeck().get(0).getSymbol());
        assertEquals("D", deck2.getCardDeck().get(0).getSuit());
    }

    @Test
    void makePartyDeckEveryAceOfDiamondsTest() {
        deck2.makePartyDeck();
        assertEquals(11, deck2.getCardDeck().get(0).getNumber());
        assertEquals("A", deck2.getCardDeck().get(0).getSymbol());
        assertEquals("D", deck2.getCardDeck().get(0).getSuit());

        assertEquals(11, deck2.getCardDeck().get(52).getNumber());
        assertEquals("A", deck2.getCardDeck().get(52).getSymbol());
        assertEquals("D", deck2.getCardDeck().get(52).getSuit());

        assertEquals(11, deck2.getCardDeck().get(104).getNumber());
        assertEquals("A", deck2.getCardDeck().get(104).getSymbol());
        assertEquals("D", deck2.getCardDeck().get(104).getSuit());

        assertEquals(11, deck2.getCardDeck().get(156).getNumber());
        assertEquals("A", deck2.getCardDeck().get(156).getSymbol());
        assertEquals("D", deck2.getCardDeck().get(156).getSuit());

        assertEquals(11, deck2.getCardDeck().get(208).getNumber());
        assertEquals("A", deck2.getCardDeck().get(208).getSymbol());
        assertEquals("D", deck2.getCardDeck().get(208).getSuit());

        assertEquals(11, deck2.getCardDeck().get(260).getNumber());
        assertEquals("A", deck2.getCardDeck().get(260).getSymbol());
        assertEquals("D", deck2.getCardDeck().get(260).getSuit());
    }

    @Test
    void makeSuitAceDiamondTest() {
        deck1.makeSuit(1,1);
        assertEquals(11, deck1.getFirstCardInDeck().getNumber());
        assertEquals("A", deck1.getFirstCardInDeck().getSymbol());
        assertEquals("D", deck1.getFirstCardInDeck().getSuit());
    }

    @Test
    void makeSuitKingHeartTest() {
        deck1.makeSuit(3,13);
        assertEquals(10, deck1.getFirstCardInDeck().getNumber());
        assertEquals("K", deck1.getFirstCardInDeck().getSymbol());
        assertEquals("H", deck1.getFirstCardInDeck().getSuit());
    }

    @Test
    void makeCardAceDiamondTest() {
        deck1.makeCard("D",1);
        assertEquals(11, deck1.getFirstCardInDeck().getNumber());
        assertEquals("A", deck1.getFirstCardInDeck().getSymbol());
        assertEquals("D", deck1.getFirstCardInDeck().getSuit());
    }

    @Test
    void makeCardKingHeartTest() {
        deck1.makeCard("H",13);
        assertEquals(10, deck1.getFirstCardInDeck().getNumber());
        assertEquals("K", deck1.getFirstCardInDeck().getSymbol());
        assertEquals("H", deck1.getFirstCardInDeck().getSuit());
    }

    @Test
    void getFirstCardInDeckTest() {
        deck1.makeClassicDeck();
        assertEquals(11, deck1.getFirstCardInDeck().getNumber());
        assertEquals("A", deck1.getFirstCardInDeck().getSymbol());
        assertEquals("D", deck1.getFirstCardInDeck().getSuit());
    }

    @Test
    void getFirstCardInDeckRemoveTest() {
        deck1.makeClassicDeck();
        assertEquals(11, deck1.getFirstCardInDeck().getNumber());
        assertEquals("A", deck1.getFirstCardInDeck().getSymbol());
        assertEquals("D", deck1.getFirstCardInDeck().getSuit());
        deck1.removeFirstCardInDeck();
        assertEquals(2, deck1.getFirstCardInDeck().getNumber());
        assertEquals("2", deck1.getFirstCardInDeck().getSymbol());
        assertEquals("D", deck1.getFirstCardInDeck().getSuit());
    }

    @Test
    void makePartyDeckDifferentObjectTest() {
        deck1.makePartyDeck();
        assertNotEquals(deck1.getCardDeck().get(0), deck1.getCardDeck().get(52));
    }

}