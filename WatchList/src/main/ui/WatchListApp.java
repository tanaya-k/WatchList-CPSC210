package ui;

import model.WatchList;

/**
 * Runs the application to manage watch lists
  */
public class WatchListApp {
    private WatchList myWatchlist;
    private WatchListFrame watchListFrame;

    //EFFECTS: runs the watchlist application
    public WatchListApp() {
        runWatchListApp();
    }

    //MODIFIES: this
    //EFFECTS: processes user input
    private void runWatchListApp() {
        myWatchlist = new WatchList("My Watch List");
        watchListFrame = new WatchListFrame(myWatchlist);
    }
}