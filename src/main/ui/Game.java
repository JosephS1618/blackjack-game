package ui;

import model.Card;
import model.Dealer;
import model.Deck;
import model.Player;

import java.util.Collections;
import java.util.Scanner;

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

    @SuppressWarnings("methodlength")
    public void startGame(int select) {
        Scanner input = new Scanner(System.in);
        newDeck = new Deck(select);
        player = new Player();
        dealer = new Dealer();
        Boolean stand = false;
        int choice;

        shuffle();
        firstDeal();
        //TODO ask user for cash bet
        do {
            printHands(stand);
            if (player.playerSum() == 21 && dealer.dealerSum() == 21) { // If there is a tie
                System.out.println("Standoff, bets are returned");
                play = false;
            } else if (player.playerSum() > 21) { // if the player busts
                System.out.println("Dealer wins!");
                play = false;
            } else if (player.playerSum() == 21) { // if the player gets a blackjack
                System.out.println("Blackjack!");
                win = true;
                play = false;
            } else if (dealer.dealerSum() > 21) { // if the dealer busts
                System.out.println("You win!");
                win = true;
                play = false;
            } else if (player.getCardSymbol(0) == player.getCardSymbol(1)) { // in case of split
                System.out.println("Split?\n1: yes\n2: no");
                play = false; //break for now
                //TODO
            } else if (stand && (player.playerSum() < dealer.dealerSum())) { // dealer is closer to 21
                System.out.println("Dealer wins!");
                play = false;
            } else if (stand && (player.playerSum() > dealer.dealerSum())) { // player is closer to 21
                System.out.println("Player wins!");
                win = true;
                play = false;
            } else {
                System.out.println("Hit (1), Stand (2)"); // either hit or stand.
                choice = input.nextInt();
                stand = hitOrStand(choice);
                if (stand) {
                    dealerPlay(); // dealer draws until over 17
                }
            }
        } while (play);

    }

    private void printHands(Boolean stand) {
        String yourHand = "";
        String dealerHand = "";
        for (Card c : player.getPlayerCards()) {
            yourHand += " " + c.getSymbol();
        }
        if (stand == true) { //doesn't show the dealer's full hands until you stand.
            for (Card c : dealer.getDealerCards()) {
                dealerHand += " " + c.getSymbol();
            }
        } else {
            dealerHand += " " + dealer.getCardSymbol(0);
        }

        System.out.println("Your cards:" + yourHand);
        System.out.println("Dealer's card:" + dealerHand);
    }


    //MODIFIES: deck
    //EFFECTS: draws two cards each for the player and the dealer.
    // prints the card drawn but hiding the dealer's second card
    private void firstDeal() {
        for (int i = 0; i < 2; i++) {
            player.addCard(newDeck.getFirstCardInDeck());
            newDeck.removeFirstCardInDeck();
            dealer.addCard(newDeck.getFirstCardInDeck());
            newDeck.removeFirstCardInDeck();
        }
    }

    //REQUIRES: choice of integer 1 or 2
    //MODIFIES: stand
    //EFFECTS: either hits or stands. if choice is 1, hit (returns false). if choice is 2, stand (returns true)
    private boolean hitOrStand(int choice) {
        if (choice == 1) {
            player.addCard(newDeck.getFirstCardInDeck());
        } else if (choice == 2) {
            return true;
        }
        return false;
    }

    private void dealerPlay() {
        while (dealer.dealerSum() < 17) {
            dealer.addCard(newDeck.getFirstCardInDeck());
            newDeck.removeFirstCardInDeck();
        }
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
