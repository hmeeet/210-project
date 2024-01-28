package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class LogTest {
    private Log testLog;
    private Day d1;
    private Day d2;
    private Day d3;
    private Day d4;
    private Day d5;
    private Day d6;
    private Day d7;

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

        d4 = new Day();
        d4.setDate("2023-08-30");
        d4.drinkWater(8);
        d4.timeOnPhone(1);
        d4.goToGym();
        d4.trackMood(5);

        d5 = new Day();
        d5.setDate("2023-10-01");

        d6 = new Day();
        d6.setDate("2023-03-01");
        d6.trackMood(5);

        d7 = new Day();
        d7.setDate("2023-04-01");
        d7.trackMood(1);
    }


    @Test
    void testConstructor() {
        assertTrue(testLog.getLogOfDays().isEmpty());
        assertEquals(0, testLog.getWaterAverage());
        assertEquals(0, testLog.getPagesTotal());
        assertEquals(0, testLog.getPercentageGym());
        assertEquals(0, testLog.getPhoneTimeAverage());
        assertEquals("not tracked", testLog.getMoodSummary());
    }

    @Test
    void testAddDay() {
        testLog.addDay(d1);
        assertEquals(1,testLog.getLogOfDays().size());
        assertEquals(6, testLog.getWaterAverage());
        assertEquals(20, testLog.getPagesTotal());
        assertEquals(2, testLog.getPhoneTimeAverage());
        assertEquals(100, testLog.getPercentageGym());
        assertEquals("good", testLog.getMoodSummary());

    }

    @Test
    void testAddDayMultipleTimes() {
        testLog.addDay(d1);
        assertEquals(1,testLog.getLogOfDays().size());
        assertEquals(6, testLog.getWaterAverage());
        assertEquals(2, testLog.getPhoneTimeAverage());
        assertEquals(20, testLog.getPagesTotal());
        assertEquals(100, testLog.getPercentageGym());
        assertEquals("good", testLog.getMoodSummary());

        testLog.addDay(d2);

        assertEquals(2,testLog.getLogOfDays().size());
        assertEquals(4, testLog.getWaterAverage());
        assertEquals(1, testLog.getPhoneTimeAverage());
        assertEquals(33, testLog.getPagesTotal());
        assertEquals(50, testLog.getPercentageGym());
        assertEquals("okay", testLog.getMoodSummary());
    }

    @Test
    void testMonthlyMoodSummary() {
        testLog.addDay(d1);
        testLog.addDay(d2);
        testLog.addDay(d3);
        testLog.addDay(d4);
        testLog.addDay(d5);
        testLog.addDay(d6);
        testLog.addDay(d7);

        String success1 = testLog.getMonthlyMoodSummary(1);
        assertEquals("good", success1);

        String success2 = testLog.getMonthlyMoodSummary(8);
        assertEquals("neutral", success2);

        String success3 = testLog.getMonthlyMoodSummary(10);
        assertEquals("not tracked", success3);

        String success4 = testLog.getMonthlyMoodSummary(2);
        assertEquals("not tracked", success4);

        String success5 = testLog.getMonthlyMoodSummary(3);
        assertEquals("great", success5);

        String success6 = testLog.getMonthlyMoodSummary(4);
        assertEquals("bad", success6);
    }

    //monthlyMoodSummary(int month)

    @Test
    void testMonthlyPagesTotal() {
        testLog.addDay(d1);
        testLog.addDay(d2);
        testLog.addDay(d3);
        testLog.addDay(d4);
        testLog.addDay(d5);

        int success1 = testLog.getPagesTotalMonthly(1);
        assertEquals(20, success1);

        int success2 = testLog.getPagesTotalMonthly(8);
        assertEquals(113, success2);

        int success3 = testLog.getPagesTotalMonthly(10);
        assertEquals(0, success3);

        int success4 = testLog.getPagesTotalMonthly(2);
        assertEquals(0, success4);
    }

    @Test
    void testMonthlyWaterAverage() {
        testLog.addDay(d1);
        testLog.addDay(d2);
        testLog.addDay(d3);
        testLog.addDay(d4);
        testLog.addDay(d5);

        int success1 = testLog.getAverageWaterMonthly(1);
        assertEquals(6, success1);

        int success2 = testLog.getAverageWaterMonthly(8);
        assertEquals(4, success2);

        int success3 = testLog.getAverageWaterMonthly(10);
        assertEquals(0, success3);

        int success4 = testLog.getAverageWaterMonthly(2);
        assertEquals(0, success4);
    }

    @Test
    void testMonthlyGymPercentage() {
        testLog.addDay(d1);
        testLog.addDay(d2);
        testLog.addDay(d3);
        testLog.addDay(d4);
        testLog.addDay(d5);

        int success1 = testLog.getGymPercentageMonthly(1);
        assertEquals(100, success1);

        int success2 = testLog.getGymPercentageMonthly(8);
        assertEquals(66, success2);

        int success3 = testLog.getGymPercentageMonthly(10);
        assertEquals(0, success3);

        int success4 = testLog.getGymPercentageMonthly(2);
        assertEquals(0, success4);
    }

    @Test
    void testMonthlyPhoneTimeAverage() {
        testLog.addDay(d1);
        testLog.addDay(d2);
        testLog.addDay(d3);
        testLog.addDay(d4);
        testLog.addDay(d5);

        int success1 = testLog.getAveragePhoneTimeMonthly(1);
        assertEquals(2, success1);

        int success2 = testLog.getAveragePhoneTimeMonthly(8);
        assertEquals(2, success2);

        int success3 = testLog.getAveragePhoneTimeMonthly(10);
        assertEquals(0, success3);

        int success4 = testLog.getAveragePhoneTimeMonthly(2);
        assertEquals(0, success4);
    }

    @Test
    void testDaysLogged() {
        testLog.addDay(d1);
        testLog.addDay(d2);
        testLog.addDay(d3);
        testLog.addDay(d4);

        assertEquals(4, testLog.getDaysLogged().size());
        assertTrue(testLog.getDaysLogged().contains(d1.getDate()));
        assertTrue(testLog.getDaysLogged().contains(d2.getDate()));
        assertTrue(testLog.getDaysLogged().contains(d3.getDate()));
        assertTrue(testLog.getDaysLogged().contains(d4.getDate()));
        assertFalse(testLog.getDaysLogged().contains(d5.getDate()));

    }

    @Test
    void testGetDay() {
        testLog.addDay(d1);
        testLog.addDay(d2);
        testLog.addDay(d3);
        testLog.addDay(d4);
        testLog.addDay(d5);

        assertEquals(d1, testLog.getDay("2023-01-02"));
        assertEquals(d2, testLog.getDay("2023-08-25"));

    }



}
