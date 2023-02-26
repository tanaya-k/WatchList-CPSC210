package ui;

import model.Event;
import model.EventLog;
import model.Media;
import model.WatchList;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

import static java.lang.Boolean.parseBoolean;
import static java.lang.Integer.parseInt;

/*
  Constructs a split pane watch list
 */

/**
 * SOURCES:
 * Using Swing Components: https://docs.oracle.com/javase/tutorial/uiswing/examples/components/index.html#DialogDemo
 * for panel implementation, for split pane implementation, for option pane and dialog implementation
 * for table implementation, for button implementation,
 * for JSON saving and loading, for action listener implementation
 */
public class WatchListPanel extends JPanel implements ActionListener {
    private static final String JSON_STORE = "./data/watchList.json";
    private final ImageIcon savingImage;
    private final ImageIcon loadingImage;
    private final String[] columnNames = {"Title", "Genre", "Movie/TV", "Rating", "Review"};

    private JSplitPane watchListPane;
    private JPanel currentlyWatchingPanel;
    private JPanel finishedWatchingPanel;
    private JButton addMedia;
    private JButton deleteMedia;
    private JButton finishMedia;
    private JButton exitApp;
    private WatchList watchList;
    private JTable currentlyWatchingTable;
    private JTable finishedWatchingTable;
    private DefaultTableModel cwTableModel;
    private DefaultTableModel fwTableModel;
    private JsonWriter writer;
    private JsonReader reader;

