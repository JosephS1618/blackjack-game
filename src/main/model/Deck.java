package model;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

// Creates a new shuffled card deck. Either a single deck (classic mode) or six decks (party mode).
// Each card object is stored in an array list cardDeck.
public class Deck {
    private List<Card> cardDeck;

    //EFFECTS: creates a new empty arrayList for Card objects
    public Deck() {
        cardDeck = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: Creates 52 cards each with value, symbol and suit.
    public void makeClassicDeck() {
        for (int suit = 1; suit < 5; suit++) { //making each suit
            for (int value = 1; value < 14; value++) { //making each card in suit
                makeSuit(suit, value);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: Combines 6 deck for 312 cards each with value, symbol and suit.
    public void makePartyDeck() {
        List<Card> tempDeck = new ArrayList<>();
        for (int x = 0; x < 6; x++) { // change the x limit to make a deck of any multiple
            makeClassicDeck();
            for (int y = 0; y < 52; y++) {
                tempDeck.add(cardDeck.get(y));
            }
            cardDeck.clear();
        }
        cardDeck = tempDeck;
    }

    //REQUIRES: number between 1-13
    //EFFECTS: assigns a suit based on the given suit number
    public void makeSuit(int suit, int number) {
        switch (suit) {
            case 1:
                makeCard("D", number);
                break;
            case 2:
                makeCard("C", number);
                break;
            case 3:
                makeCard("H", number);
                break;
            case 4:
                makeCard("S", number);
                break;
        }
    }

    //REQUIRES: suit is char 'D', 'C', 'H', or 'S', int number is between 1 and 13
    //MODIFIES: this, card
    //EFFECTS: creates card of number, symbol and suit. adds card to cardDeck
    public void makeCard(String suit, int number) {
        Card newCard = null;

        if (number == 1) {
            newCard = new Card(11,"A", suit);
        } else if (number > 1 && number < 11) {
            newCard = new Card(number, String.valueOf(number), suit);
        } else if (number == 11) {
            newCard = new Card(10, "J", suit);
        } else if (number == 12) {
            newCard = new Card(10, "Q", suit);
        } else if (number == 13) {
            newCard = new Card(10, "K", suit);
        }

        cardDeck.add(newCard);
    }

    public void addCard(Card newCard) {
        cardDeck.add(newCard);
    }

    //EFFECTS: returns the integer size of cardDeck.
    public int cardDeckSize() {
        return cardDeck.size();
    }

    public List<Card> getCardDeck() {
        return cardDeck;
    }

    //REQUIRES: cardDeckSize > 0
    //EFFECTS: gets and returns the card in index 0 of cardDeck
    public Card getFirstCardInDeck() {
        return cardDeck.get(0);
    }

    //REQUIRES: cardDeckSize > 0
    //MODIFIES: this
    //EFFECTS: removes the card in index 0 of cardDeck
    public void removeFirstCardInDeck() {
        cardDeck.remove(0);
    }

    // EFFECTS: returns gameLogs from the game as a JSON array
    public JSONArray deckToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Card card : cardDeck) {
            jsonArray.put(card.toJson());
        }
        return jsonArray;
    }

}
