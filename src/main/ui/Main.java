package ui;

import java.sql.SQLOutput;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Game game = new Game();
        int select;

        System.out.println("BLACKJACK");
        System.out.println("1. Classic Mode\n2. Party Mode\n3. View Game log");
        select = input.nextInt();
        game.startGame(select);

        System.out.println("Thanks for playing");


        //game1.printDeck(); //test only
    }
}
