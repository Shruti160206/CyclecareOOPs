package com.cyclecare.mainMenu;

import com.cyclecare.tracker.CycleDataProcessor;
import com.cyclecare.wellness.MoodMonitor;
import com.cyclecare.wellness.SymptomMonitor;
import com.cyclecare.exceptions.InvalidCycleDataException;

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
                setSize(800, 650);
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                setLocationRelativeTo(null);

                JTabbedPane tabs = new JTabbedPane();

                // ================= User Details Tab =================
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
                        statusLabel.setText("User created: " + name + ", Age: " + age);
                        outputArea.append("Welcome, " + name + "!\n");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Invalid name or age!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                });

                tabs.addTab("User Details", userPanel);

                // ================= Cycle Tracker Tab =================
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
                        JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                });

                showAvgBtn.addActionListener(e -> {
                    if (cycleProcessor != null) {
                        outputArea.append("Average Cycle Length: " + cycleProcessor.getAverageCycleLength() + " days\n");
                    } else outputArea.append("Add period dates first.\n");
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
                        outputArea.append("Next Period Estimated: " + next + "\n");
                    } else outputArea.append("Add period dates first.\n");
                });

                tabs.addTab("Cycle Tracker", cyclePanel);

                // ================= Mood Tracker Tab =================
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



                tabs.addTab("Mood Tracker", moodPanel);

                // ================= Symptom Tracker Tab =================
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
                    String name = symptomField.getText().trim();
                    try {
                        int severity = Integer.parseInt(severityField.getText().trim());
                        if (!name.isEmpty()) {
                            symptomMonitor.addSymptom(name, severity);
                            outputArea.append("Symptom added: " + name + " (Severity: " + severity + ")\n");
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Invalid severity!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                });

                showSymptomBtn.addActionListener(e -> {
                    symptomMonitor.displayRecords();
                    outputArea.append("Average Severity: " + symptomMonitor.calculateAverageSeverity() + "\n");


                });

                tabs.addTab("Symptom Tracker", symptomPanel);

                // ================= Output Area =================
                outputArea = new JTextArea();
                outputArea.setEditable(false);
                JScrollPane scrollPane = new JScrollPane(outputArea);

                add(tabs, BorderLayout.NORTH);
                add(scrollPane, BorderLayout.CENTER);
            }

            public static void main(String[] args) {
                SwingUtilities.invokeLater(() -> {
                    CycleCareMainMenu gui = new CycleCareMainMenu();
                    gui.setVisible(true);
                });
            }
        }



