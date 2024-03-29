package model;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

// Specifications for the cards that the players hold, as well as the logic
// behind calculating the sum of the players cards.
public class Player {
    private List<Card> playerCards; //cards that the player holds

    //Constructor
    //EFFECTS: creates a new list for the player to hold cards
    public Player() {
        playerCards = new ArrayList<>();
    }

    //REQUIRES: playerCards size > 0
    //EFFECTS: returns the sum of the players card numbers. if player has an ace and the sum is over 21,
    // calls playerSumWithAce.
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

    //EFFECTS: if players hand contains an ace, and they hit over 21, change the first ace to a 1 and returns playerSum
    // with the new change. If necessary, multiple aces will be changed through each pass of playerSum.
    public int playerSumWithAce() {
        findAce().setNumber(1); //changes ace from 11 to 1
        return playerSum();
    }

    //EFFECTS: finds and returns the first ace card with value 11. otherwise returns null.
    public Card findAce() {
        for (Card c : playerCards) {
            if (c.getNumber() == 11) {
                return c;
            }
        }
        return null;
    }

    public void moveCardToHand(Deck deck) {
        addCard(deck.getFirstCardInDeck());
        deck.removeFirstCardInDeck();
    }

    //EFFECTS: adds a card to the players cards.
    public void addCard(Card card) {
        playerCards.add(card);
    }

    public List<Card> getPlayerCards() {
        return playerCards;
    }

//    public JSONArray deckToJson() {
//        JSONArray jsonArray = new JSONArray();
//        for (Card card : playerCards) {
//            jsonArray.put(card.toJson());
//        }
//        return jsonArray;
//    }


}
