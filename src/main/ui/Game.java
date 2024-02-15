package ui;

import model.Deck;

import java.util.Collections;

//Controls aspects of the game, including creating new decks, keeping track of cash, and keeping game logs/stats.
public class Game {
    private Deck newDeck;

    //Constructor
    //Effects: Creates a new game with a new randomized deck based on selection 1,2
    public Game(int input) {
        newDeck = new Deck(input);
        shuffle();
    }

    //FOR TESTING PURPOSES ONLY

    public void printDeck() {
        String symbol;
        char suit;
        for (int i = 0; i < newDeck.cardDeckSize(); i++) {
            symbol = newDeck.getCardDeck().get(i).getSymbol();
            suit = newDeck.getCardDeck().get(i).getSuit();
            System.out.println(symbol + ", " + suit);
        }
    }

    //REQUIRES: the deck is not empty
    //MODIFIES: this
    //EFFECTS: Shuffles the deck in random order.
    private void shuffle() {
        Collections.shuffle(newDeck.getCardDeck());
    }



}
