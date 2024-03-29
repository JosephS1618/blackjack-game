package ui;

import model.*;
import org.json.JSONObject;
import persistence.JsonReader;
import persistence.JsonWriter;
import ui.tabs.LoadTab;
import ui.tabs.SaveTab;
import ui.tabs.StatsTab;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private JButton hitButton;
    private JButton stayButton;
    private JPanel boardPanel;
    private JLabel announcement;
    private JLabel playerSum;
    private JLabel dealerSum;

    private JTabbedPane sidebar;



    //Constructor
    //EFFECTS: assigns STARTING_CASH to cash, creates a new empty list of gameLogs.
    public Game() throws FileNotFoundException {
        manageGame = new GameManager();
        manageStats = new GameStatManager();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        initializeGraphics();
    }

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

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void setUpBoardPanel() {
        boardPanel = new JPanel();
        boardPanel.setLayout(null);
        boardPanel.setBackground(new Color(58, 148, 1)); //green
        add(boardPanel);
    }

    public void makeSideBar() {
        sidebar = new JTabbedPane();
        sidebar.setTabPlacement(JTabbedPane.LEFT);
        //addboard
        sidebar.add(boardPanel, 0);
        sidebar.setTitleAt(0, "play");
        //addstat
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



    public void displayPlayButton() {
        JButton play = new JButton("Play");
        play.setBounds(105, 150, 80, 40);
        boardPanel.add(play);
        play.setVisible(true);

        play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setupNewGameGUI();
                manageGame.setUpNewGame(1);
                boardPanel.updateUI();
                initializeGameButtonGraphics();
                displayPlayerCardGraphics();
                displayDealerCardGraphics();
                displayAnnouncement();
                displaySums();
                hitButton.setEnabled(true);
                stayButton.setEnabled(true);
                play.setVisible(false);
            }
        });
    }

    public void setupNewGameGUI() {
        playDefault = new DefaultListModel<>();
        playerList = new JList<>(playDefault);
        playerList.setBounds(50,50, 20,100);
        boardPanel.add(playerList);
    }

    public void displayTitleGraphics() {
        JLabel title = new JLabel("BLACKJACK");
        title.setBounds(110, 10, 100, 20);
        title.setForeground(Color.white);
        boardPanel.add(title, BorderLayout.NORTH);
    }

    public void displayAnnouncement() {
        announcement = new JLabel();
        announcement.setBounds(130, 100, 50, 50);
        announcement.setForeground(Color.WHITE);
        boardPanel.add(announcement);
        announcement.setVisible(false);
    }

    public void displayPlayerCardGraphics() {
        List<Card> playerCards  = manageGame.getPlayer().getPlayerCards();

        for (Card card : playerCards) {
            playDefault.addElement(card.getSymbol());
        }
    }

    public void displayDealerCardGraphics() {
        List<Card> dealerCards = manageGame.getDealer().getDealerCards();
        dealDefault = new DefaultListModel<>();

        dealDefault.addElement(dealerCards.get(0).getSymbol());

        dealerList = new JList<>(dealDefault);
        dealerList.setBounds(240,50, 20,100);
        boardPanel.add(dealerList);
    }

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

    public void displayEndCardGraphics() {
        List<Card> dealerCards = manageGame.getDealer().getDealerCards();
        dealDefault.removeAllElements();

        for (Card card : dealerCards) {
            dealDefault.addElement(card.getSymbol());
        }

        dealerList = new JList<>(dealDefault);
        dealerList.setBounds(240,50, 20,20 * dealDefault.size());
        boardPanel.add(dealerList);
    }

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

    public void hitOrStayAction() {
        hitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //playDefault.addElement(manageGame.getGameDeck().getCardDeck().get(0).getSymbol());
                playDefault.removeAllElements();
                displayPlayerCardGraphics();
                manageGame.hit();
                checkGame();
            }
        });

        stayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hitButton.setEnabled(false);
                stayButton.setEnabled(false);
                displayEndCardGraphics();
                manageGame.stand();
                checkGame();
            }
        });
    }

    public void checkGame() {
        manageGame.playGame(manageGame.getPlayer().playerSum(), manageGame.getDealer().dealerSum());
        String sumPlayer = String.valueOf(manageGame.getPlayer().playerSum());

        playerSum.setText(sumPlayer);

        if (manageGame.getOutcome() == "win") {
            endGameFunctions("WIN");
        } else if (manageGame.getOutcome() == "lose") {
            endGameFunctions("LOSE");
        } else if (manageGame.getOutcome() == "tie") {
            endGameFunctions("TIE");
        }
    }

    public void endGameFunctions(String message) {
        getOutcome();
        hitButton.setEnabled(false);
        stayButton.setEnabled(false);
        announcement.setText(message);
        announcement.setVisible(true);
        String sumDealer = String.valueOf(manageGame.getDealer().dealerSum());
        dealerSum.setText(sumDealer);
        displayEndCardGraphics();
        displayPlayButton();
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

    private void playGame() {
        do {
            manageGame.runGame();
            hitOrStand();
            repaint();
        } while (manageGame.getPlay());
        displayDealerCardGraphics();
        getOutcome();
    }

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

    private void getOutcome() {
        int cash = manageGame.getCash();
        int bet = manageGame.getBettingAmount();

        if (manageGame.getOutcome() == "win") {
            manageStats.addWins();
            blackjackOutcome(cash, bet);
        } else if (manageGame.getOutcome() == "lose") {
            manageStats.addLosses();
            manageStats.addGameLog(false, true, cash, -bet);
        } else {
            manageStats.addGameLog(true, true, cash, 0);
        }
    }

    private void blackjackOutcome(int cash, int bet) {
        if (manageGame.isBlackjack()) {
            cash += bet * 2.5;
            manageStats.addGameLog(true, false, cash, (bet * 1.5));
        } else {
            cash += bet * 2;
            manageStats.addGameLog(true, false, cash, bet);
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

    public List<Log> getStats() {
        return manageStats.getGameLog();
    }

}
