package model;

import org.json.JSONObject;

// Specifications for a single card in a deck. Contains three values, an integer number and
// a String representing its symbol, and a char representing its suit
public class Card {
    // Ace is worth 1 or 11 but is instantiated as 11, face worth 10, rest of cards are their respective numbers.
    private int number; // {1-11}
    private final String symbol; // {A,[2-10],J,Q,K}
    private final char suit; // D (diamonds), C (clubs), H (hearts), S (spades).

    //EFFECTS: creates a new card with the specified number and symbol
    public Card(int number, String symbol, char suit) {
        this.number = number;
        this.symbol = symbol;
        this.suit = suit;
    }

    //GETTERS and SETTERS
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getSymbol() {
        return symbol;
    }

    public char getSuit() {
        return suit;
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("number", number);
        json.put("symbol", symbol);
        json.put("suit", suit);
        return json;
    }
}
