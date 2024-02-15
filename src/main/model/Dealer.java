package model;

import java.util.LinkedList;
import java.util.List;

public class Dealer {
    private List<Card> dealerCards; //cards that the player holds

    //Constructor
    //EFFECTS: creates a new list for the dealer to hold cards
    public Dealer() {
        dealerCards = new LinkedList<>();
    }

    //REQUIRES: dealer cards are not empty
    //EFFECTS: returns the sum of the dealers card numbers.
    public int dealerSum() {
        int sum = 0;

        for (Card c : dealerCards) {
            sum += c.getNumber();
        }
        if (sum > 21 && (findAce() != null)) { //if the player has a 11 and they bust.
            return dealerSumWithAce();
        }

        return sum;
    }

    //EFFECTS: if players hand contains an ace, and they hit over 21, change the ace to a 1 and return the new sum.
    private int dealerSumWithAce() {
        findAce().setNumber(1); //changes ace from 11 to 1
        return dealerSum();
    }

    //EFFECTS: finds and returns the first ace card with value 11. otherwise returns null.
    private Card findAce() {
        for (Card c : dealerCards) {
            if (c.getNumber() == 11) {
                return c;
            }
        }
        return null;
    }

    public void addCard(Card card) {
        dealerCards.add(card);
    }

    public List<Card> getDealerCards() {
        return dealerCards;
    }
}
