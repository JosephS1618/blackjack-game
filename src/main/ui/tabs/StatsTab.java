package ui.tabs;


import model.Log;
import ui.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class StatsTab extends Tab {

    DefaultListModel<String> logs;
    JList<String> playerLog;

    public StatsTab(Game controller) {
        super(controller);
        setLayout(new BorderLayout());
        logs = new DefaultListModel<>();
        playerLog = new JList<>(logs);
        playerLog.setBounds(50,50, 50,200);
        add(playerLog, BorderLayout.NORTH);
        updateStatButton();
    }

    private void updateStat() {
        List<Log> player = controller.getStats();
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