    //EFFECTS: constructs a watch list panel
    public WatchListPanel(WatchList watchList) {
        super(new BorderLayout());
        this.watchList = watchList;
        currentlyWatchingPanel = new JPanel();
        finishedWatchingPanel = new JPanel();
        watchListPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, currentlyWatchingPanel, finishedWatchingPanel);
        watchListPane.setContinuousLayout(true);
        watchListPane.setOneTouchExpandable(true);
        reader = new JsonReader(JSON_STORE);
        writer = new JsonWriter(JSON_STORE);
        constructComponents();
        add(watchListPane, BorderLayout.CENTER);
        add(createControlPanel(), BorderLayout.PAGE_END);
        watchListPane.setDividerLocation(1000);
        addCurrentlyWatchingMediaToPanel();
        addFinishedWatchingMediaToPanel();
        savingImage = new ImageIcon("data/savingImage.png");
        loadingImage = new ImageIcon("data/loadingImage.png");
    }

    //EFFECTS: constructs the components of the watch list panel
    private void constructComponents() {
        cwTableModel = new DefaultTableModel(columnNames, watchList.getWatchingList().size());
        currentlyWatchingTable = new JTable(cwTableModel);
        cwTableModel.addRow(columnNames);
        addCurrentlyWatchingMediaToPanel();
        watchListPane.setLeftComponent(currentlyWatchingTable);
        fwTableModel = new DefaultTableModel(columnNames, watchList.getWatchedList().size());
        finishedWatchingTable = new JTable(fwTableModel);
        fwTableModel.addRow(columnNames);
        addFinishedWatchingMediaToPanel();
        watchListPane.setRightComponent(finishedWatchingTable);
    }

    //EFFECTS: constructs and returns the button panel
    private JComponent createControlPanel() {
        JPanel buttonPanel = new JPanel();
        addMedia = new JButton("Add");
        addMedia.addActionListener(this);
        buttonPanel.add(addMedia);
        deleteMedia = new JButton("Delete");
        deleteMedia.addActionListener(this);
        buttonPanel.add(deleteMedia);
        finishMedia = new JButton("Finish");
        finishMedia.addActionListener(this);
        buttonPanel.add(finishMedia);
        exitApp = new JButton("Exit");
        exitApp.addActionListener(this);
        buttonPanel.add(exitApp);
        return buttonPanel;
    }

    //MODIFIES: watchList, this
    //EFFECTS: constructs the dialog triggered with the add button
    private void createAddDialog() {
        String title = JOptionPane.showInputDialog("Title to add?");
        String genre = JOptionPane.showInputDialog("Genre of the media: ");
        boolean type = parseBoolean(JOptionPane.showInputDialog("Type true if the media is a TV Show"));
        int rating = parseInt(JOptionPane.showInputDialog("Rating out of 5: "));
        String review = JOptionPane.showInputDialog("Review: ");
        Media media = new Media(title, genre, type, rating, review);
        watchList.addWatchingMedia(media);
        addOneCurrentlyWatchingMedia(media);
    }

    //MODIFIES: watchList, this
    //EFFECTS: constructs the dialog triggered with the delete button
    private void createDeleteDialog() {
        String title = JOptionPane.showInputDialog("Title to remove?");
        Media mediaToRemove = watchList.getMediaFromWatchingList(title);
        watchList.removeWatchingMedia(mediaToRemove);
        removeOneCurrentlyWatchingMedia(mediaToRemove);
    }

    //MODIFIES: watchList, this
    //EFFECTS: constructs the dialog triggered with the finish button
    private void createFinishDialog() {
        JOptionPane.showMessageDialog(null, "Latest media moved to Finished Watching list!");
        Media firstCurrentlyWatching = watchList.getWatchingList().get(0);
        watchList.finishMedia();
        addOneFinishedWatchingMedia(firstCurrentlyWatching);
    }

    //MODIFIES: this
    //EFFECTS: quits application and outputs the events done
    private void createExitDialog() {
        JOptionPane.showMessageDialog(null, "Exiting application...");
        for (Event e : EventLog.getInstance()) {
            System.out.println(e.getDescription());
        }
        System.exit(0);
    }

    //MODIFIES: writer
    //EFFECTS: constructs the dialog triggered with the finish button and saves the lists
    protected void createSavePopUp() {
        JOptionPane.showConfirmDialog(null,
                "Would you like to save?",
                "Save Watch List",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        try {
            writer.open();
            writer.write(watchList);
            writer.close();
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null,
                    "Unable to store file to: " + JSON_STORE,
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
        JOptionPane.showMessageDialog(null, savingImage);
    }

    //MODIFIES: watchList, this
    //EFFECTS: constructs the dialog triggered with the finish button and loads the lists
    protected void createLoadPopUp() {
        JOptionPane.showConfirmDialog(null,
                "Would you like to load the previous watch list?",
                "Load Watch List",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        try {
            watchList = reader.read();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                    "Unable to load file from: " + JSON_STORE,
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
        JOptionPane.showMessageDialog(null, loadingImage);
        addCurrentlyWatchingMediaToPanel();
        addFinishedWatchingMediaToPanel();
    }


    @Override
    //EFFECTS: processes the actions done on the panel
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (addMedia.equals(source)) {
            createAddDialog();
        }
        if (deleteMedia.equals(source)) {
            createDeleteDialog();
        }
        if (finishMedia.equals(source)) {
            createFinishDialog();
        }
        if (exitApp.equals(source)) {
            createExitDialog();
        }
    }

    //MODIFIES: this
    //EFFECTS: adds currently watching list contents to panel
    private void addCurrentlyWatchingMediaToPanel() {
        currentlyWatchingPanel = new JPanel();
        currentlyWatchingPanel.setLayout(new GridLayout());
        cwTableModel = (DefaultTableModel) currentlyWatchingTable.getModel();
        for (int i = 0; i < watchList.getWatchingList().size(); i++) {
            String title = watchList.getWatchingList().get(i).getName();
            String genre = watchList.getWatchingList().get(i).getGenre();
            String type;
            if (watchList.getWatchingList().get(i).getType()) {
                type = "TV Show";
            } else {
                type = "Movie";
            }
            String rating = String.valueOf(watchList.getWatchingList().get(i).getRating());
            String review = watchList.getWatchingList().get(i).getReview();

            cwTableModel.addRow(new String[]{title, genre, type, rating, review});
        }
    }

    //MODIFIES: this
    //EFFECTS: adds finished watching list contents to panel
    private void addFinishedWatchingMediaToPanel() {
        finishedWatchingPanel.setLayout(new GridLayout());
        fwTableModel = (DefaultTableModel) finishedWatchingTable.getModel();
        for (int i = 0; i < watchList.getWatchedList().size(); i++) {
            String title = watchList.getWatchedList().get(i).getName();
            String genre = watchList.getWatchedList().get(i).getGenre();
            String type;
            if (watchList.getWatchedList().get(i).getType()) {
                type = "TV Show";
            } else {
                type = "Movie";
            }
            String rating = String.valueOf(watchList.getWatchedList().get(i).getRating());
            String review = watchList.getWatchedList().get(i).getReview();

            fwTableModel.addRow(new String[]{title, genre, type, rating, review});
        }
    }

    //MODIFIES: this
    //EFFECTS: adds one media to currently watching list in panel
    private void addOneCurrentlyWatchingMedia(Media media) {
        String title = media.getName();
        String genre = media.getGenre();
        String type;
        if (media.getType()) {
            type = "TV Show";
        } else {
            type = "Movie";
        }
        String rating = String.valueOf(media.getRating());
        String review = media.getReview();

        cwTableModel.addRow(new String[]{title, genre, type, rating, review});
    }

    //MODIFIES: this
    //EFFECTS: adds one media to finished watching list in panel
    private void addOneFinishedWatchingMedia(Media media) {
        String title = media.getName();
        String genre = media.getGenre();
        String type;
        if (media.getType()) {
            type = "TV Show";
        } else {
            type = "Movie";
        }
        String rating = String.valueOf(media.getRating());
        String review = media.getReview();

        fwTableModel.addRow(new String[]{title, genre, type, rating, review});
        removeOneCurrentlyWatchingMedia(media);
    }

    //MODIFIES: this
    //EFFECTS: removes one media from currently watching list in panel
    private void removeOneCurrentlyWatchingMedia(Media media) {
        for (int i = 0; i < cwTableModel.getRowCount(); i++) {
            if (cwTableModel.getValueAt(i, 0).equals(media.getName())) {
                cwTableModel.removeRow(i);
            }
        }
    }
}
