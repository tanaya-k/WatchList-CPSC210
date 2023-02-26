package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Tests for the methods in Media class
public class MediaTest {
    Media media;
    String mediaName;
    String mediaGenre;
    boolean mediaType;
    int mediaRating;
    String mediaReview;

    @BeforeEach
    void runBefore() {
        mediaName = "American Psycho";
        mediaGenre = "Thriller";
        mediaType = true;
        mediaRating = 3;
        mediaReview = "Mid";
        media = new Media(mediaName, mediaGenre, mediaType, mediaRating, mediaReview);
    }

    @Test
    void testConstructor() {
        assertEquals("American Psycho", media.getName());
        assertEquals("Thriller", media.getGenre());
        assertTrue(media.getType());
        assertEquals(3, media.getRating());
        assertEquals("Mid", media.getReview());
    }

    @Test
    void testSetName() {
        media.setName("My Little Pony");
        assertEquals("My Little Pony", media.getName());
        assertEquals("Thriller", media.getGenre());
        assertTrue(media.getType());
        assertEquals(3, media.getRating());
        assertEquals("Mid", media.getReview());
    }

    @Test
    void testSetGenre() {
        media.setGenre("Drama");
        assertEquals("American Psycho", media.getName());
        assertEquals("Drama", media.getGenre());
        assertTrue(media.getType());
        assertEquals(3, media.getRating());
        assertEquals("Mid", media.getReview());
    }

    @Test
    void testSetType() {
        media.setType(false);
        assertEquals("American Psycho", media.getName());
        assertEquals("Thriller", media.getGenre());
        assertFalse(media.getType());
        assertEquals(3, media.getRating());
        assertEquals("Mid", media.getReview());
    }

    @Test
    void testSetRating() {
        media.setRating(5);
        assertEquals("American Psycho", media.getName());
        assertEquals("Thriller", media.getGenre());
        assertTrue(media.getType());
        assertEquals(5, media.getRating());
        assertEquals("Mid", media.getReview());
    }

    @Test
    void testSetReview() {
        media.setReview("Good");
        assertEquals("American Psycho", media.getName());
        assertEquals("Thriller", media.getGenre());
        assertTrue(media.getType());
        assertEquals(3, media.getRating());
        assertEquals("Good", media.getReview());
    }
}