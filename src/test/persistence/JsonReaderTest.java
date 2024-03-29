package persistence;

import model.GameManager;
import model.GameStatManager;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            GameManager game = reader.readManageGame();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderGame() {
        JsonReader reader = new JsonReader("./data/testEmptyGameLog.json");
        try {
            GameManager gm = reader.readManageGame();
            assertEquals(500, gm.getCash());
            assertNull(gm.getPlay());
            //assertNull(gm.isStand());
            assertEquals(0, gm.getBettingAmount());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGameStat() {
        JsonReader reader = new JsonReader("./data/testFullGameLog.json");
        try {
            GameStatManager gsm = reader.readManageStats();
            assertEquals(0, gsm.getGameLog().size());
            assertEquals(0, gsm.getWins());
            assertEquals(0, gsm.getLosses());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

}
