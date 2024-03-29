package persistence;

import model.Deck;
import model.GameManager;
import model.GameStatManager;
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
    void testWriterGameManager() {
        try {
            Game game = new Game();
            game.getManageGame().setUpNewGame(1);
            JsonWriter writer = new JsonWriter("./data/testEmptyGameLog.json");
            writer.open();
            writer.write(game);
            writer.close();

            JsonReader reader = new JsonReader("./data/testEmptyGameLog.json");

            GameManager gm = reader.readManageGame();
            assertEquals(500, gm.getCash());
            assertNull(gm.getPlay());
            //assertNull(gm.isStand());
            assertEquals(0, gm.getBettingAmount());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriteGameStats() {
        try {
            Game game = new Game();
            game.getManageGame().setUpNewGame(1);
            JsonWriter writer = new JsonWriter("./data/testFullGameLog.json");
            writer.open();
            writer.write(game);
            writer.close();

            JsonReader reader = new JsonReader("./data/testFullGameLog.json");

            GameStatManager gsm = reader.readManageStats();

            assertEquals(0, gsm.getGameLog().size());
            assertEquals(0, gsm.getWins());
            assertEquals(0, gsm.getLosses());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }


}
