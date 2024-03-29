package ui.tabs;

import ui.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoadTab extends Tab {
    JLabel message;

    public LoadTab(Game controller) {
        super(controller);
        setLayout(new BorderLayout());
        message = new JLabel("Game Loaded");
        message.setBounds(150, 100, 100, 100);
        add(message);
        message.setVisible(false);
        placeLoadButton();
    }

    private void placeLoadButton() {
        JButton load = new JButton("load");
        load.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.loadGame();
                message.setVisible(true);
            }
        });
        add(load, BorderLayout.SOUTH);
    }
}
