package ui.tabs;

import ui.Game;

import javax.swing.*;

// abstract class relating to each of the tabs in the menu sidebar.
public abstract class Tab extends JPanel {

    //EFFECTS: sets the controller Game, and initializes visuals.
    protected final Game controller;

    //EFFECTS: sets the controller Game
    public Tab(Game controller) {
        this.controller = controller;
    }

}
