package model;

//keeps track of a games won or loss. Each log is a single game. later checks when the log is printed. a tie counts as
// true for won and loss. cashLog is the cash after the game. difference is the money gained (positive), net zero (0),
// or money lost (negative).
public class Log {
    private boolean won; //true = won
    private boolean loss; //true = lost
    private int cashLog;
    private double difference;

    //EFFECTS: sets the status of won, loss,
    public Log(boolean won, boolean loss, int cashLog, double difference) {
        this.won = won;
        this.loss = loss;
        this.cashLog = cashLog;
        this.difference = difference;
    }

    //REQUIRES: log has to have been created by Game. (cannot be false for both win and loss)
    //EFFECTS: returns the string of tie, win or lose based on the recorded outcome
    public String winLossStatus() {
        if (isWon() && isLoss()) {
            return "Tie";
        } else if (isWon()) {
            return "Win";
        } else {
            return "Lose";
        }
    }

    public boolean isWon() {
        return won;
    }

    public boolean isLoss() {
        return loss;
    }

    public int getCashLog() {
        return cashLog;
    }

    public double getDifference() {
        return difference;
    }
}
