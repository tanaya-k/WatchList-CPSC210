package persistence;

import model.WatchList;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            WatchList wl = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyWatchList.json");
        try {
            WatchList wl = reader.read();
            assertEquals("My Watch List", wl.getName());
            assertEquals(0, wl.numWatchingList());
            assertEquals(0, wl.numWatchedList());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWatchList() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralWatchList.json");
        try {
            WatchList wl = reader.read();
            assertEquals("My Watch List", wl.getName());
            assertEquals(2, wl.numWatchingList());
            checkMedia("The Batman", wl.getMediaFromWatchingList("The Batman"));
            checkMedia("The Mandalorian", wl.getMediaFromWatchingList("The Mandalorian"));
            assertEquals(1, wl.numWatchedList());
            checkMedia("Sing 2", wl.getMediaFromWatchedList("Sing 2"));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}