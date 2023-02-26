package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents one piece of media with a title, genre, type (movie or tv show), rating out of 5, and review
public class Media implements Writable {
    private static final int MIN_RATING = 1;  // lowest int for rating
    private static final int MAX_RATING = 5;  // highest int for rating

    private String name;                      // name of media
    private String genre;                     // genre of media
    private boolean type;                     // if false, media is a movie, else tv show
    private int rating;                       // how much the user likes the media from 1 to 5
    private String review;                    // user's review of the media

    // REQUIRES: mediaName string is not empty
    // EFFECTS: initializes media with name, genre, rating, media
    public Media(String mediaName, String mediaGenre, boolean mediaType, int mediaRating, String mediaReview) {
        name = mediaName;
        genre = mediaGenre;
        type = mediaType;
        rating = mediaRating;
        review = mediaReview;
    }

    public String getName() {
        return name;
    }

    public String getGenre() {
        return genre;
    }

    public boolean getType() {
        return type;
    }

    public int getRating() {
        return rating;
    }

    public String getReview() {
        return review;
    }

    public void setName(String mediaName) {
        name = mediaName;
    }

    public void setGenre(String mediaGenre) {
        genre = mediaGenre;
    }

    public void setType(boolean mediaType) {
        type = mediaType;
    }

    public void setRating(int mediaRating) {
        rating = mediaRating;
    }

    public void setReview(String mediaReview) {
        review = mediaReview;
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", name);
        jsonObject.put("genre", genre);
        jsonObject.put("type", type);
        jsonObject.put("rating", rating);
        jsonObject.put("review", review);
        return jsonObject;
    }
}