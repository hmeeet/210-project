package persistance;

import model.Event;
import model.EventLog;
import model.Log;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

//Represents a writer that writes a JSON representation of the log to the file
//citation: example application Json serialization demo:
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonWriter {
    private PrintWriter writer;
    private String dest;
    private static final int TAB = 4;

    //EFFECTS: creates a new JsonWriter with a destination file
    public JsonWriter(String dest) {
        this.dest = dest;
    }

    //MODIFIES: this
    //EFFECTS: writes the userLog to the json file and logs event
    public void write(Log userLog) {
        JSONObject json = userLog.toJson();
        saveToFile(json.toString(TAB));
        EventLog.getInstance().logEvent(new Event("Saved today's log to file"));
    }

    //MODIFIES: this
    //EFFECTS: opens a new PrintWriter  or throws FileNotFound exception if file isn't found
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(dest));
    }

    //MODIFIES: this
    //EFFECTS: writes the string in the destination file
    public void saveToFile(String json) {
        writer.print(json);
    }

    //MODIFIES: this
    //EFFECTS: closes the writer
    public void close() {
        writer.close();
    }
}
