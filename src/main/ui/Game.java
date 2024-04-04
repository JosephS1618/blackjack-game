package ui;

import model.*;
import org.json.JSONObject;
import persistence.JsonReader;
import persistence.JsonWriter;
import ui.tabs.LoadTab;
import ui.tabs.SaveTab;
import ui.tabs.StatsTab;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

//Controls aspects of the game, including creating new decks, starting game, game logic,
// keeping track of cash, and keeping game logs/stats.
public class Game extends JFrame {
    private static final String JSON_STORE = "./data/saved.json";
    private static final int BOARD_WIDTH = 350;
    private static final int BOARD_HEIGHT = 300;

    Scanner input = new Scanner(System.in);

    private GameManager manageGame;
    private GameStatManager manageStats;

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private DefaultListModel<String> dealDefault;
    private DefaultListModel<String> playDefault;
    private JList<String> playerList;
    private JList<String> dealerList;

    private JPanel buttonPanel;
    private JPanel boardPanel;

    private JButton hitButton;
    private JButton stayButton;
    private JButton playButton;

    private JTabbedPane sidebar;

    private JLabel announcement;
    private JLabel playerSum;
    private JLabel dealerSum;

    //Constructor
    //EFFECTS: assigns STARTING_CASH to cash, creates a new empty list of gameLogs.
    public Game() throws FileNotFoundException {
        manageGame = new GameManager();
        manageStats = new GameStatManager();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        initializeGraphics();
    }

