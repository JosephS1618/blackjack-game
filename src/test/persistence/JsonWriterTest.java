package persistence;

import model.Deck;
import model.Log;
import org.junit.jupiter.api.Test;
import ui.Game;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JsonWriterTest {
    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyGameLog() {
        try {
            Game game = new Game();
            game.setUpNewGame(1);
            JsonWriter writer = new JsonWriter("./data/testEmptyGameLog.json");
            writer.open();
            writer.writeGame(game);
            writer.close();

            JsonReader reader = new JsonReader("./data/testEmptyGameLog.json");
            List<Log> gameLog = reader.readGameLog();
            assertEquals(0, gameLog.size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriteDeck() {
        try {
            Game game = new Game();
            game.setUpNewGame(1);
            JsonWriter writer = new JsonWriter("./data/testWriteDeck.json");
            writer.open();
            writer.writeGame(game);
            writer.close();

            JsonReader reader = new JsonReader("./data/testEmptyGameLog.json");
            List<Log> gameLog = reader.readGameLog();
            assertEquals(0, gameLog.size());
            assertEquals(0, reader.readWins());
            assertTrue(reader.readPlay());
            assertEquals(0, reader.readLosses());
            assertFalse(reader.readStand());
            assertEquals(500, reader.readCash());
            assertEquals(0, reader.readBettingAmount());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }


}
