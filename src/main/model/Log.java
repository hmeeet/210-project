package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.time.LocalDate;
import java.util.List;


//Represents the log containing the information each day that the user has logged
public class Log {

    private List<Day> logOfDays;
    private int totalPages;
    private int averageWater;
    private double gymPercentage;
    private int averagePhoneTime;
    private String moodSummary;
    private List<Day> logForMonth;
    private List<LocalDate> daysLogged;

    //EFFECTS: creates a new log with an empty log of days
    public Log() {
        this.logOfDays = new ArrayList<>();
    }


    //MODIFIES: this
    //EFFECTS: adds day to log of days and logs event
    public void addDay(Day day) {
        this.logOfDays.add(day);
        EventLog.getInstance().logEvent(new Event("Day added to log."));
    }


    //EFFECTS: gets the total number of pages that the user has read on logged days
    public int getPagesTotal() {
        totalPages = 0;
        for (Day day : logOfDays) {
            totalPages += day.getPagesRead();
        }
        return totalPages;
    }

    //EFFECTS: gets the average glasses of water that the user has drank per day based on logged days
    public int getWaterAverage() {
        if (!logOfDays.isEmpty()) {
            int totalWater = 0;
            for (Day day : logOfDays) {
                totalWater += day.getGlassesOfWater();
            }
            averageWater = totalWater / logOfDays.size();
        }
        return averageWater;
    }

    //EFFECTS: gets the average time that the user has spent on  their phone per day based on logged days
    public int getPhoneTimeAverage() {
        if (!logOfDays.isEmpty()) {
            int totalTime = 0;
            for (Day day : logOfDays) {
                totalTime += day.getHoursOnPhone();
            }
            averagePhoneTime = totalTime / logOfDays.size();
        }
        return averagePhoneTime;
    }


    //EFFECTS: gets the percentage of days (out of the days logged) that the user went to the gym based on logged days
    public int getPercentageGym() {
        if (!logOfDays.isEmpty()) {
            double totalGymDays = 0;
            for (Day day : logOfDays) {
                if (day.getWentToGym()) {
                    totalGymDays++;
                }
            }
            gymPercentage = (totalGymDays / logOfDays.size()) * 100;
        }
        return (int) gymPercentage;
    }

    //EFFECTS: returns the mood description that corresponds to the average mood over all of the days logged
    public String getMoodSummary() {
        int sum = 0;
        if (!logOfDays.isEmpty()) {
            for (Day day : logOfDays) {
                sum += day.getMood();
            }
            int avg = sum / logOfDays.size();
            moodSummary = getMoodDescription(avg);
        } else {
            moodSummary = getMoodDescription(0);
        }
        return moodSummary;
    }

    // REQUIRES: 1 <= month <= 12
    //EFFECTS: creates a list of days that are in the given month
    private void filterByMonth(int month) {
        logForMonth = new ArrayList<>();
        for (Day day : logOfDays) {
            if (day.getDate().getMonthValue() == month) {
                logForMonth.add(day);
            }
        }
    }

    // REQUIRES: 1 <= month <= 12
    //EFFECTS: returns the mood description that corresponds to the average mood over the month based on logged days
    public String getMonthlyMoodSummary(int month) {
        filterByMonth(month);
        EventLog.getInstance().logEvent(new Event("showed info for month: " + month));
        int sum = 0;
        if (!logForMonth.isEmpty()) {
            for (Day day : logForMonth) {
                sum += day.getMood();
            }
            int avg = sum / logForMonth.size();
            return getMoodDescription(avg);
        } else {
            return "not tracked";
        }
    }


    //EFFECTS: returns the description that corresponds to the mood rating number
    private String getMoodDescription(int mood) {
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


    //  REQUIRES: 1 <= month <= 12
    //EFFECTS: gets the total number of pages that the user has read in the given month based on logged days
    public int getPagesTotalMonthly(int month) {
        filterByMonth(month);
        int monthlyTotalPages = 0;
        for (Day day : logForMonth) {
            monthlyTotalPages += day.getPagesRead();
        }

        return monthlyTotalPages;
    }

    // REQUIRES: 1 <= month <= 12
    //EFFECTS: returns the average glasses of water drank per day for the given month based on logged days
    public int getAverageWaterMonthly(int month) {
        filterByMonth(month);
        if (!logForMonth.isEmpty()) {
            int totalWater = 0;
            for (Day day : logForMonth) {
                totalWater += day.getGlassesOfWater();
            }
            return totalWater / logForMonth.size();
        } else {
            return 0;
        }

    }

    // REQUIRES: 1 <= month <= 12
    //EFFECTS: returns the average glasses of water drank per day for the given month based on logged days
    public int getAveragePhoneTimeMonthly(int month) {
        filterByMonth(month);
        if (!logForMonth.isEmpty()) {
            int totalPhoneTime = 0;
            for (Day day : logForMonth) {
                totalPhoneTime += day.getHoursOnPhone();
            }
            return totalPhoneTime / logForMonth.size();
        } else {
            return 0;
        }

    }

    // REQUIRES: 1 <= month <= 12
    //EFFECTS: gets the percentage of days (out of the days logged) that the user went to the gym based on logged days
    public int getGymPercentageMonthly(int month) {
        filterByMonth(month);
        double gymDays = 0;
        if (!logForMonth.isEmpty()) {
            double totalGymDays = 0;
            for (Day day : logForMonth) {
                if (day.getWentToGym()) {
                    totalGymDays++;
                }
            }
            gymDays = (totalGymDays / logForMonth.size()) * 100;
        }
        return (int) gymDays;
    }

    //EFFECTS: returns a list of the dates that have logs
    public List<LocalDate> getDaysLogged() {
        daysLogged = new ArrayList<>();
        for (Day day : logOfDays) {
            daysLogged.add(day.getDate());
        }
        return daysLogged;
    }
    
    //REQUIRES: daysLogged must contain d
    //EFFECTS: returns the Day corresponding to the given date
    public Day getDay(String s) {
        LocalDate d = LocalDate.parse(s);
        Day get = null;
        for (Day day : logOfDays) {
            if (d.equals(day.getDate())) {
                get = day;
            }
        }
        EventLog.getInstance().logEvent(new Event("Showed info for day: " + s));
        return get;
    }

    public List<Day> getLogOfDays() {
        return logOfDays;
    }

    //EFFECTS: returns Log as a JSONObject
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("logOfDays", daysToJson());
        return json;
    }

    //EFFECTS: returns the Days in the logOfDays as a JSONArray
    private JSONArray daysToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Day d: logOfDays) {
            jsonArray.put(d.toJson());
        }
        return jsonArray;
    }

    //MODIFIES: this
    //EFFECTS: adds day to log
    public void addDayFromJson(Day day) {
        this.logOfDays.add(day);
    }

}
