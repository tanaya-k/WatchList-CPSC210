package persistence;

import model.Event;
import model.EventLog;
import model.Media;
import model.WatchList;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

//CITATION: Carter, Paul (2021) JSON Serialization Demo (Version 20210307) [Source Code].
//https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/commit/d31979d8a993d63c3a8c13c8add7f9d1753777b6

//Represents a reader that reads the JSON objects in a file for a watch list

public class JsonReader {
    private String source;

    //MODIFIES: this
    //EFFECTS: constructs a new reader that points to given source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads watch list from file and returns it;
    // throws IOException if an error occurs reading data from file
    public WatchList read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseWatchList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }
        return contentBuilder.toString();
    }

    // EFFECTS: parses watch list from JSON object,
    // adds an event to the event log and returns it
    private WatchList parseWatchList(JSONObject jsonObject) {
        JSONArray watchingJsonArray = jsonObject.getJSONArray("currentlyWatchingList");
        JSONArray watchedJsonArray = jsonObject.getJSONArray("finishedWatchingList");
        String name = jsonObject.getString("name");
        WatchList watchList = new WatchList(name);
        addWatchingList(watchList, watchingJsonArray);
        addWatchedList(watchList, watchedJsonArray);
        EventLog.getInstance().logEvent(new Event("Loaded watch list."));
        return watchList;
    }

    // MODIFIES: watchList
    // EFFECTS: parses watching list from JSON array and adds them to watch list
    private void addWatchingList(WatchList watchList, JSONArray jsonArray) {
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject nextMedia = jsonArray.getJSONObject(i);
            addOneMediaInWatchingList(watchList, nextMedia);
        }
    }

    // MODIFIES: watchList
    // EFFECTS: parses watched list from JSON array and adds them to watch list
    private void addWatchedList(WatchList watchList, JSONArray jsonArray) {
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject nextMedia = jsonArray.getJSONObject(i);
            addOneMediaInWatchedList(watchList, nextMedia);
        }
    }

    // MODIFIES: watchList
    // EFFECTS: parses watching media from JSON object and adds it to watch list
    private void addOneMediaInWatchingList(WatchList watchList, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String genre = jsonObject.getString("genre");
        boolean type = jsonObject.getBoolean("type");
        int rating = jsonObject.getInt("rating");
        String review = jsonObject.getString("review");
        Media media = new Media(name, genre, type, rating, review);
        watchList.addWatchingMedia(media);
    }

    // MODIFIES: watchList
    // EFFECTS: parses watched media from JSON object and adds it to watch list
    private void addOneMediaInWatchedList(WatchList watchListBranch, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String genre = jsonObject.getString("genre");
        boolean type = jsonObject.getBoolean("type");
        int rating = jsonObject.getInt("rating");
        String review = jsonObject.getString("review");
        Media media = new Media(name, genre, type, rating, review);
        watchListBranch.addWatchedMedia(media);
    }
}