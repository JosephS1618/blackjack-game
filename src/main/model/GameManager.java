package model;

import org.json.JSONObject;
import persistence.Writable;
import ui.MessagePrinter;

// Contains all the methods related to running game functionality.
public class GameManager extends MessagePrinter implements Writable {
    private static final int STARTING_CASH = 500; //starting cash for a new game

    private Deck gameDeck;
    private Player player;
    private Dealer dealer;

    private int cash = STARTING_CASH;
    private int bettingAmount;

    private String outcome; // "win" "lose" "tie"
    private boolean blackjack;
    private Boolean stand; //false = don't stand. true = stand
    private Boolean play;

    //REQUIRES: select either 1 or 2
    //EFFECTS: sets up objects required for a new game. also sets up a new deck based on input 1 or 2.
    // (1) classic deck or (2) party deck. sets up starting variables play, stand, and bettingAmount.
    public void setUpNewGame(int select) {
        gameDeck = new Deck();
        player = new Player();
        dealer = new Dealer();
        stand = false;
        bettingAmount = 0;
        blackjack = false;
        play = true;
        outcome = null;

        if (select == 1) {
            gameDeck.makeClassicDeck();
        } else if (select == 2) {
            gameDeck.makePartyDeck();
        }

        gameDeck.shuffle(); // shuffles the deck
        firstDeal(); // gives player and dealer two cards each
        EventLog.getInstance().logEvent(new Event("Started a new game"));
    }

    //MODIFIES: this, playerCard, dealerCard
    //EFFECTS: draws two cards each for the player and the dealer.
    // Alternates adding the first card to playerCards and dealerCards twice. Removes
    // the added cards from gameDeck after each add.
    public void firstDeal() {
        for (int i = 0; i < 2; i++) {
            player.moveCardToHand(gameDeck);
            dealer.moveCardToHand(gameDeck);
        }
    }

    //REQUIRES: select either 1 or 2
    //MODIFIES: this, Deck cardDeck, Player playerCards, Dealer dealerCards.
    //EFFECTS: sets up the game, controls game logic (calling playGame),
    // controls bets (makeBet), outputs win, loss, tie. Prints dealer and player hands
    // in printHands and printEndHand.
    public void runGame() {
        printHands(player, dealer); // prints your entire hand and the first card of the dealer
        playGame(player.playerSum(), dealer.dealerSum()); // main game logic
    }

    //REQUIRES: playSum and dealSum > 0 (game must have started and hands must have been dealt)
    //MODIFIES: this
    //EFFECTS: main logic for the game. determines if a player wins, loses, or ties. takes in user input for
    // hitting (drawing cards) or standing (stop and calls dealerPlay).
    public void playGame(int playSum, int dealSum) {
        if (stand && playSum == dealSum) { // stand and tie
            outcomeStandoff();
        } else if (playSum == 21 && dealSum == 21) { // 21 and tie
            outcomeStandoff();
        } else if (playSum == 21) { // win and blackjack
            outcomePlayerWins("Blackjack!");
            blackjack = true;
        } else if (playSum > 21) { // lose and player busts
            outcomePlayerLoses("Player busts.");
        } else if (dealSum > 21) { // win and dealer busts
            outcomePlayerWins("Dealer busts!");
        } else if (stand && (playSum < dealSum)) { // stand and dealer is closer to 21
            outcomePlayerLoses("Dealer closer to 21.");
        } else if (stand) { // stand and player is closer to 21 (second part is true regardless)
            outcomePlayerWins("You're closer to 21!");
        }
    }

    //EFFECTS: keeps stand false. moves card from gameDeck to players hand.
    public void hit() {
        player.moveCardToHand(gameDeck);
    }

    //EFFECTS: sets stand to true. begins dealer play.
    public void stand() {
        stand = true;
        dealer.play(gameDeck);
    }

    //REQUIRES: boolean blackjack is not null.
    //MODIFIES: this
    //EFFECTS: prints out a win statement. returns game wins.
    // if win and blackjack, adds 3:2 (bettingAmount * 2.5) amount.
    // otherwise match bet 1:1 (bettingAmount * 2) and adds to cash.
    // play is stopped (false), win is added, and gameLog win added.
    private void outcomePlayerWins(String description) {
        print(description + " Player wins!");
        if (blackjack) {
            cash += bettingAmount * 2.5;
        } else {
            cash += bettingAmount * 2;
        }
        outcome = "win";
        endGame();
    }

    //MODIFIES: this
    //EFFECTS: prints out a losing statement. cash and bets are not changed.
    //play is stopped (false), loss is added and gameLog loss is added
    // with negative bettingAmount difference added.
    private void outcomePlayerLoses(String description) {
        print(description + " Dealer wins.");
        outcome = "lose";
        endGame();
    }

    //MODIFIES: this
    //EFFECTS: bets are returned in full to cash total.
    // (no change in cash before bet and after game)
    //play is stopped (false), and gameLog tie is added.
    private void outcomeStandoff() {
        String message = "Standoff! Bets are returned.";
        print(message);
        cash += bettingAmount;
        outcome = "tie";
        endGame();
    }

    //MODIFIES: this
    //EFFECTS: removes the betting amount from the cash.
    public void removeBetFromCash(int amount) {
        cash -= amount;
    }

    //MODIFIES: this
    //EFFECTS: ends the game for the play and prints the hand of the dealer
    private void endGame() {
        play = false;
        printEndHand(dealer); // prints the dealers entire hand.
        EventLog.getInstance().logEvent(new Event("Game ended"));
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("cash", cash);
        json.put("bettingAmount", bettingAmount);
        json.put("play", play);
        json.put("stand", stand);
        return json;
    }

    public String getOutcome() {
        return outcome;
    }

    public int getCash() {
        return cash;
    }

    public void setCash(int cash) {
        this.cash = cash;
    }

    public int getBettingAmount() {
        return bettingAmount;
    }

    public void setBettingAmount(int bettingAmount) {
        this.bettingAmount = bettingAmount;
    }

    public boolean isBlackjack() {
        return blackjack;
    }

    public Boolean getPlay() {
        return play;
    }

    public void setPlay(Boolean play) {
        this.play = play;
    }

    public Boolean getStand() {
        return stand;
    }

    public Player getPlayer() {
        return player;
    }

    public Dealer getDealer() {
        return dealer;
    }

    public Deck getGameDeck() {
        return gameDeck;
    }

    public void setStand(Boolean stand) {
        this.stand = stand;
    }
}
