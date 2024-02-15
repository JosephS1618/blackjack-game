package model;

import java.util.ArrayList;
import java.util.List;

// Creates a new shuffled card deck. Either a single deck (classic mode) or six decks (party mode).
public class Deck {
    private List<Card> deck;

    //EFFECTS: creates a new deck of cards with either 52 cards (classic) or 312 cards (party) and shuffles it
    public Deck(int select) {
        deck = new ArrayList<>();

        if (select == 1) {
            makeClassicDeck();
        } else if (select == 2) {
            makePartyDeck();
        }
        shuffle();
    }

    private void makeClassicDeck() {
        for (int suit = 1; suit < 5; suit++) { //making each suit
            for (int value = 1; value < 14; value++) { //making each card in suit
                makeSuit(suit, value);
            }
        }
    }

    private void makePartyDeck() {
        //do later
    }

    //REQUIRES: number between 1-13
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

    private void makeCard(char suit, int number) {
        Card card = null;

        if (number == 1) {
            card = new Card(11,"A", suit);
        } else if (number > 1 && number < 11) {
            card = new Card(number, String.valueOf(number), suit);
        } else if (number == 11) {
            card = new Card(10, "J", suit);
        } else if (number == 12) {
            card = new Card(10, "Q", suit);
        } else if (number == 13) {
            card = new Card(10, "K", suit);
        }

        deck.add(card);
    }

    //REQUIRES: the deck is not empty
    //MODIFIES: this
    //EFFECTS: Shuffles the deck.
    private void shuffle() {
        //
    }

    public int deckSize() {
        return deck.size();
    }

    public List<Card> getDeck() {
        return deck;
    }

}
