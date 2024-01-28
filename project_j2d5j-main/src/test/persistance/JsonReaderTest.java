package persistance;


import model.Log;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

//citation: example application Json serialization demo:
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonReaderTest {

    @Test
    void testJsonReader() {
        JsonReader reader = new JsonReader("./data/testReaderLog.json");

        try {
            Log testLog = reader.read();
            assertEquals(2, testLog.getLogOfDays().size());

        } catch (IOException e) {
            fail("unexpected exception thrown");
        }
    }

    @Test
    void testJsonReaderEmptyLog() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyLog.json");

        try {
            Log testLog = reader.read();
            assertEquals(0, testLog.getLogOfDays().size());
        } catch (IOException e) {
            fail("unexpected exception thrown");
        }
    }

    @Test
    void testJsonReaderDoesNotExist() {
        JsonReader reader = new JsonReader("./data/DoesNotExist.json");

        try {
            reader.read();
            fail("expected exception not thrown");
        } catch (IOException e) {
            //expected
        }
    }
}
