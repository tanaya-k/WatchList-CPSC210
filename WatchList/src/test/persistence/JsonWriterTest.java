package persistence;

import model.Media;
import model.WatchList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest extends JsonTest {
    protected Media movie;
    protected Media tvShow;

    @BeforeEach
    void runBefore() {
        movie = new Media("Saw", "Thriller", false, 3, "good");
        tvShow = new Media("Arthur", "Cartoon", true, 3, "fun");
    }

    @Test
    void testWriterInvalidFile() {
        try {
            WatchList wl = new WatchList("My Watch List");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            WatchList wl = new WatchList("My Watch List");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyWatchList.json");
            writer.open();
            writer.write(wl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyWatchList.json");
            wl = reader.read();
            assertEquals(0, wl.numWatchingList());
            assertEquals(0, wl.numWatchedList());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            WatchList wl = new WatchList("My Watch List");
            wl.addWatchingMedia(movie);
            wl.addWatchedMedia(tvShow);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralWatchList.json");
            writer.open();
            writer.write(wl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralWatchList.json");
            wl = reader.read();
            assertEquals("My Watch List", wl.getName());
            List<Media> mediaList = wl.getWatchingList();
            List<Media> mediaList2 = wl.getWatchedList();
            assertEquals(1, mediaList.size());
            assertEquals(1, mediaList2.size());
            checkMedia("Saw", movie);
            checkMedia("Arthur", tvShow);

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}