package model;

import java.util.ArrayList;
import java.util.List;

public class Dealer {
    private List<Card> dealerCards; //cards that the player holds

    //Constructor
    //EFFECTS: creates a new list for the dealer to hold cards
    public Dealer() {
        dealerCards = new ArrayList<>();
    }

    public void addCard(Card card) {
        dealerCards.add(card);
    }

    public List<Card> getDealerCards() {
        return dealerCards;
    }
}
