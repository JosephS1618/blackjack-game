package ui;

import java.sql.SQLOutput;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Game game = new Game();
        int select = 0;

        System.out.println("BLACKJACK");

        do {
            System.out.println("1. Classic Mode (one deck)\n2. Party Mode (six decks)\n3. View Game log");
            select = input.nextInt();
            game.startGame(select);
        } while (select != 0);

        System.out.println("Thanks for playing");


        //game1.printDeck(); //test only
    }
}
