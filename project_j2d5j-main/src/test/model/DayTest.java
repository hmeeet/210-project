package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class DayTest {
    private Day testDay;

    @BeforeEach
    void runBefore() {
        testDay = new Day();
    }

    @Test
    void testConstructor() {
        assertEquals(LocalDate.now(), testDay.getDate());
        assertFalse(testDay.getWentToGym());
        assertEquals(0, testDay.getGlassesOfWater());
        assertEquals(0, testDay.getHoursOnPhone());
        assertEquals(0, testDay.getMood());
        assertEquals(0, testDay.getPagesRead());
    }

    @Test
    void testDrinkWater() {
        testDay.drinkWater(7);
        assertEquals(7, testDay.getGlassesOfWater());
    }

    @Test
    void testDrinkWaterMultipleTimes() {
        testDay.drinkWater(4);
        testDay.drinkWater(5);
        assertEquals(9, testDay.getGlassesOfWater());
    }

    @Test
    void testGoToGym() {
        testDay.goToGym();
        assertTrue(testDay.getWentToGym());
    }

    @Test
    void testGoToGymTwice() {
        testDay.goToGym();
        testDay.goToGym();
        assertTrue(testDay.getWentToGym());
    }

    @Test
    void testReadPages() {
        testDay.readPages(100);
        assertEquals(100, testDay.getPagesRead());
    }

    @Test
    void testReadPagesMultipleTimes() {
        testDay.readPages(100);
        testDay.readPages(40);
        testDay.readPages(6);
        assertEquals(146, testDay.getPagesRead());
    }

    @Test
    void testTimeOnPhone() {
        testDay.timeOnPhone(1);
        assertEquals(1, testDay.getHoursOnPhone());
    }

    @Test
    void testMoreTimeOnPhone() {
        testDay.timeOnPhone(1);
        testDay.timeOnPhone(3);
        assertEquals(4, testDay.getHoursOnPhone());
    }

    @Test
    void testTrackMood() {
        testDay.trackMood(3);
        assertEquals(3, testDay.getMood());
    }

    @Test
    void testTrackMoodChange() {
        testDay.trackMood(3);
        testDay.trackMood(5);
        assertEquals(5, testDay.getMood());
    }

    @Test
    void testGetMoodDescription() {
        assertEquals("not tracked", testDay.getMoodDescription());
        testDay.trackMood(1);
        assertEquals("bad", testDay.getMoodDescription());
        testDay.trackMood(2);
        assertEquals("okay", testDay.getMoodDescription());
        testDay.trackMood(3);
        assertEquals("neutral", testDay.getMoodDescription());
        testDay.trackMood(4);
        assertEquals("good", testDay.getMoodDescription());
        testDay.trackMood(5);
        assertEquals("great", testDay.getMoodDescription());
    }



}