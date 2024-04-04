package ui.tabs;

import ui.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// contains the methods related to the "save" tab on the menu screen
public class SaveTab extends Tab {
    JLabel message;

    //EFFECTS: sets the controller Game, and initializes visuals.
    public SaveTab(Game controller) {
        super(controller);
        setLayout(new BorderLayout());
        message = new JLabel("Game Saved");
        message.setBounds(150, 100, 100, 100);
        add(message);
        message.setVisible(false);
        placeSaveButton();
    }

    //MODIFIES: this
    //EFFECTS: creates the button for saving game information.
    private void placeSaveButton() {
        JButton save = new JButton("save");
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.saveGame();

                message.setVisible(true);
            }
        });
        add(save, BorderLayout.SOUTH);
    }
}