    //MODIFIES: this
    //EFFECTS: sets up the JFrame and the basic panels making up the game menu.
    public void initializeGraphics() {
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
        setTitle("BLACKJACK");

        setUpBoardPanel();
        displayTitleGraphics();
        displayPlayButton();
        makeSideBar();
        displayAnnouncement();
        initializeCardGUI();
        initializeGameButtonGraphics();
        displaySums();
        playButton.setVisible(true);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    //MODIFIES: this
    //EFFECTS: sets up the boardPanel where the game is played on.
    private void setUpBoardPanel() {
        boardPanel = new JPanel();
        boardPanel.setLayout(null);
        boardPanel.setBackground(new Color(58, 148, 1)); //green
        add(boardPanel);
    }

    //MODIFIES: this
    //EFFECTS: sets up the sidebar menu.
    public void makeSideBar() {
        sidebar = new JTabbedPane();
        sidebar.setTabPlacement(JTabbedPane.LEFT);
        //add board
        sidebar.add(boardPanel, 0);
        sidebar.setTitleAt(0, "play");
        //add stat
        JPanel statsTab = new StatsTab(this);
        sidebar.add(statsTab, 1);
        sidebar.setTitleAt(1, "stats");
        //save
        JPanel saveTab = new SaveTab(this);
        sidebar.add(saveTab, 2);
        sidebar.setTitleAt(2, "save");
        //load
        JPanel loadTab = new LoadTab(this);
        sidebar.add(loadTab, 3);
        sidebar.setTitleAt(3, "load");
        add(sidebar);
    }

    //TODO fix
    //MODIFIES: this
    //EFFECTS: sets up the play button that starts the game
    public void displayPlayButton() {
        playButton = new JButton("Play");
        playButton.setBounds(105, 150, 80, 40);
        boardPanel.add(playButton);
        playButton.setVisible(false);

        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                manageGame.setUpNewGame(1);
                updatePlayerCardGraphics();
                updateDealerCardGraphics();
                updatePlayerSum();
                updateDealerSumFirst();
                announcement.setVisible(false);
                hitButton.setEnabled(true);
                stayButton.setEnabled(true);
                playButton.setVisible(false);
            }
        });
    }

    //MODIFIES: this
    //EFFECTS: sets up the title of the game in boardPanel.
    public void displayTitleGraphics() {
        JLabel title = new JLabel("BLACKJACK");
        title.setBounds(110, 10, 100, 20);
        title.setForeground(Color.white);
        boardPanel.add(title, BorderLayout.NORTH);
    }

    //MODIFIES: this
    //EFFECTS:displays the announcement for win or loss.
    public void displayAnnouncement() {
        announcement = new JLabel();
        announcement.setBounds(130, 100, 50, 50);
        announcement.setForeground(Color.WHITE);
        boardPanel.add(announcement);
        announcement.setVisible(false);
    }

    //MODIFIES: this
    //EFFECTS: restarts the GUI for game. resets the player and dealer cards.
    public void initializeCardGUI() {
        playDefault = new DefaultListModel<>();
        playerList = new JList<>(playDefault);
        boardPanel.add(playerList);

        dealDefault = new DefaultListModel<>();
        dealerList = new JList<>(dealDefault);
        boardPanel.add(dealerList);
    }

    //MODIFIES: this
    //EFFECTS: displays the player cards.
    public void updatePlayerCardGraphics() {
        List<Card> playerCards  = manageGame.getPlayer().getPlayerCards();
        playDefault.removeAllElements();

        for (Card card : playerCards) {
            playDefault.addElement(card.getSymbol());
        }
        playerList.setBounds(50,50, 20, 18 * playerCards.size());
    }

    //MODIFIES: this
    //EFFECTS: displays the dealer cards.
    public void updateDealerCardGraphics() {
        List<Card> dealerCards = manageGame.getDealer().getDealerCards();
        dealDefault.removeAllElements();
        dealDefault.addElement(dealerCards.get(0).getSymbol()); //gets the first in dealer's deck
        dealerList.setBounds(240,50, 20,18 * dealerCards.size());
    }

    //MODIFIES: this
    //EFFECTS: shows the dealer's entire hands at the end.
    public void updateDealerCardGraphicsAtEnd() {
        List<Card> dealerCards = manageGame.getDealer().getDealerCards();
        dealDefault.removeAllElements();

        for (Card card : dealerCards) {
            dealDefault.addElement(card.getSymbol()); //gets all the cards in the dealer's deck
        }

        dealerList.setBounds(240,50, 20,18 * dealerCards.size());
    }

    //MODIFIES: this
    //EFFECTS: displays the sum shown above the player and dealer cards.
    public void displaySums() {
        playerSum = new JLabel();
        playerSum.setForeground(Color.WHITE);
        playerSum.setBounds(50, 30, 50, 20);
        boardPanel.add(playerSum);

        dealerSum = new JLabel();
        dealerSum.setForeground(Color.WHITE);
        dealerSum.setBounds(240, 30, 50, 20);
        boardPanel.add(dealerSum);
    }

    //MODIFIES: this
    //EFFECTS: initializes the game buttons hit and stay
    public void initializeGameButtonGraphics() {
        buttonPanel = new JPanel();

        hitButton = new JButton("Hit");
        hitButton.setFocusable(false);
        buttonPanel.add(hitButton);

        stayButton = new JButton("Stay");
        stayButton.setFocusable(false);
        buttonPanel.add(stayButton);

        add(buttonPanel, BorderLayout.SOUTH);

        hitOrStayAction();
    }

    //MODIFIES: this
    //EFFECTS: checks the action for the hit and stay button.
    public void hitOrStayAction() {
        hitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                manageGame.hit();
                updatePlayerCardGraphics();
                checkGame();
            }
        });

        stayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                manageGame.stand();
                hitButton.setEnabled(false);
                stayButton.setEnabled(false);
                updateDealerCardGraphicsAtEnd();
                updateDealerSum();
                checkGame();
            }
        });
    }

    //MODIFIES: this
    //EFFECTS: checks the game logic with playGame.
    public void checkGame() {
        manageGame.playGame(manageGame.getPlayer().playerSum(), manageGame.getDealer().dealerSum());
        updatePlayerSum();

        if (manageGame.getOutcome().equals("win")) {
            endGameFunctions("WIN");
        } else if (manageGame.getOutcome().equals("lose")) {
            endGameFunctions("LOSE");
        } else if (manageGame.getOutcome().equals("tie")) {
            endGameFunctions("TIE");
        }
    }

    //MODIFIES: this
    //EFFECTS: restarts the game.
    public void endGameFunctions(String message) {
        getOutcome();
        hitButton.setEnabled(false);
        stayButton.setEnabled(false);
        announcement.setText(message);
        announcement.setVisible(true);
        updateDealerSum();
        updateDealerCardGraphicsAtEnd();
        playButton.setVisible(true);
    }

    //MODIFIES: this
    //EFFECTS: modifies the player sum to update to the total sum
    public void updatePlayerSum() {
        String sumPlayer = String.valueOf(manageGame.getPlayer().playerSum());
        playerSum.setText(sumPlayer);
        playerSum.setVisible(true);
    }

    //MODIFIES: this
    //EFFECTS: updates the dealer sum to show the total sum
    public void updateDealerSum() {
        String sumDealer = String.valueOf(manageGame.getDealer().dealerSum());
        dealerSum.setText(sumDealer);
        dealerSum.setVisible(true);
    }

    //MODIFIES: this
    //EFFECTS: updates the dealer sum to show the first sum.
    public void updateDealerSumFirst() {
        String sumDealer = String.valueOf(manageGame.getDealer().getDealerCards().get(0).getNumber());
        dealerSum.setText(sumDealer);
        dealerSum.setVisible(true);
    }


    // MODIFIES: this
    // EFFECTS: runs user input. If the player cash == 0, cash is reset.
    public void runInput() {
        int select;
        input = new Scanner(System.in);

        while (true) {
            displayMenu();
            select = input.nextInt();

            if (select == 0) {
                quitDialogue();
            } else {
                processInput(select);
            }

            if (manageGame.getCash() == 0) {
                System.out.println("SCORE: 0\nGAME OVER\n...\nGAME RESETS");
                manageGame.setCash(500);
            }
        }
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("----------BLACKJACK----------\nSCORE: $" + manageGame.getCash());
        System.out.println("0. Quit ");
        System.out.println("1. Classic Mode (one deck)");
        System.out.println("2. Party Mode (six decks)");
        System.out.println("3. View Game log");
        System.out.println("4. Save Game");
        System.out.println("5. Load Game");
    }

    //EFFECTS: Asks users for menu input.
    public void processInput(int input) {
        if (input == 1 || input == 2) {
            manageGame.setUpNewGame(input);
            makeBet(); // asks user for bet
            playGame();
        } else if (input == 3) {
            manageStats.printGameStatsLog();
        } else if (input == 4) {
            saveGame();
        } else if (input == 5) {
            loadGame();
        } else {
            System.out.println("Invalid input");
        }
    }

    //MODIFIES: this
    //EFFECTS: takes in a user inputted betting amount and temporarily removes it
    // from their total cash. user input is stored in the value bettingAmount.
    // prints out a bet confirmation, while also printing out error messages if
    // inputted bet is invalid (negative amount or greater than total).
    private void makeBet() {
        int cash = manageGame.getCash();
        int choice = cash + 1;

        System.out.print("Betting amount" + " (max " + cash + "): ");
        while (choice > cash || choice <= 0) {
            choice = input.nextInt();
            if (choice > cash) {
                System.out.println("Must be less than the maximum!");
            } else if (choice <= 0) {
                System.out.println("Bet must be greater than zero!");
            }
        }

        System.out.println("You are betting: " + choice);
        System.out.println("----------------------");

        manageGame.removeBetFromCash(choice);
        manageGame.setBettingAmount(choice);
    }

    //EFFECTS: runs the
    private void playGame() {
        do {
            manageGame.runGame();
            hitOrStand();
        } while (manageGame.getPlay());
        getOutcome();
    }

    //EFFECTS: determines if the user selected hit or stand.
    private void hitOrStand() {
        if (manageGame.getPlay()) {
            System.out.println("(1) Hit (2) Stand");
            int choice = input.nextInt();
            if (choice == 1) {
                manageGame.hit();
            } else if (choice == 2) {
                manageGame.stand();
            }
        }
    }

    //EFFECTS: asks the user if they wish to keep playing. save to file, or simply quit.
    public void quitDialogue() {
        System.out.println("----------------------");
        System.out.println("Keep playing (any key)");
        System.out.println("Save         (s)");
        System.out.println("Quit game    (q)");
        System.out.println("----------------------");
        String choice = input.next().toLowerCase();
        if (choice.equals("s")) {
            saveGame();
            System.out.println("\nThanks for playing!");
            System.exit(0);
        } else if (choice.equals("q")) {
            System.out.println("\nThanks for playing!");
            System.exit(0);
        }
    }

    //MODIFIES: wins, loss
    //EFFECTS: determines if the outcome is a win or a loss or a tie, and adding to the game log.
    private void getOutcome() {
        int cash = manageGame.getCash();
        int bet = manageGame.getBettingAmount();

        if (manageGame.getOutcome().equals("win")) {
            manageStats.addWins();
            manageStats.addGameLog(true, false, cash, bet);
        } else if (manageGame.getOutcome().equals("lose")) {
            manageStats.addLosses();
            manageStats.addGameLog(false, true, cash, -bet);
        } else {
            manageStats.addGameLog(true, true, cash, 0);
        }
    }

    // EFFECTS: saves fields of the game by writing to the JSON file.
    public void saveGame() {
        try {
            jsonWriter.open();
            jsonWriter.write(this);
            jsonWriter.close();
            System.out.println("Saved stats to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    //EFFECTS: converts the all fields necessary to play a game to JSON format.
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("gameManager", manageGame.toJson());
        json.put("gameStats", manageStats.toJson());
        return json;
    }

    // MODIFIES: this
    // EFFECTS: loads game data from file
    public void loadGame() {
        try {
            manageGame = jsonReader.readManageGame();
            manageStats = jsonReader.readManageStats();
            System.out.println("Loaded from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    public GameManager getManageGame() {
        return manageGame;
    }

    public GameStatManager getManageStats() {
        return manageStats;
    }

    public List<Log> getStats() {
        return manageStats.getGameLog();
    }

}
