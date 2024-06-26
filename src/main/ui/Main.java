package ui;

import java.io.FileNotFoundException;

//initial game menu. shows user what functions are available, 0,1,2,3. selecting any integer other than 0 brings them
//into the UI of the game class. If the game is over (cash reaches 0), the game resets completely, wiping all data
// and score progress. 0 quits the game from the menu.
public class Main {
    public static void main(String[] args) {
        try {
            Game game = new Game();
            game.runInput(); // use to run terminal instead of GUI
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        }

    }
}