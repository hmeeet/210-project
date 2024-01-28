package model;


import org.json.JSONObject;
import java.time.LocalDate;


//Represents a day, having a date, a boolean for whether the user went to the gym, how much water they drank
// (in glasses), what their mood was (on a scale of 1-5), how many hours they spent on their phone, and how many pages
// they read
// I used the LocalDate library, I found information about it on this website:
// https://www.baeldung.com/java-8-date-time-intro
public class Day {


    private LocalDate date;
    private boolean wentToGym;
    private int glassesOfWater;
    private int hoursOnPhone;
    private int mood;
    private int pagesRead;


    //EFFECTS: creates a new date with no activities done
    public Day() {
        this.date = LocalDate.now();
        this.wentToGym = false;
        this.glassesOfWater = 0;
        this.hoursOnPhone = 0;
        this.mood = 0;
        this.pagesRead = 0;
    }

    // MODIFIES: this
    //EFFECTS: adds glasses to glasses of water drank for the day
    public void drinkWater(int glasses) {
        this.glassesOfWater += glasses;
    }


    // MODIFIES: this
    //EFFECTS: changes wentToGym to true
    public void goToGym() {
        this.wentToGym = true;
    }


    // MODIFIES: this
    //EFFECTS: adds pages to pages read for the day
    public void readPages(int pages) {
        this.pagesRead += pages;
    }


    // MODIFIES: this
    //EFFECTS: adds hours to time on phone for the day
    public void timeOnPhone(int hours) {
        this.hoursOnPhone += hours;
    }

    //REQUIRES: 1<= m <= 5
    // MODIFIES: this
    //EFFECTS: sets mood to m
    public void trackMood(int m) {
        this.mood = m;
    }

    //EFFECTS: returns the string that corresponds to the mood rating for the given day
    public String getMoodDescription() {
        String describeMood = "not tracked";
        switch (mood) {
            case 1:
                describeMood = "bad";
                break;
            case 2:
                describeMood = "okay";
                break;
            case 3:
                describeMood = "neutral";
                break;
            case 4:
                describeMood = "good";
                break;
            case 5:
                describeMood = "great";
                break;
        }
        return describeMood;
    }

    public int getGlassesOfWater() {

        return glassesOfWater;
    }

    public double getHoursOnPhone() {

        return  hoursOnPhone;
    }

    public boolean getWentToGym() {

        return wentToGym;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getMood() {

        return mood;
    }

    public int getPagesRead() {

        return pagesRead;
    }

    //MODIFIES: this
    //EFFECTS: sets the date to the given date
    public void setDate(String date) {

        this.date = LocalDate.parse(date);
    }

    //EFFECTS: returns Day as a JSONObject
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("date", date);
        json.put("wentToGym", wentToGym);
        json.put("glassesOfWater", glassesOfWater);
        json.put("hoursOnPhone", hoursOnPhone);
        json.put("mood", mood);
        json.put("pagesRead", pagesRead);
        return json;
    }




}
