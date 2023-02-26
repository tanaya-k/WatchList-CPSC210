package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

//Tests for the watchlist class
class WatchlistTest {
    private Media tvShow;
    private Media tvShow2;
    private Media movie;
    private WatchList emptyWatchList;
    private WatchList emptyWatchListWithName;
    private WatchList watchlistWithCurrentlyWatchingMedia;
    private WatchList watchlistWithOnlyWatchedMedia;
    private WatchList watchlistWithBothMedia;

    @BeforeEach
    void runBefore() {
        tvShow = new Media("Grey's Anatomy", "Drama", true, 2, "Fun.");
        movie = new Media("Shrek", "Comedy", false, 5, "The best!");
        tvShow2 = new Media("Jujutsu Kaisen", "Action Anime", true, 4,
                "Somehow philosophical, brutal and funny at the same time.");
        emptyWatchList = new WatchList();
        emptyWatchListWithName = new WatchList("New Watch List");
        watchlistWithCurrentlyWatchingMedia = new WatchList();
        watchlistWithCurrentlyWatchingMedia.addWatchingMedia(tvShow);
        watchlistWithCurrentlyWatchingMedia.addWatchingMedia(movie);
        watchlistWithOnlyWatchedMedia = new WatchList();
        watchlistWithOnlyWatchedMedia.addWatchedMedia(tvShow);
        watchlistWithOnlyWatchedMedia.addWatchedMedia(movie);
        watchlistWithBothMedia = new WatchList();
        watchlistWithBothMedia.addWatchingMedia(tvShow);
        watchlistWithBothMedia.addWatchedMedia(movie);
    }

    @Test
    void testEmptyConstructor() {
        assertEquals("My Watch List", emptyWatchList.getName());
        assertEquals(0, emptyWatchList.getWatchingList().size());
        assertEquals(0, emptyWatchList.getWatchingList().size());
    }

    @Test
    void testEmptyConstructorWithName() {
        assertEquals("New Watch List", emptyWatchListWithName.getName());
        assertEquals(0, emptyWatchListWithName.getWatchingList().size());
        assertEquals(0, emptyWatchListWithName.getWatchingList().size());
    }

    @Test
    void testNumWatchingList() {
        assertEquals(0, emptyWatchList.numWatchingList());
        assertEquals(2, watchlistWithCurrentlyWatchingMedia.numWatchingList());
        assertEquals(0, watchlistWithOnlyWatchedMedia.numWatchingList());
        assertEquals(1, watchlistWithBothMedia.numWatchingList());
    }

    @Test
    void testNumWatchedList() {
        assertEquals(0, emptyWatchList.numWatchedList());
        assertEquals(0, watchlistWithCurrentlyWatchingMedia.numWatchedList());
        assertEquals(2, watchlistWithOnlyWatchedMedia.numWatchedList());
        assertEquals(1, watchlistWithBothMedia.numWatchedList());
    }

    @Test
    void testAddWatchingMedia() {
        emptyWatchList.addWatchingMedia(tvShow);
        assertEquals(1, emptyWatchList.numWatchingList());
        assertEquals(0, emptyWatchList.numWatchedList());
        assertTrue(emptyWatchList.getWatchingList().contains(tvShow));

        watchlistWithCurrentlyWatchingMedia.addWatchingMedia(tvShow2);
        assertEquals(3, watchlistWithCurrentlyWatchingMedia.numWatchingList());
        assertEquals(0, watchlistWithCurrentlyWatchingMedia.numWatchedList());
        assertTrue(watchlistWithCurrentlyWatchingMedia.getWatchingList().contains(tvShow2));

        watchlistWithOnlyWatchedMedia.addWatchingMedia(tvShow2);
        assertEquals(1, watchlistWithOnlyWatchedMedia.numWatchingList());
        assertEquals(2, watchlistWithOnlyWatchedMedia.numWatchedList());
        assertTrue(watchlistWithOnlyWatchedMedia.getWatchingList().contains(tvShow2));

        watchlistWithBothMedia.addWatchingMedia(tvShow2);
        assertEquals(2, watchlistWithBothMedia.numWatchingList());
        assertEquals(1, watchlistWithBothMedia.numWatchedList());
        assertTrue(watchlistWithBothMedia.getWatchingList().contains(tvShow2));
    }

