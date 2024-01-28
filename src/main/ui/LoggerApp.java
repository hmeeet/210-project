package ui;

import model.Day;
import model.Log;
import persistance.JsonReader;
import persistance.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Scanner;

// Logger application
// Referred to the phase 1 example Teller application:
// https://github.students.cs.ubc.ca/CPSC210/TellerApp/blob/main/src/main/ca/ubc/cpsc210/bank/ui/TellerApp.java
public class LoggerApp {
    private static final String JSON = "./data/myLog.json";
    private Log userLog;
    private Day newDay;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    //EFFECTS: runs the logger app
    public LoggerApp() {
        runLogger();
    }

    //EFFECTS: processes the input from the user
    private void runLogger() {
        boolean keepGoing = true;
        String command = null;

        init();

        while (keepGoing) {
            displayFirstMenu();
            command = input.next();

            if (command.equals("e")) {
                keepGoing = false;
            } else {
                processFirstCommand(command);
            }
        }
    }

    //EFFECTS: initializes the logging system
    private void init() {
        userLog = new Log();
        input = new Scanner(System.in);
        jsonWriter = new JsonWriter(JSON);
        jsonReader = new JsonReader(JSON);
    }

    //EFFECTS: displays the starting menu
    private void displayFirstMenu() {
        System.out.println("Here are your options:");
        System.out.println("l to load log from file");
        System.out.println("d to enter your log for today");
        System.out.println("s to save your log to file");
        System.out.println("v to view your data from a previous month");
        System.out.println("r to view data from previous days");
        System.out.println("e to exit");
    }

    //EFFECTS: processes the user's input
    private void processFirstCommand(String command) {
        switch (command) {
            case "d":
                logToday();
                break;
            case "v":
                viewMonth();
                break;
            case "r":
                showDays();
                break;
            case "s":
                saveLog();
                break;
            case "l":
                loadLog();
                break;
            default:
                System.out.println("try that again...");
                break;
        }
    }

    //MODIFIES:  newDay
    //EFFECTS: creates a new Day instance and fills in the user's information for the day
    private void logToday() {
        newDay = new Day();
        System.out.println("how many pages did you read today?");
        String pages = input.next();
        newDay.readPages(Integer.parseInt(pages));
        System.out.println("how many glasses of water did you drink today?");
        String glasses = input.next();
        newDay.drinkWater(Integer.parseInt(glasses));
        System.out.println("did you go to the gym today?");
        String gym = input.next();
        if (gym.equals("yes")) {
            newDay.goToGym();
        }
        System.out.println("how many hours did you spend on your phone today?");
        String hours = input.next();
        newDay.timeOnPhone(Integer.parseInt(hours));
        System.out.println("rate your mood on a scale of 1 - 5:");
        String mood = input.next();
        newDay.trackMood(Integer.parseInt(mood));
        userLog.addDay(newDay);
    }

    //EFFECTS: shows the user the information for the month they choose
    private void viewMonth() {
        System.out.println("select a month (enter as a number):");
        int month = 0;
        month = Integer.parseInt(input.next());

        if ((month <= 12) && (month >= 1)) {
            monthlyStats(month);
        } else {
            System.out.println("try that again...");
        }
    }

    //EFFECTS: shows the user the information for the day that they choose
    private void showDays() {
        System.out.println("here are the days you have stored data for:");
        System.out.println(userLog.getDaysLogged());
        System.out.println("enter the date you would like to view data for");
        String date;
        date = input.next();
        if (userLog.getDaysLogged().contains(LocalDate.parse(date))) {
            dailyStats(date);
        } else {
            System.out.println("try that again...");
        }
    }

    //EFFECTS: prints the information for the chosen day
    private void dailyStats(String date) {
        Day dayStats = userLog.getDay(date);
        System.out.println("On that day, you:");
        System.out.println("read " + dayStats.getPagesRead() + " pages.");
        System.out.println("drank " + dayStats.getGlassesOfWater() + " glasses of water.");
        System.out.println("spent " + dayStats.getHoursOnPhone() + " hours on your phone.");
        System.out.println(didGo(dayStats) + " go to the gym.");
        System.out.println("And had a " + dayStats.getMoodDescription() + " day.");
    }

    //EFFECTS: returns "Did" if getWentToGym() for given day is true, "Didn't", if it is false
    private String didGo(Day d) {
        if (d.getWentToGym()) {
            return "Did";
        } else {
            return "Didn't";
        }
    }

    //EFFECTS: prints the information for the chosen month
    private void monthlyStats(int month) {
        System.out.println("During that month, you:");
        System.out.println("Read " + userLog.getPagesTotalMonthly(month) + " pages.");
        System.out.println("Drank an average of " + userLog.getAverageWaterMonthly(month)
                                                                                        + " glasses of water per day.");
        System.out.println("spent an average of " + userLog.getAveragePhoneTimeMonthly(month)
                                                                                    + " hours on your phone per day.");
        System.out.println("Went to the gym " + userLog.getGymPercentageMonthly(month) + "% of days out of the month.");
        System.out.println("And had an overall" + userLog.getMonthlyMoodSummary(month) + " month.");
    }

    //EFFECTS: saves the entered log to the user log file
    private void saveLog() {
        try {
            jsonWriter.open();
            jsonWriter.write(userLog);
            jsonWriter.close();
            System.out.println("Saved today's log to " + JSON);
        } catch (FileNotFoundException e) {
            System.out.println("file not found");
        }
    }

    //EFFECTS: loads the data from the file that it is saved in
    private void loadLog() {
        try {
            userLog = jsonReader.read();
            System.out.println("Loaded log from " + JSON);
        } catch (IOException e) {
            System.out.println("unable to read");
        }
    }
}

