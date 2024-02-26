package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardTest {

    @Test
    void cardTest() {
        Card card = new Card(11,"A", 'D');
        assertEquals(11, card.getNumber());
        assertEquals("A", card.getSymbol());
        assertEquals('D', card.getSuit());
    }
}
