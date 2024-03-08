package ui;

import model.*;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.JsonReader;
import persistence.JsonWriter;
import persistence.Writable;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

//Controls aspects of the game, including creating new decks, starting game, game logic,
// keeping track of cash, and keeping game logs/stats.
public class Game implements Writable {
    private static final int STARTING_CASH = 500; //starting cash for a new game
    private static final String JSON_STORE = "./data/gameTest.json";

    private Deck gameDeck;
    private Player player;
    private Dealer dealer;

    private int cash;
    private int bettingAmount;
    private Boolean play; //false = end game. true = continue game
    private Boolean stand; //false = don't stand. true = stand

    private List<model.Log> gameLog;
    private double wins;
    private double losses;

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    Scanner input = new Scanner(System.in);

    //Constructor
    //EFFECTS: assigns STARTING_CASH to cash, creates a new empty list of gameLogs.
    public Game() throws FileNotFoundException {
        cash = STARTING_CASH;
        gameLog = new ArrayList<>();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runInput();

    }

    //REQUIRES: select either 1 or 2
    //MODIFIES: this, Deck cardDeck, Player playerCards, Dealer dealerCards.
    //EFFECTS: sets up the game, controls game logic (calling playGame),
    // controls bets (makeBet), outputs win, loss, tie. Prints dealer and player hands
    // in printHands and printEndHand.
    public void runGame() {
        do {
            printHands(); // prints your entire hand and the first card of the dealer
            playGame(player.playerSum(), dealer.dealerSum()); // main game logic
        } while (play);

        printEndHand(); // prints the dealers entire hand.
    }

    //REQUIRES: select either 1 or 2
    //EFFECTS: sets up objects required for a new game. also sets up a new deck based on input 1 or 2.
    // (1) classic deck or (2) party deck. sets up starting variables play, stand, and bettingAmount.
    public void setUpNewGame(int select) {
        gameDeck = new Deck();
        player = new Player();
        dealer = new Dealer();
        play = true;
        stand = false;
        bettingAmount = 0;

        if (select == 1) {
            gameDeck.makeClassicDeck();
        } else if (select == 2) {
            gameDeck.makePartyDeck();
        }

        shuffle(); // shuffles the deck
        firstDeal(); // gives player and dealer two cards each
        makeBet(); // asks user for bet
    }

    // MODIFIES: this
    // EFFECTS: runs user input. If the player cash == 0, cash is reset.
    private void runInput() {
        boolean run = true;
        int select = 0;
        input = new Scanner(System.in);

        while (run) {
            displayMenu();
            select = input.nextInt();

            if (select == 0) {
                run = false;
            } else {
                processInput(select);
            }

            if (cash == 0) {
                System.out.println("SCORE: 0\nGAME OVER\n...\nGAME RESETS");
                cash = STARTING_CASH; //TODO change this ability
            }
        }
        System.out.println("\nThanks for playing!");
    }

    //
    public void processInput(int input) {
        if (input == 1 || input == 2) {
            setUpNewGame(input);
            runGame();
        } else if (input == 3) {
            if (gameLog.isEmpty()) {
                System.out.println("No games played yet");
            } else {
                printGameStatsLog();
            }
        } else if (input == 4) {
            saveGame();
        } else if (input == 5) {
            loadGameLog();
        } else {
            System.out.println("Invalid input");
        }
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("----------BLACKJACK----------\nSCORE: $" + cash);
        System.out.println("0. Quit ");
        System.out.println("1. Classic Mode (one deck)");
        System.out.println("2. Party Mode (six decks)");
        System.out.println("3. View Game log");
        System.out.println("4. Save Game");
        System.out.println("5. Load Game");
    }

    //REQUIRES: gameDeck is not empty
    //MODIFIES: this
    //EFFECTS: Shuffles gameDeck in random order.
    private void shuffle() {
        Collections.shuffle(gameDeck.getCardDeck());
    }

