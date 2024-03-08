package persistence;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            int cash = reader.readCash();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyGameLog() {
        JsonReader reader = new JsonReader("./data/testEmptyGameLog.json");
        try {
            assertEquals(0, reader.readGameLog().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderFullGameLog() {
        JsonReader reader = new JsonReader("./data/testFullGameLog.json");
        try {
            assertEquals(6 , reader.readGameLog().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralGame() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralGame.json");
        try {
            assertEquals(6, reader.readWins());
            assertFalse(reader.readPlay());
            assertEquals(0, reader.readLosses());
            assertTrue(reader.readStand());
            assertEquals(2200, reader.readCash());
            assertEquals(400, reader.readBettingAmount());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }






}
