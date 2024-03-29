package model;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

// Specifications for the cards that the dealer hold, as well as the logic
// behind calculating the sum of the dealer cards.
public class Dealer {
    private List<Card> dealerCards; //cards that the player holds

    //Constructor
    //EFFECTS: creates a new list for the dealer to hold cards
    public Dealer() {
        dealerCards = new ArrayList<>();
    }

    //REQUIRES: dealerCards size > 0
    //EFFECTS: returns the sum of the dealers card numbers. if player has an ace and the sum is over 21,
    // calls dealerSumWithAce.
    public int dealerSum() {
        int sum = 0;

        for (Card c : dealerCards) {
            sum += c.getNumber();
        }
        if (sum > 21 && (findAce() != null)) { //if the player has an 11 and they bust.
            return dealerSumWithAce();
        }

        return sum;
    }

    //EFFECTS: if players hand contains an ace, and they hit over 21, change the first ace to a 1 and returns dealerSum
    // with the new change. If necessary, multiple aces will be changed through each pass of dealerSum.
    public int dealerSumWithAce() {
        findAce().setNumber(1); //changes ace from 11 to 1
        return dealerSum();
    }

    //EFFECTS: finds and returns the first ace card with value 11. otherwise returns null.
    public Card findAce() {
        for (Card c : dealerCards) {
            if (c.getNumber() == 11) {
                return c;
            }
        }
        return null;
    }

    //REQUIRES: play is true, stand is true.
    //MODIFIES: dealerCards, Deck
    //EFFECTS: Dealer draws cards until over 16. If ace is added and 16 < dealerSum < 21 then ace stays 11
    public void play(Deck deck) {
        while (dealerSum() < 17) {
            dealerCards.add(deck.getFirstCardInDeck());
            deck.removeFirstCardInDeck();
        }
    }

    public void moveCardToHand(Deck deck) {
        addCard(deck.getFirstCardInDeck());
        deck.removeFirstCardInDeck();
    }

    public void addCard(Card card) {
        dealerCards.add(card);
    }

    public List<Card> getDealerCards() {
        return dealerCards;
    }

    public String getCardSymbol(int index) {
        return dealerCards.get(index).getSymbol();
    }

    public int getCardValue(int index) {
        return dealerCards.get(index).getNumber();
    }

//    public JSONArray deckToJson() {
//        JSONArray jsonArray = new JSONArray();
//        for (Card card : dealerCards) {
//            jsonArray.put(card.toJson());
//        }
//        return jsonArray;
//    }
}
