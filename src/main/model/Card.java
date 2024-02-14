package model;

// Specifications for a single card in a deck. Contains two values, an integer number and
// a char representing its symbol.
public class Card {
    // Ace is worth 1 or 11 but is instantiated as 11, face worth 10, rest of cards are their respective numbers.
    private int number; // 1-11
    private char symbol; // A,[2-10],J,Q,K
    private char suit; // D (diamonds), C (clubs), H (hearts), S (spades).

    //EFFECTS: creates a new card with the specified number and symbol
    public Card(int number, char symbol, char suit) {
        this.number = number;
        this.symbol = symbol;
        this.suit = suit;
    }

    //GETTERS
    public int getNumber() {
        return number;
    }

    public char getSymbol() {
        return symbol;
    }

    public char getSuit() {
        return suit;
    }
}
