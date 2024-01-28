package persistance;

import model.Day;
import model.Event;
import model.EventLog;
import model.Log;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;


//Represents a reader that reads a log from the JSON data stored in the JSON file
//citation: example application Json serialization demo:
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonReader {
    private String file;

    //EFFECTS: creates a JsonReader with a file to read from
    public JsonReader(String file) {
        this.file = file;
    }

    //EFFECTS: returns the data in the file as a Log and logs event or
    // throws an IOException if file can't be read
    public Log read() throws IOException {
        String jsonData = readFile(file);
        JSONObject jsonObject = new JSONObject(jsonData);
        EventLog.getInstance().logEvent(new Event("Loaded log from file"));
        return parseLog(jsonObject);
    }

    //EFFECTS: reads the source file as a string and returns it
    private String readFile(String file) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(file), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }
        return contentBuilder.toString();
    }

    //MODIFIES: l
    //EFFECTS: parses a log out of the JSON object
    private Log parseLog(JSONObject jsonObject) {
        Log l = new Log();
        addDays(l, jsonObject);
        return l;
    }

    //MODIFIES: l
    //EFFECTS: adds all of the Day objects in the file to the log
    private void addDays(Log l, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("logOfDays");
        for (Object json: jsonArray) {
            JSONObject nextDay = (JSONObject) json;
            addDay(l, nextDay);
        }
    }

    //MODIFIES: l
    //EFFECTS: creates a Day out of the data in the file and adds it to the log
    private void addDay(Log l, JSONObject jsonObject) {
        String date = jsonObject.getString("date");
        boolean wentToGym = jsonObject.getBoolean("wentToGym");
        int glassesOfWater = jsonObject.getInt("glassesOfWater");
        int hoursOnPhone = jsonObject.getInt("hoursOnPhone");
        int mood = jsonObject.getInt("mood");
        int pagesRead = jsonObject.getInt("pagesRead");

        Day d = new Day();
        d.setDate(date);
        d.drinkWater(glassesOfWater);
        if (wentToGym) {
            d.goToGym();
        }
        d.timeOnPhone(hoursOnPhone);
        d.readPages(pagesRead);
        d.trackMood(mood);
        l.addDayFromJson(d);
    }


}


