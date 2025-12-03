package com.cyclecare.tracker;

import java.time.LocalDate;
import java.util.List;

@SuppressWarnings("unused")
public class CycleCalculator {

    private List<LocalDate> periodHistory;
    private int avgCycleLength;

    public CycleCalculator(List<LocalDate> periodHistory) {

        setPeriodHistory(periodHistory);
    }

    public int getAverageCycleLength() {

        return avgCycleLength;
    }

    public List<LocalDate> getPeriodHistory() {

        return periodHistory;
    }

    public void setPeriodHistory(List<LocalDate> periodHistory) {
        this.periodHistory = periodHistory;
        this.avgCycleLength = calculateAverage();
    }

    // Calculate average cycle length safely
    private int calculateAverage() {
        if (periodHistory == null || periodHistory.size() < 2) {
            return 28; // Default fallback
        }

        long totalDays = 0;
        for (int i = 0; i < periodHistory.size() - 1; i++) {
            totalDays += getDaysBetween(periodHistory.get(i), periodHistory.get(i + 1));
        }
        return (int) (totalDays / (periodHistory.size() - 1));
    }

    // Predict the next expected period date
    public LocalDate estimateNextPeriod() {
        if (periodHistory == null || periodHistory.isEmpty()) {
            return null;
        }
        LocalDate lastDate = periodHistory.getLast();
        return lastDate.plusDays(avgCycleLength);
    }

    // Return all cycle differences as an int array
    public int[] getAllCycleDifferences() {
        if (periodHistory == null || periodHistory.size() < 2) {
            return new int[] {};
        }

        int[] differences = new int[periodHistory.size() - 1];
        for (int i = 0; i < periodHistory.size() - 1; i++) {
            differences[i] = (int) getDaysBetween(periodHistory.get(i), periodHistory.get(i + 1));
        }
        return differences;
    }

    // Custom method to calculate days between two dates
    private long getDaysBetween(LocalDate start, LocalDate end) {
        return end.toEpochDay() - start.toEpochDay();
    }
}
