package com.cyclecare.tracker;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class CycleCalculator {
    private List<LocalDate> periodHistory;
    private int averageCycleLength;

    public CycleCalculator(List<LocalDate> periodHistory) {
        this.periodHistory = periodHistory;
        this.averageCycleLength = calculateAverage();
    }
    public int getAverageCycleLength() {
        return averageCycleLength;
    }

    public List<LocalDate> getPeriodHistory() {
        return periodHistory;
    }

    public void setPeriodHistory(List<LocalDate> periodHistory) {
        this.periodHistory = periodHistory;
        this.averageCycleLength = calculateAverage();
    }

    //calculating Average cycle length from all past cycles
    private int calculateAverage() {
        if (periodHistory == null || periodHistory.size() < 2) {
            return 28; // default
        }
        long totalDays = 0;

        for (int i = 0; i < periodHistory.size() - 1; i++) {
            long diff = ChronoUnit.DAYS.between(periodHistory.get(i), periodHistory.get(i + 1));
            totalDays += diff;
        }

        return (int) (totalDays / (periodHistory.size() - 1));
    }

    //updates the average cycle length after you add a new period date.
    public void recalculateAverage() {
        this.averageCycleLength = calculateAverage();
    }
    //estimating next period
    public LocalDate estimateNextPeriod() {
        if (periodHistory == null || periodHistory.isEmpty()) {
            return null;
        }
        LocalDate lastDate = periodHistory.get(periodHistory.size() - 1);
        return lastDate.plusDays(averageCycleLength);
    }
    //calculating all cycle lengths
    public int[] getAllCycleDifferences() {
        if (periodHistory == null || periodHistory.size() < 2) {
            return new int[]{};
        }

        int[] lengths = new int[periodHistory.size() - 1];

        for (int i = 0; i < periodHistory.size() - 1; i++) {
            lengths[i] = (int) ChronoUnit.DAYS.between(periodHistory.get(i), periodHistory.get(i + 1));
        }

        return lengths;
    }
    // adding exception
    public static class InvalidCycleValueException extends Exception {
        public InvalidCycleValueException(String message) { super(message); }
        public InvalidCycleValueException() { super("Invalid cycle value provided."); }
    }
}

