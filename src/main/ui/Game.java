package ui;

import model.Deck;

//Controls aspects of the game, including creating new decks, keeping track of cash, and keeping game logs/stats.
public class Game {
    private Deck deck;

    //Constructor
    //Effects: Creates a new game with a new randomized deck based on selection 1,2
    public Game(int input) {
        deck = new Deck(input);
    }

    public void printDeck() {
        String symbol;
        char suit;
        for (int i = 0; i < deck.deckSize(); i++) {
            symbol = deck.getDeck().get(i).getSymbol();
            suit = deck.getDeck().get(i).getSuit();
            System.out.println(symbol + ", " + suit);
        }
    }
}
