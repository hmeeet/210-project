package persistance;

import model.Day;
import model.Log;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;


//citation: example application Json serialization demo:
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonWriterTest {
    Log testLog;
    Day d1;
    Day d2;
    Day d3;

    @BeforeEach
    void runBefore() {
        testLog = new Log();

        d1 = new Day();
        d1.setDate("2023-01-02");
        d1.drinkWater(6);
        d1.goToGym();
        d1.timeOnPhone(2);
        d1.readPages(20);
        d1.trackMood(4);

        d2 = new Day();
        d2.setDate("2023-08-25");
        d2.drinkWater(3);
        d2.timeOnPhone(0);
        d2.readPages(13);
        d2.trackMood(1);

        d3 = new Day();
        d3.setDate("2023-08-15");
        d3.drinkWater(1);
        d3.timeOnPhone(7);
        d3.goToGym();
        d3.readPages(100);
        d3.trackMood(3);
    }

    @Test
    void testJsonWriter() {
        testLog.addDay(d1);
        testLog.addDay(d2);
        testLog.addDay(d3);

        try {
            JsonWriter writer = new JsonWriter("./data/testWriterLog.json");
            writer.open();
            writer.write(testLog);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterLog.json");
            testLog = reader.read();
            assertEquals(3, testLog.getLogOfDays().size());

        } catch (IOException e) {
            fail("unexpected exception thrown");
        }

    }

    @Test
    void testJsonWriterEmptyLog() {
        try {
            JsonWriter writer = new JsonWriter("./data/testWriterLog.json");
            writer.open();
            writer.write(testLog);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterLog.json");
            testLog = reader.read();
            assertEquals(0, testLog.getLogOfDays().size());

        } catch (IOException e) {
            fail("unexpected exception thrown");
        }
    }

    @Test
    void testJsonWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/test\0:badName.json");
            writer.open();
            fail("expected exception not thrown");

        } catch (FileNotFoundException e) {
            //expected
        }

    }






}
