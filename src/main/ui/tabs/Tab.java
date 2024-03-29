package ui.tabs;

import ui.Game;

import javax.swing.*;

public abstract class Tab extends JPanel {

    protected final Game controller;

    public Tab(Game controller) {
        this.controller = controller;
    }

    //EFFECTS: returns the Game controller for this tab
    public Game getController() {
        return controller;
    }

}
