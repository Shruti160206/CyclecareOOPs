package com.cyclecare.tracker;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class CycleCalculator {
    private List<LocalDate> periodHistory;
    private int averageCycleLength;

    public CycleCalculator(List<LocalDate> periodHistory){
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
    //calculating Average cycle length
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
}
