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

        System.out.println("BLACKJACK");

        while (true) {
            System.out.println("-------------------------------");
            System.out.println("SCORE: $" + game.getCash());
            System.out.println("0. Quit \n1. Classic Mode (one deck)\n2. Party Mode (six decks)\n3. View Game log");
            select = input.nextInt();

            if (select == 0) {
                break;
            }

            game.startGame(select);

            if (game.getCash() == 0) {
                System.out.println("SCORE: 0\nGAME OVER\n...\nGAME RESETS");
                game = new Game();
            }
        }

        System.out.println("Thanks for playing");
    }
}