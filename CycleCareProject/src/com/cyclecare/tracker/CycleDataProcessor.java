package com.cyclecare.tracker;

import exception.InvalidCycleDataException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

// Child class extending parent CycleCalculator
public class CycleDataProcessor extends CycleCalculator {

    private List<LocalDate> periodDates = new ArrayList<>();
    private double regularityScore = 0;

    // Constructor: child passes the list to parent
    public CycleDataProcessor(List<LocalDate> history) {
        super(history);
        this.periodDates = history;
    }


    //Adding a new period date
    public void addPeriodDate(LocalDate date) throws InvalidCycleDataException {
        if (date == null) {
            throw new InvalidCycleDataException("Date cannot be null.");
        }
        periodDates.add(date);
        setPeriodHistory(periodDates);   // update parent class
    }


    //CALCULATE AVERAGE CYCLE LENGTH (using parent method)

    public void calculateAverageLength() {
        try {
            if (periodDates.size() < 2) {
                throw new InvalidCycleDataException("At least 2 dates are required.");
            }

            int avg = getAverageCycleLength();
            System.out.println("Average Cycle Length: " + avg);

        } catch (InvalidCycleDataException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    // ESTIMATE NEXT PERIOD
    public void estimateNextPeriod() {
        try {
            if (periodDates.isEmpty()) {
                throw new InvalidCycleDataException("No period data available.");
            }

            LocalDate next = periodEstimator();   // parent method
            System.out.println("Next Estimated Period: " + next);

        } catch (InvalidCycleDataException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }


    //CALCULATE REGULARITY SCORE

    public void calculateCycleRegularity() {
        try {
            if (periodDates.size() < 3) {
                regularityScore = 1.0;    // assume perfect regularity
                System.out.println("Regularity Score: " + regularityScore);
                return;
            }

            List<Integer> lengths = new ArrayList<>();

            for (int i = 0; i < periodDates.size() - 1; i++) {
                int diff = (int) ChronoUnit.DAYS.between(periodDates.get(i), periodDates.get(i + 1));

                if (diff <= 0) {
                    throw new InvalidCycleDataException("Cycle length must be positive.");
                }

                lengths.add(diff);
            }

            double avg = checkAverage(lengths);
            double var = checkVariance(lengths, avg);

            regularityScore = 1 / (1 + var);
            System.out.println("Regularity Score: " + regularityScore);

        } catch (InvalidCycleDataException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
    //Calculate avg and use our custom excpetion

    private double checkAverage(List<Integer> values) throws InvalidCycleDataException {
        if (values.isEmpty()) {
            throw new InvalidCycleDataException("Cannot compute average of empty list.");
        }
        double sum = 0;
        for (int v : values) sum += v;
        return sum / values.size();
    }


    //Check Variance
    private double checkVariance(List<Integer> values, double mean) throws InvalidCycleDataException {
        if (values.isEmpty()) {
            throw new InvalidCycleDataException("Cannot compute variance of empty list.");
        }
        double sumSq = 0;
        for (int v : values) {
            double d = v - mean;
            sumSq += d * d;
        }
        return sumSq / values.size();   // population variance
    }
}