    //MODIFIES: this, playerCard, dealerCard
    //EFFECTS: draws two cards each for the player and the dealer.
    // Alternates adding the first card to playerCards and dealerCards twice. Removes
    // the added cards from gameDeck after each add.
    public void firstDeal() {
        for (int i = 0; i < 2; i++) {
            player.addCard(gameDeck.getFirstCardInDeck());
            gameDeck.removeFirstCardInDeck();
            dealer.addCard(gameDeck.getFirstCardInDeck());
            gameDeck.removeFirstCardInDeck();
        }
    }

    //MODIFIES: this
    //EFFECTS: takes in a user inputted betting amount and temporarily removes it
    // from their total cash. user input is stored in the value bettingAmount.
    // prints out a bet confirmation, while also printing out error messages if
    // inputted bet is invalid (negative amount or greater than total).
    private void makeBet() {
        int choice = cash + 1;

        System.out.println("Betting amount:" + " max (" + cash + ")");
        while (choice > cash || choice <= 0) {
            choice = input.nextInt();
            if (choice > cash) {
                System.out.println("Must be less than the maximum!");
            } else if (choice <= 0) {
                System.out.println("Bet must be greater than zero!");
            }
        }
        bettingAmount = choice;
        System.out.println("You are betting: " + bettingAmount);
        cash -= bettingAmount;
    }

    //REQUIRES: playerCards and dealerCards are not empty.
    //EFFECTS: prints out the dealer and players hands (card symbol)
    // and current totals (sum of card values). only shows the dealers first card
    private void printHands() {
        String yourHand = "";
        String dealerHand = "";
        for (Card c : player.getPlayerCards()) {
            yourHand += " " + c.getSymbol();
        }
        dealerHand += " " + dealer.getCardSymbol(0);

        System.out.print("Your cards:" + yourHand + " (" + player.playerSum() + ") ");
        System.out.println("Dealer's card:" + dealerHand + " (" + dealer.getCardValue(0) + ")");
    }

    //REQUIRES: playSum and dealSum > 0 (game must have started and hands must have been dealt)
    //MODIFIES: this
    //EFFECTS: main logic for the game. determines if a player wins, loses, or ties. takes in user input for
    // hitting (drawing cards) or standing (stop and calls dealerPlay).
    public void playGame(int playSum, int dealSum) {
        if (stand && playSum == dealSum) { // stand and tie
            standoff();
        } else if (playSum == 21 && dealSum == 21) { // 21 and tie
            standoff();
        } else if (playSum == 21) { // win and blackjack
            playerWins("Blackjack!", true);
        } else if (playSum > 21) { // lose and player busts
            playerLoses("Player busts.");
        } else if (dealSum > 21) { // win and dealer busts
            playerWins("Dealer busts!", false);
        } else if (stand && (playSum < dealSum)) { // stand and dealer is closer to 21
            playerLoses("Dealer closer to 21.");
        } else if (stand) { // stand and player is closer to 21 (second part is true regardless)
            playerWins("You're closer to 21!", false);
        } else {
            stand = hitOrStand();
            if (stand) {
                dealerPlay(); // dealer draws until over 17
            }
        }
    }

    //REQUIRES: play is false. only prints out if the game is over.
    //EFFECTS: prints out card symbol of each card in dealerHand. prints dealerSum.
    private void printEndHand() {
        String dealerHand = "";
        for (Card c : dealer.getDealerCards()) {
            dealerHand += " " + c.getSymbol();
        }
        System.out.println(dealerHand + " (" + dealer.dealerSum() + ")");
    }

    //REQUIRES: choice of integer 1 or 2
    //MODIFIES: stand, gameDeck, playerCards
    //EFFECTS: either hits or stands. if choice is 1, hit (returns false). if choice is 2, stand (returns true)
    // hitting adds a card to the playerHand and removes a card from gameDeck.
    private boolean hitOrStand() {
        System.out.println("Hit (1), Stand (2)"); // either hit or stand.
        int choice = input.nextInt();

        if (choice == 1) {
            player.addCard(gameDeck.getFirstCardInDeck());
            gameDeck.removeFirstCardInDeck();
            return false; // hit
        } else {
            return true; // stand
        }
    }

