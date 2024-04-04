package ui.tabs;

import ui.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// contains the methods related to the "load" tab on the menu screen
public class LoadTab extends Tab {
    JLabel message;

    //EFFECTS: sets the controller Game, and initializes visuals.
    public LoadTab(Game controller) {
        super(controller);
        setLayout(new BorderLayout());
        message = new JLabel("Game Loaded");
        message.setBounds(150, 100, 100, 100);
        add(message);
        message.setVisible(false);
        placeLoadButton();
    }

    //MODIFIES: this
    //EFFECTS: creates the button for loading game information.
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
