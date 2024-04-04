package ui.tabs;


import model.Log;
import ui.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

// contains the methods related to the "stats" tab on the menu screen
public class StatsTab extends Tab {
    private DefaultListModel<String> logs;
    private JList<String> playerLog;
    private JLabel winLabel;
    private JLabel loseLabel;

    //EFFECTS: sets the controller Game, and initializes visuals.
    public StatsTab(Game controller) {
        super(controller);
        setLayout(new BorderLayout());
        initializeLog();
        updateStatButton();
        updateWinButton();
        initializeWinLabel();
        updateLossesButton();
        initializeLossesLabel();
    }

    //MODIFIES: this
    //EFFECTS: initializes label
    private void initializeLossesLabel() {
        loseLabel = new JLabel();
        loseLabel.setBounds(0, 30, 100, 20);
        add(loseLabel, BorderLayout.CENTER);
        loseLabel.setVisible(false);
    }

    //MODIFIES: this
    //EFFECTS: updates the label
    private void updateLossesButton() {
        JButton getLosses = new JButton("get losses");
        getLosses.setBounds(0, 100, 100, 30);
        add(getLosses);
        getLosses.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Double losses = controller.getManageStats().getLosses();
                loseLabel.setText("Lost: " + Double.toString(losses));
                loseLabel.setVisible(true);
            }
        });
    }

    //MODIFIES: this
    //EFFECTS: initializes label
    private void initializeWinLabel() {
        winLabel = new JLabel();
        winLabel.setBounds(0, 30, 100, 20);
        add(winLabel, BorderLayout.CENTER);
        winLabel.setVisible(false);
    }

    //MODIFIES: this
    //EFFECTS: updates the label
    public void updateWinButton() {
        JButton getWins = new JButton("get wins");
        getWins.setBounds(0, 50, 80, 30);
        add(getWins);
        getWins.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Double wins = controller.getManageStats().getWins();
                winLabel.setText("Wins: " + Double.toString(wins));
                winLabel.setVisible(true);
            }
        });
    }

    //MODIFIES: this
    //EFFECTS: initializes list
    private void initializeLog() {
        logs = new DefaultListModel<>();
        playerLog = new JList<>(logs);
        playerLog.setBounds(50,50, 100,200);
        add(playerLog, BorderLayout.EAST);
    }

    //MODIFIES: this
    //EFFECTS: updates the list
    private void updateStat() {
        List<Log> player = controller.getStats();
        logs.removeAllElements();
        for (Log log : player) {
            if (log.isWon() && !log.isLoss()) {
                logs.addElement("Won");
            } else if (!log.isWon() && log.isLoss()) {
                logs.addElement("Loss");
            } else {
                logs.addElement("Loss");
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: initializes buttons
    private void updateStatButton() {
        JButton update = new JButton("update");
        update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateStat();
            }
        });
        add(update, BorderLayout.SOUTH);
    }






}