    //REQUIRES: play is true, stand is true.
    //MODIFIES: this, dealerCards
    //EFFECTS: Dealer draws cards until over 16. If ace is added and 16 < dealerSum < 21 then ace stays 11
    private void dealerPlay() {
        while (dealer.dealerSum() < 17) {
            dealer.addCard(gameDeck.getFirstCardInDeck());
            gameDeck.removeFirstCardInDeck();
        }
    }

    //REQUIRES: boolean blackjack is not null.
    //MODIFIES: this
    //EFFECTS: prints out a win statement. returns game wins.
    // if win and blackjack, adds 3:2 (bettingAmount * 2.5) amount.
    // otherwise match bet 1:1 (bettingAmount * 2) and adds to cash.
    // play is stopped (false), win is added, and gameLog win added.
    private void playerWins(String description, Boolean blackjack) {
        System.out.println(description + " Player wins!");
        if (blackjack) {
            cash += bettingAmount * 2.5;
            addGameLog(true, false, cash, (bettingAmount * 1.5));
        } else {
            cash += bettingAmount * 2;
            addGameLog(true, false, cash, bettingAmount);
        }
        play = false;
        wins++;
    }

    //MODIFIES: this
    //EFFECTS: prints out a losing statement. cash and bets are not changed.
    //play is stopped (false), loss is added and gameLog loss is added
    // with negative bettingAmount difference added.
    private void playerLoses(String description) {
        System.out.println(description + " Dealer wins.");
        play = false;
        losses++;
        addGameLog(false, true, cash, -bettingAmount);
    }

    //MODIFIES: this
    //EFFECTS: bets are returned in full to cash total.
    // (no change in cash before bet and after game)
    //play is stopped (false), and gameLog tie is added.
    private void standoff() {
        System.out.println("Standoff! Bets are returned.");
        cash += bettingAmount;
        play = false;
        addGameLog(true, true, cash, 0);
    }

    //MODIFIES: this
    //EFFECTS: creates a new game log with specified won or loss, current cash, and difference.
    // newLog is added to gameLog.
    public void addGameLog(boolean won, boolean loss, int cashLog, double difference) {
        model.Log newLog = new model.Log(won, loss, cashLog, difference);
        gameLog.add(newLog);
    }

    //EFFECTS: prints the win/loss percent and each log in gameLog in the format of win/loss/tie,
    // cash score, and diff (money difference from game).
    public void printGameStatsLog() {
        int count = 0;
        double winPercent = (wins / (losses + wins)) * 100;
        System.out.println("Win rate: " + Math.round(winPercent) + "%");

        for (model.Log l : gameLog) {
            count++;
            System.out.println("Game " + count);
            System.out.println(l.winLossStatus() + " - Score: " + l.getCashLog() + " - Diff: " + l.getDifference());
        }
    }

    private void saveGame() {
        try {
            jsonWriter.open();
            jsonWriter.writeGame(this);
            jsonWriter.close();
            System.out.println("Saved stats to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("cash", cash);
        json.put("wins", wins);
        json.put("losses", losses);
        json.put("gameLog", gameLogToJson());
        json.put("play", play);
        json.put("stand", stand);
        json.put("player", player.deckToJson());
        json.put("dealer", dealer.deckToJson());
        json.put("gameDeck", gameDeck.deckToJson());
        return json;
    }

    // EFFECTS: returns gameLogs from the game as a JSON array
    private JSONArray gameLogToJson() {
        JSONArray jsonArray = new JSONArray();
        for (model.Log l : gameLog) {
            jsonArray.put(l.toJson());
        }
        return jsonArray;
    }

    // MODIFIES: this
    // EFFECTS: loads workroom from file
    private void loadGameLog() {
        try {
            gameLog = jsonReader.readGameLog();
            System.out.println("Loaded from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }


    public void setCash(int cash) {
        this.cash = cash;
    }

    public void setPlay(Boolean play) {
        this.play = play;
    }

    public void setStand(Boolean stand) {
        this.stand = stand;
    }

    public void setWins(double wins) {
        this.wins = wins;
    }

    public void setLosses(double losses) {
        this.losses = losses;
    }
}
