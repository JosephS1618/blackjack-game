package ui.tabs;

import ui.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SaveTab extends Tab {
    JLabel message;

    public SaveTab(Game controller) {
        super(controller);
        setLayout(new BorderLayout());
        message = new JLabel("Game Saved");
        message.setBounds(150, 100, 100, 100);
        add(message);
        message.setVisible(false);
        placeSaveButton();
    }

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
