package com.cyclecare.mainMenu;

import com.cyclecare.tracker.CycleDataProcessor;
import com.cyclecare.tracker.MyCyclePhase;
import com.cyclecare.exceptions.InvalidCycleDataException;
import com.cyclecare.exceptions.InvalidCycleDayException;
import com.cyclecare.wellness.Mood;
import com.cyclecare.wellness.MoodMonitor;
import com.cyclecare.wellness.SymptomMonitor;
import com.cyclecare.wellness.WellnessGuide;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class CycleCareMainMenu extends JFrame {

    private JTextField nameField, ageField, periodField, moodField, symptomField, severityField, dayField;
    private JTextArea outputArea;

    private String userName;
    private int userAge;

    private List<LocalDate> periodHistory = new ArrayList<>();
    private CycleDataProcessor cycleProcessor;

    private MoodMonitor moodMonitor = new MoodMonitor();
    private SymptomMonitor symptomMonitor = new SymptomMonitor();

    public CycleCareMainMenu() {

        setTitle("CycleCare");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Output area
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        Color tabPink = new Color(255, 224, 224); // Soft pink
        Color buttonPink = new Color(255, 182, 193); // Hot pink

        JTabbedPane tabs = new JTabbedPane();

        // ==========================================
        // USER DETAILS TAB
        // ==========================================
        JPanel userPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        userPanel.setBackground(tabPink);

        nameField = new JTextField();
        ageField = new JTextField();
        JButton createUserBtn = new JButton("Create User");
        createUserBtn.setBackground(buttonPink);
        JLabel statusLabel = new JLabel("No user created.");

        userPanel.add(new JLabel("Name:"));
        userPanel.add(nameField);
        userPanel.add(new JLabel("Age:"));
        userPanel.add(ageField);
        userPanel.add(createUserBtn);
        userPanel.add(statusLabel);

        createUserBtn.addActionListener(e -> {
            String name = nameField.getText().trim();
            try {
                int age = Integer.parseInt(ageField.getText().trim());
                if (name.isEmpty()) throw new Exception();

                userName = name;
                userAge = age;

                statusLabel.setText("User Created: " + name + " (Age: " + age + ")");
                outputArea.append("Welcome, " + name + "!\n");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid name or age!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        tabs.addTab("User Details", userPanel);

        // ==========================================
        // CYCLE TRACKER TAB
        // ==========================================
        JPanel cyclePanel = new JPanel(new GridLayout(6, 2, 5, 5));
        cyclePanel.setBackground(tabPink);

        periodField = new JTextField();
        JButton addPeriodBtn = new JButton("Add Period Date");
        addPeriodBtn.setBackground(buttonPink);
        JButton showAvgBtn = new JButton("Show Average Cycle Length");
        showAvgBtn.setBackground(buttonPink);
        JButton showDiffBtn = new JButton("Show Cycle Differences");
        showDiffBtn.setBackground(buttonPink);
        JButton showStatsBtn = new JButton("Show Average & Variance");
        showStatsBtn.setBackground(buttonPink);
        JButton estimateNextBtn = new JButton("Estimate Next Period");
        estimateNextBtn.setBackground(buttonPink);

        cyclePanel.add(new JLabel("Period Date (YYYY-MM-DD):"));
        cyclePanel.add(periodField);
        cyclePanel.add(addPeriodBtn);
        cyclePanel.add(showAvgBtn);
        cyclePanel.add(showDiffBtn);
        cyclePanel.add(showStatsBtn);
        cyclePanel.add(estimateNextBtn);

        addPeriodBtn.addActionListener(e -> {
            try {
                LocalDate date = LocalDate.parse(periodField.getText().trim());
                periodHistory.add(date);
                cycleProcessor = new CycleDataProcessor(periodHistory);
                outputArea.append("Period date added: " + date + "\n");
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this, "Invalid date format!", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (InvalidCycleDataException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        showAvgBtn.addActionListener(e -> {
            if (cycleProcessor != null)
                outputArea.append("Average Cycle Length: " + cycleProcessor.getAverageCycleLength() + " days\n");
            else
                outputArea.append("Add period dates first.\n");
        });

        showDiffBtn.addActionListener(e -> {
            if (cycleProcessor != null) {
                int[] diffs = cycleProcessor.getAllCycleDifferences();
                outputArea.append("Cycle Differences: ");
                for (int d : diffs) outputArea.append(d + " ");
                outputArea.append("\n");
            } else outputArea.append("Add period dates first.\n");
        });

        showStatsBtn.addActionListener(e -> {
            if (cycleProcessor != null) {
                outputArea.append("Average Difference: " + cycleProcessor.getAverageDifference() + "\n");
                outputArea.append("Variance: " + cycleProcessor.getVariance() + "\n");
            } else outputArea.append("Add period dates first.\n");
        });

        estimateNextBtn.addActionListener(e -> {
            if (cycleProcessor != null) {
                LocalDate next = cycleProcessor.estimateNextPeriod();
                outputArea.append("Estimated Next Period: " + next + "\n");
            } else outputArea.append("Add period dates first.\n");
        });

        tabs.addTab("Cycle Tracker", cyclePanel);

        // ==========================================
        // MOOD TRACKER TAB
        // ==========================================
        JPanel moodPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        moodPanel.setBackground(tabPink);

        moodField = new JTextField(10); // smaller box
        JButton addMoodBtn = new JButton("Add Mood");
        addMoodBtn.setBackground(buttonPink);
        JButton showMoodBtn = new JButton("Show Mood Records");
        showMoodBtn.setBackground(buttonPink);

        moodPanel.add(new JLabel("Mood:"));
        moodPanel.add(moodField);
        moodPanel.add(addMoodBtn);
        moodPanel.add(showMoodBtn);

        addMoodBtn.addActionListener(e -> {
            String mood = moodField.getText().trim();
            if (!mood.isEmpty()) {
                moodMonitor.addMood(mood);
                outputArea.append("Mood added: " + mood + "\n");
            }
        });

        showMoodBtn.addActionListener(e -> {
            outputArea.append("\nMood Records:\n");
            for (Mood m : moodMonitor.getRecords()) {
                outputArea.append(m + "\n");
            }
        });

        tabs.addTab("Mood Tracker", moodPanel);

        // ==========================================
        // CYCLE PHASE DETECTOR TAB
        // ==========================================
        JPanel phasePanel = new JPanel(new GridLayout(3, 2, 5, 5));
        phasePanel.setBackground(tabPink);

        dayField = new JTextField(5); // smaller box for 1-28
        JButton detectPhaseBtn = new JButton("Detect Phase");
        detectPhaseBtn.setBackground(buttonPink);

        phasePanel.add(new JLabel("Enter Day in Cycle (1-28):"));
        phasePanel.add(dayField);
        phasePanel.add(detectPhaseBtn);

        detectPhaseBtn.addActionListener(e -> {
            try {
                int day = Integer.parseInt(dayField.getText().trim());
                MyCyclePhase tool = new MyCyclePhase();
                String phase = tool.detectPhase(day);
                outputArea.append("Cycle Phase: " + phase + "\n");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Enter a valid number!", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (InvalidCycleDayException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        tabs.addTab("Cycle Phase", phasePanel);

        // ==========================================
        // CLEAR OUTPUT BUTTON
        // ==========================================
        JButton clearBtn = new JButton("Clear Output");
        clearBtn.setBackground(buttonPink);
        clearBtn.addActionListener(e -> outputArea.setText(""));

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(clearBtn);

        // ==========================================
        // ADD TABS AND OUTPUT AREA
        // ==========================================
        add(tabs, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CycleCareMainMenu().setVisible(true));
    }
}
