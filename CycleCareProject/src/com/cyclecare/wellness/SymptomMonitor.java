package com.cyclecare.wellness;

import com.cyclecare.exceptions.InvalidInputException;

public class SymptomMonitor extends Monitor<Symptoms>{
    public void addSymptom(String name, int severity) {
        try {
            if (name == null || name.isEmpty()) {
                throw new InvalidInputException("Symptom name cannot be empty.");
            }
            if (severity < 1 || severity > 10) {
                throw new InvalidInputException("Severity must be between 1 and 10.");
            }

            addRecord(new Symptoms(name, severity));
        }
        catch (InvalidInputException e) {
            System.out.println("Error adding symptom: " + e.getMessage());
        }
    }

    @Override
    public void displayRecords() {
        System.out.println("Your Symptom Records:");
        for (Symptoms s : list) {
            System.out.println("- " + s);
        }
    }

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