    @Test
    void testAddWatchedMedia() {
        emptyWatchList.addWatchedMedia(tvShow);
        assertEquals(1, emptyWatchList.numWatchedList());
        assertEquals(0, emptyWatchList.numWatchingList());
        assertTrue(emptyWatchList.getWatchedList().contains(tvShow));

        watchlistWithCurrentlyWatchingMedia.addWatchedMedia(tvShow2);
        assertEquals(1, watchlistWithCurrentlyWatchingMedia.numWatchedList());
        assertEquals(2, watchlistWithCurrentlyWatchingMedia.numWatchingList());
        assertTrue(watchlistWithCurrentlyWatchingMedia.getWatchedList().contains(tvShow2));

        watchlistWithOnlyWatchedMedia.addWatchedMedia(tvShow2);
        assertEquals(3, watchlistWithOnlyWatchedMedia.numWatchedList());
        assertEquals(0, watchlistWithOnlyWatchedMedia.numWatchingList());
        assertTrue(watchlistWithOnlyWatchedMedia.getWatchedList().contains(tvShow2));

        watchlistWithBothMedia.addWatchedMedia(tvShow2);
        assertEquals(2, watchlistWithBothMedia.numWatchedList());
        assertEquals(1, watchlistWithBothMedia.numWatchingList());
        assertTrue(watchlistWithBothMedia.getWatchedList().contains(tvShow2));
    }

    @Test
    void testRemoveWatchingMedia() {
        watchlistWithCurrentlyWatchingMedia.removeWatchingMedia(tvShow);
        assertEquals(1, watchlistWithCurrentlyWatchingMedia.numWatchingList());
        assertEquals(0, watchlistWithCurrentlyWatchingMedia.numWatchedList());
        assertFalse(watchlistWithCurrentlyWatchingMedia.getWatchingList().contains(tvShow));

        watchlistWithBothMedia.removeWatchingMedia(tvShow);
        assertEquals(0, watchlistWithBothMedia.numWatchingList());
        assertEquals(1, watchlistWithBothMedia.numWatchedList());
        assertFalse(watchlistWithBothMedia.getWatchingList().contains(tvShow));
    }

    @Test
    void testRemoveWatchedMedia() {
        watchlistWithOnlyWatchedMedia.removeWatchedMedia(tvShow);
        assertEquals(1, watchlistWithOnlyWatchedMedia.numWatchedList());
        assertEquals(0, watchlistWithOnlyWatchedMedia.numWatchingList());
        assertFalse(watchlistWithOnlyWatchedMedia.getWatchedList().contains(tvShow));

        watchlistWithBothMedia.removeWatchedMedia(movie);
        assertEquals(0, watchlistWithBothMedia.numWatchedList());
        assertEquals(1, watchlistWithBothMedia.numWatchingList());
        assertFalse(watchlistWithBothMedia.getWatchedList().contains(movie));
    }

    @Test
    void testFinishMedia() {
        watchlistWithBothMedia.finishMedia();
        assertEquals(0, watchlistWithBothMedia.numWatchingList());
        assertEquals(2, watchlistWithBothMedia.numWatchedList());

        watchlistWithCurrentlyWatchingMedia.finishMedia();
        assertEquals(1, watchlistWithCurrentlyWatchingMedia.numWatchingList());
        assertEquals(1, watchlistWithCurrentlyWatchingMedia.numWatchedList());
    }

    @Test
    void testIsWatchingList() {
        LinkedList<Media> emptyList = new LinkedList<>();
        assertTrue(emptyWatchList.isWatchingList(emptyList));

        LinkedList<Media> listWithCurrentlyWatchingMedia = new LinkedList<>();
        listWithCurrentlyWatchingMedia.add(tvShow);
        assertTrue(watchlistWithBothMedia.isWatchingList(listWithCurrentlyWatchingMedia));

        listWithCurrentlyWatchingMedia.addFirst(movie);
        assertTrue(watchlistWithCurrentlyWatchingMedia.isWatchingList(listWithCurrentlyWatchingMedia));
    }

    @Test
    void testIsWatchedList() {
        LinkedList<Media> emptyList = new LinkedList<>();
        assertTrue(emptyWatchList.isWatchedList(emptyList));

        LinkedList<Media> listWithFinishedWatchingMedia = new LinkedList<>();
        listWithFinishedWatchingMedia.add(movie);
        assertTrue(watchlistWithBothMedia.isWatchedList(listWithFinishedWatchingMedia));

        listWithFinishedWatchingMedia.add(tvShow);
        assertTrue(watchlistWithOnlyWatchedMedia.isWatchedList(listWithFinishedWatchingMedia));
    }

    @Test
    void testGetMediaFromWatchingList() {
        assertNull(watchlistWithCurrentlyWatchingMedia.getMediaFromWatchingList(tvShow2.getName()));
    }

    @Test
    void testGetMediaFromWatchedList() {
        assertNull(watchlistWithOnlyWatchedMedia.getMediaFromWatchedList(tvShow2.getName()));
    }
}