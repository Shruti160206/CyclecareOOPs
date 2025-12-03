package com.cyclecare.mainMenu;

import com.cyclecare.tracker.CycleDataProcessor;
import com.cyclecare.tracker.MyCyclePhase;
import com.cyclecare.exceptions.InvalidCycleDataException;
import com.cyclecare.exceptions.InvalidCycleDayException;
import com.cyclecare.wellness.Mood;
import com.cyclecare.wellness.MoodMonitor;
import com.cyclecare.wellness.Symptoms;
import com.cyclecare.wellness.SymptomMonitor;
import com.cyclecare.wellness.WellnessGuide;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class CycleCareMainMenu extends JFrame {

    private JTextField nameField, ageField, periodField, moodField, symptomField, severityField, cycleDayField;
    private JTextArea outputArea;
    private JLabel statusLabel;

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

        // Output Area
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(outputArea);

        Color tabColor = new Color(255, 224, 224); // Soft pink
        Color buttonColor = new Color(255, 143, 199); // Light blue

        JTabbedPane tabs = new JTabbedPane();

        // =========================
        // USER DETAILS TAB
        // =========================
        JPanel userPanel = new JPanel(new GridLayout(3, 1, 5, 10));
        userPanel.setBackground(tabColor);

        nameField = new JTextField();
        ageField = new JTextField();
        statusLabel = new JLabel("");
        JButton createUserBtn = new JButton("Create User");
        createUserBtn.setBackground(buttonColor);

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

        // =========================
        // CYCLE TRACKER TAB
        // =========================
        JPanel cyclePanel = new JPanel(new GridLayout(6, 2, 5, 5));
        cyclePanel.setBackground(tabColor);

        periodField = new JTextField();
        periodField.setColumns(10);
        JButton addPeriodBtn = new JButton("Add Period Date");
        addPeriodBtn.setBackground(buttonColor);
        JButton showAvgBtn = new JButton("Show Average Cycle Length");
        showAvgBtn.setBackground(buttonColor);
        JButton showDiffBtn = new JButton("Show Cycle Differences");
        showDiffBtn.setBackground(buttonColor);
        JButton showStatsBtn = new JButton("Show Avg & Variance");
        showStatsBtn.setBackground(buttonColor);
        JButton estimateNextBtn = new JButton("Estimate Next Period");
        estimateNextBtn.setBackground(buttonColor);

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

        // =========================
        // MOOD TRACKER TAB
        // =========================
        JPanel moodPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        moodPanel.setBackground(tabColor);

        moodField = new JTextField();
        moodField.setColumns(10);
        JButton addMoodBtn = new JButton("Add Mood");
        addMoodBtn.setBackground(buttonColor);
        JButton showMoodBtn = new JButton("Show Mood Records");
        showMoodBtn.setBackground(buttonColor);

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
            for (Mood m : moodMonitor.getRecords()) outputArea.append(m + "\n");
        });

        tabs.addTab("Mood Tracker", moodPanel);

        // =========================
        // SYMPTOM TRACKER TAB
        // =========================
        JPanel symptomPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        symptomPanel.setBackground(tabColor);

        symptomField = new JTextField();
        symptomField.setColumns(10);
        severityField = new JTextField();
        severityField.setColumns(5);

        JButton addSymptomBtn = new JButton("Add Symptom");
        addSymptomBtn.setBackground(buttonColor);
        JButton showSymptomBtn = new JButton("Show Symptoms");
        showSymptomBtn.setBackground(buttonColor);

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
            for (Symptoms s : symptomMonitor.getRecords()) outputArea.append(s + "\n");
            outputArea.append("Average Severity: " + symptomMonitor.calculateAverageSeverity() + "\n");
        });

        tabs.addTab("Symptom Tracker", symptomPanel);

        // =========================
        // CYCLE PHASE DETECTOR TAB
        // =========================
        JPanel phasePanel = new JPanel(new GridLayout(3, 2, 5, 5));
        phasePanel.setBackground(tabColor);

        cycleDayField = new JTextField();
        cycleDayField.setColumns(10);
        JButton detectPhaseBtn = new JButton("Detect Phase");
        detectPhaseBtn.setBackground(buttonColor);

        phasePanel.add(new JLabel("Day in Cycle (1-28):"));
        phasePanel.add(cycleDayField);
        phasePanel.add(detectPhaseBtn);

        detectPhaseBtn.addActionListener(e -> {
            try {
                int day = Integer.parseInt(cycleDayField.getText().trim());
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

        // =========================
        // WELLNESS GUIDE TAB
        // =========================
        JPanel wellnessPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        wellnessPanel.setBackground(tabColor);

        JButton showAdviceBtn = new JButton("Show Wellness Advice");
        showAdviceBtn.setBackground(buttonColor);

        wellnessPanel.add(new JLabel("Get wellness advice:"));
        wellnessPanel.add(showAdviceBtn);

        showAdviceBtn.addActionListener(e -> {
            WellnessGuide guide = new WellnessGuide(moodMonitor, symptomMonitor);
            outputArea.append(guide.giveAdvice() + "\n");
        });

        tabs.addTab("Wellness Guide", wellnessPanel);

        // =========================
        // CLEAR OUTPUT BUTTON
        // =========================
        JButton clearOutputBtn = new JButton("Clear Output");
        clearOutputBtn.setBackground(Color.RED);
        clearOutputBtn.setForeground(Color.WHITE);
        clearOutputBtn.addActionListener(e -> outputArea.setText(""));

        // =========================
        // ADD COMPONENTS
        // =========================
        add(tabs, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(clearOutputBtn, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CycleCareMainMenu().setVisible(true));
    }
}
