package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;
import ui.MessagePrinter;

import java.util.ArrayList;
import java.util.List;

// Runs the game stat manager. keeps track of the wins and losses, as well as a list of game logs.
public class GameStatManager extends MessagePrinter implements Writable {
    private List<Log> gameLog;
    private double wins;
    private double losses;

    //EFFECTS: makes a new stat manager with no wins, no losses, and an empty gameLog.
    public GameStatManager() {
        gameLog = new ArrayList<>();
        wins = 0;
        losses = 0;
    }

    //MODIFIES: this
    //EFFECTS: creates a new game log with specified won or loss, current cash, and difference.
    // newLog is added to gameLog.
    public void addGameLog(boolean won, boolean loss, int cashLog, double difference) {
        Log newLog = new Log(won, loss, cashLog, difference);
        gameLog.add(newLog);
    }

    //EFFECTS: prints the win/loss percent and each log in gameLog in the format of win/loss/tie,
    // cash score, and diff (money difference from game).
    public void printGameStatsLog() {
        if (gameLog.isEmpty()) {
            print("No games played yet");
        } else {
            int gameNumber = 0;
            double winPercent = (wins / (losses + wins)) * 100;
            print("----------------------");
            print("Win rate: " + Math.round(winPercent) + "%");

            for (Log l : gameLog) {
                gameNumber++;
                print("Game " + gameNumber);
                print(l.winLossStatus() + " - Score: " + l.getCashLog() + " - Diff: " + l.getDifference());
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: adds a
    public void addWins() {
        this.wins++;
        EventLog.getInstance().logEvent(new Event("Added a win. Win total: " + wins));
    }

    public void addLosses() {
        this.losses++;
        EventLog.getInstance().logEvent(new Event("Added a loss. loss total: " + losses));
    }

    //EFFECTS: saves the wins losses, and gameLog by writing it to a JSON file.
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("wins", wins);
        json.put("losses", losses);
        json.put("gameLog", gameLogToJson());
        return json;
    }

    // EFFECTS: returns gameLogs from the game as a JSON array
    private JSONArray gameLogToJson() {
        JSONArray jsonArray = new JSONArray();
        for (model.Log l : gameLog) {
            jsonArray.put(l.toJson());
        }
        return jsonArray;
    }

    public void setGameLog(List<Log> gameLog) {
        this.gameLog = gameLog;
    }

    public void setWins(double wins) {
        this.wins = wins;
    }

    public void setLosses(double losses) {
        this.losses = losses;
    }

    public List<Log> getGameLog() {
        return gameLog;
    }

    public double getWins() {
        return wins;
    }

    public double getLosses() {
        return losses;
    }
}
