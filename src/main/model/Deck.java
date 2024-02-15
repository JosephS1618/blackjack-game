package model;

import java.util.ArrayList;
import java.util.List;

// Creates a new shuffled card deck. Either a single deck (classic mode) or six decks (party mode).
public class Deck {
    private List<Card> cardDeck;

    //EFFECTS: creates a new deck of cards with either 52 cards (classic) or 312 cards (party)
    public Deck(int select) {
        cardDeck = new ArrayList<>();

        if (select == 1) {
            makeClassicDeck();
        } else if (select == 2) {
            makePartyDeck();
        }
    }

    // MODIFIES: this
    // EFFECTS: Creates a deck of 52 cards each with value, symbol and suit.
    private void makeClassicDeck() {
        for (int suit = 1; suit < 5; suit++) { //making each suit
            for (int value = 1; value < 14; value++) { //making each card in suit
                makeSuit(suit, value);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: Combines 6 deck for 312 cards each with value, symbol and suit.
    private void makePartyDeck() {
        makeClassicDeck();
        List<Card> tempDeck = new ArrayList<>();
        for (int x = 0; x < 6; x++) { // change the x limit to make a deck of any size
            for (int y = 0; y < 52; y++) {
                tempDeck.add(cardDeck.get(y));
            }
        }

        cardDeck = tempDeck;
    }

    //REQUIRES: number between 1-13
    //EFFECTS: assigns a suit based on the given suit number
    private void makeSuit(int suit, int number) {
        switch (suit) {
            case 1:
                makeCard('D', number);
                break;
            case 2:
                makeCard('C', number);
                break;
            case 3:
                makeCard('H', number);
                break;
            case 4:
                makeCard('S', number);
                break;
        }
    }

    //REQUIRES: suit is char 'D', 'C', 'H', or 'S', int number is between 1 and 13
    //MODIFIES: this, card
    //EFFECTS: creates card of number, symbol and suit.
    private void makeCard(char suit, int number) {
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

    //EFFECTS: returns the integer size of the deck.
    public int cardDeckSize() {
        return cardDeck.size();
    }

    public List<Card> getCardDeck() {
        return cardDeck;
    }

}
