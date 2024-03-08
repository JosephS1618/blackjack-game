package persistence;

import model.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

// Represents a reader that reads Game from JSON data stored in file
public class JsonReader {
    private String source;

    private JSONObject jsonObject;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads gameLog from file and returns it;
    // throws IOException if an error occurs reading data from file
    public List<Log> readGameLog() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseLog(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses log from JSON object and returns it
    private List<Log> parseLog(JSONObject jsonObject) {
        List<Log> savedLog = new ArrayList<>();
        addGameLog(savedLog, jsonObject);
        return savedLog;
    }

    // MODIFIES: log
    // EFFECTS: parses log from JSON object and adds them to gameLog
    private void addGameLog(List<Log> savedLog, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("gameLog");
        for (Object json : jsonArray) {
            JSONObject nextLog = (JSONObject) json;
            addLog(savedLog, nextLog);
        }
    }

    // MODIFIES: log
    // EFFECTS: parses log from JSON object and adds it to gameLog
    private void addLog(List<Log> savedLog, JSONObject jsonObject) {
        Boolean won = jsonObject.getBoolean("won");
        Boolean loss = jsonObject.getBoolean("loss");
        int cashLog = jsonObject.getInt("cashLog");
        double difference = jsonObject.getDouble("difference");
        Log log = new Log(won, loss, cashLog, difference);
        savedLog.add(log);
    }

    // EFFECTS: reads source file as Deck and returns it
    public Deck readDeck() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseDeck(jsonObject, "gameDeck");
    }

    // EFFECTS: parses deck from JSON object and returns it
    private Deck parseDeck(JSONObject jsonObject, String key) {
        Deck gameDeck = new Deck();
        addDeck(gameDeck, jsonObject, key);
        return gameDeck;
    }

    // MODIFIES: deck
    // EFFECTS: parses deck from JSON object and adds it to deck
    private void addDeck(Deck deck, JSONObject jsonObject, String key) {
        JSONArray jsonArray = jsonObject.getJSONArray(key);
        for (Object json : jsonArray) {
            JSONObject nextCard = (JSONObject) json;
            addCard(deck, nextCard);
        }
    }

    // MODIFIES: deck
    // EFFECTS: parses card from JSON object and adds it to deck
    private void addCard(Deck deck, JSONObject jsonObject) {
        int number = jsonObject.getInt("number");
        String symbol = jsonObject.getString("symbol");
        String suit = jsonObject.getString("suit");
        Card card = new Card(number, symbol, suit);
        deck.addCard(card);
    }

    // EFFECTS: reads source file as Player and returns it
    public Player readPlayer() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parsePlayer(jsonObject, "player");
    }

    // EFFECTS: parses deck from JSON object and returns it
    private Player parsePlayer(JSONObject jsonObject, String player) {
        Player savedPlayer = new Player();
        addPlayerDeck(savedPlayer, jsonObject, player);
        return savedPlayer;
    }

    // MODIFIES: deck
    // EFFECTS: parses deck from JSON object and adds it to deck
    private void addPlayerDeck(Player savedPlayer, JSONObject jsonObject, String key) {
        JSONArray jsonArray = jsonObject.getJSONArray(key);
        for (Object json : jsonArray) {
            JSONObject nextCard = (JSONObject) json;
            addPlayerCard(savedPlayer, nextCard);
        }
    }

    // MODIFIES: deck
    // EFFECTS: parses card from JSON object and adds it to deck
    private void addPlayerCard(Player savedPlayer, JSONObject jsonObject) {
        int number = jsonObject.getInt("number");
        String symbol = jsonObject.getString("symbol");
        String suit = jsonObject.getString("suit");
        Card card = new Card(number, symbol, suit);
        savedPlayer.addCard(card);
    }

    // EFFECTS: reads source file as Dealer and returns it
    public Dealer readDealer() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseDealer(jsonObject, "dealer");
    }

    // EFFECTS: parses deck from JSON object and returns it
    private Dealer parseDealer(JSONObject jsonObject, String dealer) {
        Dealer savedDealer = new Dealer();
        addDealerDeck(savedDealer, jsonObject, dealer);
        return savedDealer;
    }

    // MODIFIES: deck
    // EFFECTS: parses deck from JSON object and adds it to deck
    private void addDealerDeck(Dealer savedDealer, JSONObject jsonObject, String key) {
        JSONArray jsonArray = jsonObject.getJSONArray(key);
        for (Object json : jsonArray) {
            JSONObject nextCard = (JSONObject) json;
            addDealerCard(savedDealer, nextCard);
        }
    }

    // MODIFIES: deck
    // EFFECTS: parses card from JSON object and adds it to deck
    private void addDealerCard(Dealer savedDealer, JSONObject jsonObject) {
        int number = jsonObject.getInt("number");
        String symbol = jsonObject.getString("symbol");
        String suit = jsonObject.getString("suit");
        Card card = new Card(number, symbol, suit);
        savedDealer.addCard(card);
    }

    //EFFECTS: Sets up a new jsonData to reduce repetition.
    public void setUpJsonObject() throws IOException {
        String jsonData = readFile(source);
        jsonObject = new JSONObject(jsonData);
    }

    // EFFECTS: reads source file as int and returns it
    public int readCash() throws IOException {
        setUpJsonObject();
        return jsonObject.getInt("cash");
    }

    // EFFECTS: reads source file as Deck and returns it
    public int readBettingAmount() throws IOException {
        setUpJsonObject();
        return jsonObject.getInt("bettingAmount");
    }

    // EFFECTS: reads source file as double and returns it
    public double readWins() throws IOException {
        setUpJsonObject();
        return jsonObject.getDouble("wins");
    }

    // EFFECTS: reads source file as double and returns it
    public double readLosses() throws IOException {
        setUpJsonObject();
        return jsonObject.getDouble("losses");
    }

    // EFFECTS: reads source file as boolean and returns it
    public boolean readPlay() throws IOException {
        setUpJsonObject();
        return jsonObject.getBoolean("play");
    }

    // EFFECTS: reads source file as boolean and returns it
    public boolean readStand() throws IOException {
        setUpJsonObject();
        return jsonObject.getBoolean("stand");
    }


}
