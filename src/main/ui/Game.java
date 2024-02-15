package ui;

import model.Dealer;
import model.Deck;
import model.Player;

import java.util.Collections;

//Controls aspects of the game, including creating new decks, keeping track of cash, and keeping game logs/stats.
public class Game {
    private static final int STARTING_CASH = 100;
    private Deck newDeck;
    private Player newPlayer;
    private Dealer newDealer;
    private int cash;

    //Constructor
    //Effects: Creates a new game with a new randomized deck based on selection 1 or 2,
    // gives starting cash of 100, and shuffles the deck.
    public Game(int input) {
        newDeck = new Deck(input);
        cash = STARTING_CASH; //starting cash
        startGame();
    }

    private void startGame() {
        shuffle();
        newPlayer = new Player();
        newDealer = new Dealer();

        //TODO ask user for cash
    }

    // MODIFIES: this
    // EFFECTS: keeps track of the
    private void gameLog() {
        //TODO
    }




    //REQUIRES: the deck is not empty
    //MODIFIES: this
    //EFFECTS: Shuffles the deck in random order.
    private void shuffle() {
        Collections.shuffle(newDeck.getCardDeck());
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

}
