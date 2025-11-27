package com.cyclecare.tracker;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class CycleDataProcessor extends CycleCalculator {


    private List<LocalDate> periodDates = new ArrayList<>();
    private double regularityScore = 0;


    public void addPeriodDate(LocalDate date) {
        periodDates.add(date);
    }


    public void calculateAverageLength() {
        if (periodDates.size() < 2) {
            System.out.println("Not enough data to calculate average length.");
            return;
        }

        int avg = avgCycleLength(periodDates); // inherited method
        System.out.println("Average Cycle Length: " + avg);
    }


    public void estimateNextPeriod() {
        if (periodDates.size() < 1) {
            System.out.println("No past period dates available.");
            return;
        }

        int avg = avgCycleLength(periodDates);
        LocalDate last = periodDates.get(periodDates.size() - 1);

        LocalDate next = periodEstimator(last, avg); // inherited method

        System.out.println("Next Estimated Period: " + next);
    }


    public void calculateCycleRegularity() {
        if (periodDates.size() < 3) {
            regularityScore = 1.0;
            System.out.println("Regularity Score: " + regularityScore);
            return;
        }


        List<Integer> cycleLengths = new ArrayList<>();
        for (int i = 0; i < periodDates.size() - 1; i++) {
            int diff = periodDates.get(i).until(periodDates.get(i + 1)).getDays();
            cycleLengths.add(diff);
        }

        double avg = computeAverage(cycleLengths);
        double variance = computeVariance(cycleLengths, avg);
        regularityScore = 1 / (1 + variance);

        System.out.println("Regularity Score: " + regularityScore);
    }


    private double checkAverage(List<Integer> values) {
        double sum = 0;
        for (int v : values) sum += v;
        return sum / values.size();
    }

    private double checkVariance(List<Integer> values, double mean) {
        double sumSq = 0;
        for (int v : values) sumSq += Math.pow(v - mean, 2);
        return sumSq / values.size();
    }
}

