package persistence;

import model.Log;
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

// Represents a reader that reads workroom from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public List<Log> readGameLog() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseGame(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses workroom from JSON object and returns it
    private List<Log> parseGame(JSONObject jsonObject) throws FileNotFoundException {
        List<Log> savedLog = new ArrayList<>();
        addGameLog(savedLog, jsonObject);
//        savedLog.setCash(jsonObject.getInt("cash"));
//        savedLog.setWins(jsonObject.getDouble("wins"));
//        savedLog.setLosses(jsonObject.getDouble("losses"));
//        savedLog.setPlay(jsonObject.getBoolean("play"));
//        savedLog.setStand(jsonObject.getBoolean("stand"));
        return savedLog;
    }

    // MODIFIES: game
    // EFFECTS: parses thingies from JSON object and adds them to workroom
    private void addGameLog(List<Log> savedLog, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("gameLog");
        for (Object json : jsonArray) {
            JSONObject nextLog = (JSONObject) json;
            addLog(savedLog, nextLog);
        }
    }

    // MODIFIES: game
    // EFFECTS: parses thingy from JSON object and adds it to workroom
    private void addLog(List<Log> savedLog, JSONObject jsonObject) {
        Boolean won = jsonObject.getBoolean("won");
        Boolean loss = jsonObject.getBoolean("loss");
        int cashLog = jsonObject.getInt("cashLog");
        double difference = jsonObject.getDouble("difference");
        Log log = new Log(won, loss, cashLog, difference);
        savedLog.add(log);
    }
}
