package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.LinkedList;

//Represents the lists that record the media watched and the media currently watching by user
public class WatchList implements Writable {
    private String name;                             //name of watchlist
    private LinkedList<Media> currentlyWatchingList; //list of media watching now, most recently watched at the top
    private LinkedList<Media> finishedWatchingList;  //list of watched media, most recently finished at the bottom
    private Media recentlyFinishedWatching;          //last

    //MODIFIES: this
    //EFFECTS: constructs a watch list with two empty lists
    public WatchList() {
        name = "My Watch List";
        currentlyWatchingList = new LinkedList<>();
        finishedWatchingList = new LinkedList<>();
    }

    //MODIFIES: this
    //EFFECTS: constructs a watch list with a name and two empty lists
    public WatchList(String name) {
        this.name = name;
        currentlyWatchingList = new LinkedList<>();
        finishedWatchingList = new LinkedList<>();
    }

    //EFFECTS: retrieves watch list name
    public String getName() {
        return name;
    }

    //EFFECTS: retrieves watching list
    public LinkedList<Media> getWatchingList() {
        return currentlyWatchingList;
    }

    //EFFECTS: retrieves watched list
    public LinkedList<Media> getWatchedList() {
        return finishedWatchingList;
    }

    //EFFECTS: returns the number of media in the watching list
    public int numWatchingList() {
        return currentlyWatchingList.size();
    }

    //EFFECTS: returns the number of media in the watched list
    public int numWatchedList() {
        return finishedWatchingList.size();
    }

    // MODIFIES: this
    // EFFECTS: adds specified media to the front of watching list and adds a log of the event
    public void addWatchingMedia(Media mediaCard) {
        currentlyWatchingList.addFirst(mediaCard);
        EventLog.getInstance().logEvent(new Event("Added media to currently watching list."));
    }

    //REQUIRES: watching list is a non-empty list
    //MODIFIES: this
    //EFFECTS: removes specified media from the watching list if it exists and adds a log of the event,
    //otherwise does nothing
    public void removeWatchingMedia(Media mediaCard) {
        boolean containsMedia = currentlyWatchingList.contains(mediaCard);
        currentlyWatchingList.remove(mediaCard);
        if (containsMedia) {
            EventLog.getInstance().logEvent(new Event("Removed media from currently watching list."));
        }
    }

    //MODIFIES: this
    //EFFECTS: adds specified media to front of watching list
    public void addWatchedMedia(Media mediaCard) {
        finishedWatchingList.addFirst(mediaCard);
    }

    //REQUIRES: watched list is a non-empty list
    //MODIFIES: this
    //EFFECTS: removes specified media from the watching list if it exists, otherwise does nothing
    public void removeWatchedMedia(Media mediaCard) {
        finishedWatchingList.remove(mediaCard);
    }

    //REQUIRES: watching list is a non-empty list
    // MODIFIES: this
    // EFFECTS: takes the media from the start of watching list and transfers it to watched list
    // and adds a log of the event
    public void finishMedia() {
        recentlyFinishedWatching = currentlyWatchingList.getFirst();
        currentlyWatchingList.remove(recentlyFinishedWatching);
        finishedWatchingList.addLast(recentlyFinishedWatching);
        EventLog.getInstance().logEvent(new Event("Finished media in currently watching list."));
    }

    //EFFECTS: returns the media from watching list under the given title
    public Media getMediaFromWatchingList(String name) {
        for (Media media : currentlyWatchingList) {
            if (media.getName().equals(name)) {
                return media;
            }
        }
        return null;
    }

    //EFFECTS: returns the media from watched list under the given title
    public Media getMediaFromWatchedList(String name) {
        for (Media media : finishedWatchingList) {
            if (media.getName().equals(name)) {
                return media;
            }
        }
        return null;
    }

    //EFFECTS: instantiates watch list as a JSON object, adds an event to the log
    @Override
    public JSONObject toJson() {
        JSONObject watchListJsonObject = new JSONObject();
        watchListJsonObject.put("name", name);
        watchListJsonObject.put("currentlyWatchingList", currentlyWatchingMediaToJson());
        watchListJsonObject.put("finishedWatchingList", finishedWatchingMediaToJson());
        EventLog.getInstance().logEvent(new Event("Saved watch list."));
        return watchListJsonObject;
    }

    // EFFECTS: returns media in currently watching list as a JSON array
    private JSONArray currentlyWatchingMediaToJson() {
        JSONArray currentlyWatchingJsonArray = new JSONArray();
        for (Media media : currentlyWatchingList) {
            currentlyWatchingJsonArray.put(media.toJson());
        }
        return currentlyWatchingJsonArray;
    }

    //EFFECTS: returns media in finished watching list as a JSON array
    private JSONArray finishedWatchingMediaToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Media media : finishedWatchingList) {
            jsonArray.put(media.toJson());
        }

        return jsonArray;
    }

    //EFFECTS: returns true if given list is equal to currently watching list
    public boolean isWatchingList(LinkedList<Media> list) {
        return list.equals(currentlyWatchingList);
    }

    //EFFECTS: returns true if given list is equal to finished watching list
    public boolean isWatchedList(LinkedList<Media> list) {
        return list.equals(finishedWatchingList);
    }
}