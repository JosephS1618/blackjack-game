package ui;

import model.Card;
import model.Dealer;
import model.Player;

public class MessagePrinter {
    public void print(String message) {
        System.out.println(message);
    }

    //REQUIRES: playerCards and dealerCards are not empty.
    //EFFECTS: prints out the dealer and players hands (card symbol)
    // and current totals (sum of card values). only shows the dealers first card
    public void printHands(Player player, Dealer dealer) {
        String yourHand = "";
        String dealerHand = "";
        for (Card c : player.getPlayerCards()) {
            yourHand += " " + c.getSymbol();
        }
        dealerHand += " " + dealer.getCardSymbol(0);

        System.out.println("Your cards:"    + yourHand   + " (" + player.playerSum()           + ")");
        System.out.println("Dealer's card:" + dealerHand + " (" + dealer.getCardValue(0) + ")");
    }

    //REQUIRES: play is false. only prints out if the game is over.
    //EFFECTS: prints out card symbol of each card in dealerHand. prints dealerSum.
    public void printEndHand(Dealer dealer) {
        String dealerHand = "";
        for (Card c : dealer.getDealerCards()) {
            dealerHand += " " + c.getSymbol();
        }
        System.out.println(dealerHand + " (" + dealer.dealerSum() + ")");
    }
}
