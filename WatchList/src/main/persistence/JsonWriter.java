package persistence;

import model.WatchList;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

//CITATION: Carter, Paul (2021) JSON Serialization Demo (Version 20210307) [Source Code].
//https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/commit/d31979d8a993d63c3a8c13c8add7f9d1753777b6

//Represents a reader that reads the JSON objects in a file for a watch list
public class JsonWriter {
    public static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(destination);
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of watch list to file
    public void write(WatchList watchList) {
        JSONObject jsonObject = watchList.toJson();
        saveToFile(jsonObject.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    public void saveToFile(String jsonObject) {
        writer.print(jsonObject);
    }
}