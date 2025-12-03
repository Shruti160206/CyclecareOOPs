package com.cyclecare.mainMenu;

import com.cyclecare.tracker.CycleDataProcessor;
import com.cyclecare.tracker.MyCyclePhase;
import com.cyclecare.exceptions.InvalidCycleDataException;
import com.cyclecare.exceptions.InvalidCycleDayException;
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

    private JTextField nameField, ageField, periodField, moodField, symptomField, severityField;
    private JTextArea outputArea;

    private String userName;
    private int userAge;

    private List<LocalDate> periodHistory = new ArrayList<>();
    private CycleDataProcessor cycleProcessor;

    private MoodMonitor moodMonitor = new MoodMonitor();
    private SymptomMonitor symptomMonitor = new SymptomMonitor();

    public CycleCareMainMenu() {

        setTitle("CycleCare");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabs = new JTabbedPane();

        // ==========================================
        // USER DETAILS TAB
        // ==========================================
        JPanel userPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        nameField = new JTextField();
        ageField = new JTextField();
        JButton createUserBtn = new JButton("Create User");
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
        periodField = new JTextField();

        JButton addPeriodBtn = new JButton("Add Period Date");
        JButton showAvgBtn = new JButton("Show Average Cycle Length");
        JButton showDiffBtn = new JButton("Show Cycle Differences");
        JButton showStatsBtn = new JButton("Show Average & Variance");
        JButton estimateNextBtn = new JButton("Estimate Next Period");

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
        moodField = new JTextField();
        JButton addMoodBtn = new JButton("Add Mood");
        JButton showMoodBtn = new JButton("Show Mood Records");

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
            moodMonitor.displayRecords();
        });

        tabs.addTab("Mood Tracker", moodPanel);

        // ==========================================
        // SYMPTOM TRACKER TAB
        // ==========================================
        JPanel symptomPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        symptomField = new JTextField();
        severityField = new JTextField();
        JButton addSymptomBtn = new JButton("Add Symptom");
        JButton showSymptomBtn = new JButton("Show Symptoms");

        symptomPanel.add(new JLabel("Symptom:"));
        symptomPanel.add(symptomField);
        symptomPanel.add(new JLabel("Severity (1-10):"));
        symptomPanel.add(severityField);
        symptomPanel.add(addSymptomBtn);
        symptomPanel.add(showSymptomBtn);

        addSymptomBtn.addActionListener(e -> {
            try {
                String name = symptomField.getText().trim();
                int severity = Integer.parseInt(severityField.getText().trim());

                symptomMonitor.addSymptom(name, severity);
                outputArea.append("Symptom added: " + name + " (Severity: " + severity + ")\n");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid severity!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        showSymptomBtn.addActionListener(e -> {
            outputArea.append("\nSymptom Records:\n");
            symptomMonitor.displayRecords();
            outputArea.append("Average Severity: " + symptomMonitor.calculateAverageSeverity() + "\n");
        });

        tabs.addTab("Symptom Tracker", symptomPanel);

        // ==========================================
        // WELLNESS GUIDE TAB
        // ==========================================
        JPanel wellnessPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        JButton showAdviceBtn = new JButton("Show Wellness Advice");

        wellnessPanel.add(new JLabel("Get wellness advice:"));
        wellnessPanel.add(showAdviceBtn);

        showAdviceBtn.addActionListener(e -> {
            WellnessGuide guide = new WellnessGuide(moodMonitor, symptomMonitor);
            outputArea.append(guide.giveAdvice() + "\n");
        });

        tabs.addTab("Wellness Guide", wellnessPanel);

        // ==========================================
        // CYCLE PHASE DETECTOR TAB
        // ==========================================
        JPanel phasePanel = new JPanel(new GridLayout(3, 2, 5, 5));

        JTextField dayField = new JTextField();
        JButton detectPhaseBtn = new JButton("Detect Phase");

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
        // OUTPUT AREA
        // ==========================================
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        add(tabs, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CycleCareMainMenu().setVisible(true));
    }
}



