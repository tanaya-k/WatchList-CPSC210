package persistence;

import model.Media;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkMedia(String name, Media media) {
        assertEquals(name, media.getName());
    }
}
