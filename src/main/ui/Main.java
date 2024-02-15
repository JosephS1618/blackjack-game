package ui;

import java.sql.SQLOutput;
import java.util.Scanner;

//initial game menu. shows user what functions are available, 0,1,2,3. selecting any integer other than 0 brings them
//into the UI of the game class. If the game is over (cash reaches 0), the game resets completely, wiping all data
// and score progress. 0 quits the game from the menu.
public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Game game = new Game();
        int select;

        while (true) {
            System.out.println("-------------------------------\nSCORE: $" + game.getCash());
            System.out.println("0. Quit \n1. Classic Mode (one deck)\n2. Party Mode (six decks)\n3. View Game log");
            select = input.nextInt();

            if (select == 0) {
                break;
            } else if  (select == 3) {
                if (game.getGameLog().isEmpty()) {
                    System.out.println("No games played yet");
                } else {
                    game.printGameStatsLog();
                }
            } else if (select == 1 || select == 2) {
                game.startGame(select);
            }

            if (game.getCash() == 0) {
                System.out.println("SCORE: 0\nGAME OVER\n...\nGAME RESETS");
                game = new Game();
            }
        }
    }
}