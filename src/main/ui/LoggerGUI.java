package ui;

import model.Day;
import model.Event;
import model.EventLog;
import model.Log;
import persistance.JsonReader;
import persistance.JsonWriter;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

// Logger application GUI
//citation:
// https://docs.oracle.com/javase/tutorial/uiswing/examples/components/index.html
// https://www.clear.rice.edu/comp310/JavaResources/frame_close.html
public class LoggerGUI extends JFrame {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 1000;

    private JDesktopPane desktop;
    private JInternalFrame logPanel;
    private JInternalFrame pastLogs;
    private JInternalFrame loadOrSave;
    private JInternalFrame dayInfo;
    private JInternalFrame showMonths;
    private JInternalFrame monthInfo;

    private static final String JSON = "./data/myLog.json";
    private static final String IMAGE = "./data/background.png";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private JTextField pagesText;
    private JTextField waterText;
    private JCheckBox goToGym;
    private JTextField hoursText;
    private JSlider moodSlider;
    private JPanel logArea;

    private JList<String> prevJList;
    private JList<String> months;

    private Log userLog;
    private Day newDay;

    //EFFECTS: runs the logger app GUI
    public LoggerGUI() {
        init();

        desktop = new JDesktopPane();
        desktop.setLayout(new BorderLayout());
        setContentPane(desktop);
        setTitle("Logger App");
        setSize(WIDTH, HEIGHT);

        JLabel background = new JLabel(new ImageIcon(IMAGE));
        background.setSize(WIDTH, HEIGHT);
        desktop.add(background);

        createPanels();

        addLogPanel();
        addSavePanel();
        addPreviousLogsPanel();
        addMonthPanel();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                printEventLog(EventLog.getInstance());
            }
        });

        setVisible(true);
    }

    private void printEventLog(EventLog el) {
        for (Event next : el) {
            System.out.println(next.toString());
        }
    }

    //EFFECTS: initializes the logging system
    private void init() {
        newDay = new Day();
        userLog = new Log();
        jsonWriter = new JsonWriter(JSON);
        jsonReader = new JsonReader(JSON);
    }



    //EFFECTS:creates the panels that show up at opening
    private void createPanels() {
        logPanel = new JInternalFrame("enter your log for the day!", false, false, false, false);
        logPanel.setLayout(new BorderLayout());
        logPanel.pack();
        logPanel.setVisible(true);
        logPanel.setSize(WIDTH, HEIGHT / 4);
        desktop.add(logPanel, BorderLayout.SOUTH);

        pastLogs = new JInternalFrame("previous logs", true, false, false, false);
        pastLogs.setLayout(new BorderLayout());
        pastLogs.pack();
        pastLogs.setVisible(true);
        pastLogs.setSize(WIDTH / 4,HEIGHT / 3);
        desktop.add(pastLogs, BorderLayout.WEST);

        loadOrSave = new JInternalFrame("load or save", false, false, false, false);
        loadOrSave.pack();
        loadOrSave.setVisible(true);
        loadOrSave.setSize(WIDTH, HEIGHT / 6);
        desktop.add(loadOrSave, BorderLayout.NORTH);

        showMonths = new JInternalFrame("View Month", false, false, false, false);
        showMonths.setLayout(new BorderLayout());
        showMonths.pack();
        showMonths.setVisible(true);
        showMonths.setSize(WIDTH / 4,HEIGHT / 3);
        desktop.add(showMonths, BorderLayout.EAST);
    }

    //EFFECTS: creates the panel that displays all days in the log
    private void addPreviousLogsPanel() {
        DefaultListModel listModel = new DefaultListModel();

        for (Day d : userLog.getLogOfDays()) {
            listModel.addElement(d.getDate().toString());

        }

        prevJList = new JList(listModel);
        prevJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        prevJList.setLayoutOrientation(JList.VERTICAL);
        prevJList.setVisibleRowCount(-1);
        prevJList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                displayDay(prevJList.getSelectedValue());
            }
        });

        JScrollPane prevScroller = new JScrollPane(prevJList);
        prevScroller.setPreferredSize(new Dimension(80, 250));

        pastLogs.add(prevJList);
    }

    //EFFECTS: shows the user the information for the day that they choose
    private void displayDay(String s) {
        Day dayStats = userLog.getDay(s);

        dayInfo = new JInternalFrame("info for " + s, false, true, false, false);
        dayInfo.setLayout(new BorderLayout());
        dayInfo.pack();
        dayInfo.setVisible(true);
        dayInfo.setSize(WIDTH / 2, HEIGHT / 2);
        desktop.add(dayInfo, BorderLayout.CENTER);
        dayInfo.moveToFront();

        JPanel showStats = new JPanel();
        showStats.setLayout(new GridLayout(0, 1));

        JLabel dayTitle = new JLabel("On " + dayStats.getDate().toString() + ", you:");
        showStats.add(dayTitle);

        JLabel pages = new JLabel("read " + dayStats.getPagesRead() + " pages.");
        showStats.add(pages);

        JLabel water = new JLabel("drank " + dayStats.getGlassesOfWater() + " glasses of water.");
        showStats.add(water);

        JLabel hours = new JLabel("spent " + dayStats.getHoursOnPhone() + " hours on your phone.");
        showStats.add(hours);

        JLabel gym = new JLabel(didGo(dayStats) + " go to the gym.");
        showStats.add(gym);

        JLabel mood = new JLabel("And had a " + dayStats.getMoodDescription() + " day.");
        showStats.add(mood);

        dayInfo.add(showStats, BorderLayout.NORTH);
    }

    //EFFECTS: returns "Did" if getWentToGym() for given day is true, "Didn't", if it is false
    private String didGo(Day d) {
        if (d.getWentToGym()) {
            return "Did";
        } else {
            return "Didn't";
        }
    }

    //EFFECTS: creates a panel with all the months that the user can choose from
    private void addMonthPanel() {
        String[] month = {"01 - January", "02 - February", "03 - March", "04 - April", "05 - May", "06 - June",
                "07 - July", "08 - August", "09 - September", "10 - October", "11 - November", "12 - December"};


        months = new JList(month);
        months.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        months.setLayoutOrientation(JList.VERTICAL);
        months.setVisibleRowCount(-1);
        months.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                displayMonth(months.getSelectedValue());
            }
        });

        JScrollPane prevScroller = new JScrollPane(months);
        prevScroller.setPreferredSize(new Dimension(80, 250));

        showMonths.add(months);

    }

    //EFFECTS: shows the user the information for the month they choose
    private void displayMonth(String s) {
        int month = Integer.parseInt(s.substring(0,2));

        createMonthInfo(s);

        JPanel showStats = new JPanel();
        showStats.setLayout(new GridLayout(0, 1));

        JLabel dayTitle = new JLabel("During " + s.substring(5) + ", you:");
        showStats.add(dayTitle);

        JLabel pages = new JLabel("read " + userLog.getPagesTotalMonthly(month) + " pages.");
        showStats.add(pages);

        JLabel water = new JLabel("Drank an average of " + userLog.getAverageWaterMonthly(month)
                + " glasses of water per day.");
        showStats.add(water);

        JLabel hours = new JLabel("spent an average of " + userLog.getAveragePhoneTimeMonthly(month)
                + " hours on your phone per day.");
        showStats.add(hours);

        JLabel gym = new JLabel("Went to the gym " + userLog.getGymPercentageMonthly(month)
                + "% of days out of the month.");
        showStats.add(gym);

        JLabel mood = new JLabel("And had an overall " + userLog.getMonthlyMoodSummary(month) + " month.");
        showStats.add(mood);

        monthInfo.add(showStats, BorderLayout.NORTH);
    }

    //EFFECTS: creates the panel that the month information is displayed on
    private void createMonthInfo(String s) {
        monthInfo = new JInternalFrame("info for " + s.substring(5), false, true, false, false);
        monthInfo.setLayout(new BorderLayout());
        monthInfo.pack();
        monthInfo.setVisible(true);
        monthInfo.setSize(WIDTH / 2, HEIGHT / 2);
        desktop.add(monthInfo, BorderLayout.CENTER);
        monthInfo.moveToFront();
    }

    //MODIFIES:  newDay
    //EFFECTS: creates a new Day instance and fills in the user's information for the day
    private void addLogPanel() {
        initLogArea();

        JLabel pages = new JLabel("how many pages did you read today?");
        logArea.add(pages);
        pagesText = new JTextField(2);
        logArea.add(pagesText);

        JLabel water = new JLabel("how many glasses of water did you drink today?");
        logArea.add(water);
        waterText = new JTextField(2);
        logArea.add(waterText);

        JLabel gym = new JLabel("did you go to the gym today?");
        logArea.add(gym);
        goToGym = new JCheckBox();
        logArea.add(goToGym);

        JLabel hours = new JLabel("how many hours did you spend on your phone today?");
        logArea.add(hours);
        hoursText = new JTextField(2);
        logArea.add(hoursText);

        JLabel mood = new JLabel("rate your mood on a scale of 1 - 5:");
        logArea.add(mood);
        moodSlider = new JSlider(1, 5);
        logArea.add(moodSlider);

        JButton saveDayLog = new JButton(new SubmitAction());
        logArea.add(saveDayLog);
    }

    //EFFECTS: creates the panel that the log information is displayed
    private void initLogArea() {
        logArea = new JPanel();
        logArea.setLayout(new GridLayout(0, 2));
        logPanel.add(logArea);
    }

    private class SubmitAction extends AbstractAction {

        SubmitAction() {
            super("submit log!");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String page = pagesText.getText();
            newDay.readPages(Integer.parseInt(page));

            String glasses = waterText.getText();
            newDay.drinkWater(Integer.parseInt(glasses));

            if (goToGym.isSelected()) {
                newDay.goToGym();
            }

            String hour = hoursText.getText();
            newDay.timeOnPhone(Integer.parseInt(hour));

            newDay.trackMood(moodSlider.getValue());

            pastLogs.remove(prevJList);
            userLog.addDay(newDay);
            addPreviousLogsPanel();
        }
    }

    //EFFECTS: creates the panel with the options to save or load the user log file
    private void addSavePanel() {
        JPanel popUp = new JPanel();
        popUp.setLayout(new GridLayout(1, 2));
        loadOrSave.add(popUp);

        JButton load = new JButton(new LoadAction());
        popUp.add(load);

        JButton save = new JButton(new SaveAction());
        popUp.add(save);
    }

    //EFFECTS: saves the state of the application to file
    private class SaveAction extends AbstractAction {
        SaveAction() {
            super("save all new changes");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            pastLogs.remove(prevJList);
            saveLog();
            addPreviousLogsPanel();
        }
    }

    //EFFECTS: loads the previously saved data from file
    private class LoadAction extends AbstractAction {
        LoadAction() {
            super("load previously saved data");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            pastLogs.remove(prevJList);
            loadLog();
            addPreviousLogsPanel();
        }
    }

    //EFFECTS: loads the data from the file that it is saved in
    private void loadLog() {
        try {
            userLog = jsonReader.read();
            addPreviousLogsPanel();
        } catch (IOException e) {
            System.out.println("unable to read");
        }
    }

    //EFFECTS: saves the entered log to the user log file
    private void saveLog() {
        try {
            jsonWriter.open();
            jsonWriter.write(userLog);
            jsonWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("file not found");
        }
    }

}


