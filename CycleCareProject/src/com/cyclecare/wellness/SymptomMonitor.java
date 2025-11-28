package com.cyclecare.wellness;

import com.cyclecare.exceptions.InvalidInputException;

public class SymptomMonitor extends Monitor<Symptoms> {
    private final String[] symptomList = {"Cramps", "Bloating", "Back Pain", "Fatigue", "Headache", "Mood Swings", "Acne/Breakouts"};

    // This method will show the available symptoms the user can choose from
    public void symptomsMenu() {
        System.out.println("\nSelect from the following symptoms:");
        for (int i = 0; i < symptomList.length; i++) {
            System.out.println((i + 1) + ". " + symptomList[i]);
        }
        System.out.println("Enter 0 to exit.");
    }

    //User chooses symptoms along with its severity in this method
    public void chooseSymptom_Severity(int choice, int severity) {
        try {
            if (choice < 1 || choice > symptomList.length) {
                throw new InvalidInputException("Invalid symptom choice selected.");
            }

            if (severity < 1 || severity > 10) {
                throw new InvalidInputException("Severity must be between 1 and 10.");
            }

            String selectedSymptom = symptomList[choice - 1];
            addRecord(new Symptoms(selectedSymptom, severity));
        } catch (InvalidInputException e) {
            System.out.println("Error adding symptom: " + e.getMessage());
        }
    }

    @Override
    public void displayRecords() {
        System.out.println("\nYour Selected Symptoms:");
        for (Symptoms s : list) {
            System.out.println("- " + s);
        }
    }

    // This method calculates the Average severity based on the user's severity entry
    public double calculateAverageSeverity() {
        if (list.isEmpty()) {
            return 0;
        }

        int total = 0;
        for (Symptoms s : list) {
            total += s.getSeverity();
        }
        return total / (double) list.size();
    }
}

