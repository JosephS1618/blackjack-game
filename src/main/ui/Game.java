package ui;

import model.Card;
import model.Dealer;
import model.Deck;
import model.Player;

import java.util.Collections;

//Controls aspects of the game, including creating new decks,
// keeping track of cash, and keeping game logs/stats.
public class Game {
    private static final int STARTING_CASH = 500;
    private Deck newDeck;
    private Player player;
    private Dealer dealer;
    private int cash;
    private int bettingAmount;
    private Boolean play; //false = end game. true = continue game
    private Boolean win; //false = not win. true = win game

    //Constructor
    //Effects: Creates a new game with a new randomized deck based on selection 1 or 2,
    // gives starting cash of 100, and shuffles the deck.
    public Game() {
        cash = STARTING_CASH; //starting cash
        play = true;
        win = false;
    }

    public void startGame(int select) {
        newDeck = new Deck(select);
        player = new Player();
        dealer = new Dealer();

        shuffle();
        firstDraw();

        //TODO ask user for cash

        do {
            if (player.playerSum() == 21) {
                System.out.println("Blackjack!");
                win = true;
                play = false;
            }
        } while (play);

    }

    //REQUIRES: not an empty deck
    //EFFECTS: gets and returns the first card of the deck
    private Card getFirstCardInDeck() {
        return newDeck.getCardDeck().get(0);
    }

    //REQUIRES: not an empty deck
    //MODIFIES: deck
    //EFFECTS: removes the first card of the deck
    private void removeFirstCardInDeck() {
        newDeck.getCardDeck().remove(0);
    }

    //MODIFIES: deck
    //EFFECTS: draws two cards each for the player and the dealer.
    // prints the card drawn but hiding the dealer's second card
    private void firstDraw() {
        for (int i = 0; i < 2; i++) {
            player.addCard(getFirstCardInDeck());
            removeFirstCardInDeck();
            dealer.addCard(getFirstCardInDeck());
            removeFirstCardInDeck();
        }
        System.out.println("Your cards: ");
        System.out.println("Dealer's card: " + " and X");
    }

    // MODIFIES: this
    // EFFECTS: keeps track of the outcome of a game.
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
