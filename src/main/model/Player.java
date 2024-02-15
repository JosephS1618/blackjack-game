package model;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private List<Card> playerCards; //cards that the player holds
    private List<Card> splitCards; //TODO

    //Constructor
    //EFFECTS: creates a new list for the player to hold cards
    public Player() {
        playerCards = new ArrayList<>();
    }

    //REQUIRES: player cards are not empty
    //EFFECTS: returns the sum of the players card numbers.
    public int playerSum() {
        int sum = 0;

        for (Card c : playerCards) {
            sum += c.getNumber();
        }
        if (sum > 21 && (findAce() != null)) { //if the player has a 11 and they bust.
            return playerSumWithAce();
        }

        return sum;
    }

    //EFFECTS: if players hand contains an ace, and they hit over 21, change the ace to a 1 and return the new sum.
    private int playerSumWithAce() {
        findAce().setNumber(1); //changes ace from 11 to 1
        return playerSum();
    }

    //EFFECTS: finds and returns the first ace card with value 11. otherwise returns null.
    private Card findAce() {
        for (Card c : playerCards) {
            if (c.getNumber() == 11) {
                return c;
            }
        }
        return null;
    }

    //EFFECTS: adds a card to the players cards.
    public void addCard(Card card) {
        playerCards.add(card);
    }

    public List<Card> getPlayerCards() {
        return playerCards;
    }

    public String getCardSymbol(int index) {
        return playerCards.get(index).getSymbol();
    }

}
