package com.cyclecare.tracker;

import java.time.LocalDate;
import java.util.List;
import com.cyclecare.exceptions.InvalidCycleDataException;

public class CycleDataProcessor extends CycleCalculator {

    public CycleDataProcessor(List<LocalDate> history) throws InvalidCycleDataException {
        super(history);
        validateData(history);   // may throw custom exception
    }

    //Using the custom-made Exception from the exceptions package
    private void validateData(List<LocalDate> history) throws InvalidCycleDataException {
        if (history == null || history.isEmpty()) {
            throw new InvalidCycleDataException("Period history cannot be empty.");
        }
        if (history.size() == 1) {
            throw new InvalidCycleDataException("At least two dates are required.");
        }

        // check increasing order
        for (int i = 0; i < history.size() - 1; i++) {
            if (history.get(i + 1).isBefore(history.get(i))) {
                throw new InvalidCycleDataException("Dates must be in chronological order.");
            }
        }
    }


    //Polymorphism method: overriding the parent class CycleCalculator

    @Override
    public LocalDate estimateNextPeriod() {
        try {
            // re-validate before prediction
            validateData(getPeriodHistory());

            // parent class logic reused
            return super.estimateNextPeriod();

        } catch (InvalidCycleDataException e) {
            System.out.println("Error while estimating next period: " + e.getMessage());
            return null;
        }
    }


    //Average calculation

    public double getAverageDifference() {
        int[] diffs = getAllCycleDifferences();

        try {
            if (diffs.length == 0) {
                throw new InvalidCycleDataException("Not enough data to compute average.");
            }
            return computeAverage(diffs);

        } catch (InvalidCycleDataException e) {
            System.out.println("Average calculation failed: " + e.getMessage());
            return 0;
        }
    }

    public double getVariance() {
        int[] diffs = getAllCycleDifferences();

        try {
            if (diffs.length == 0) {
                throw new InvalidCycleDataException("Not enough data to compute variance.");
            }

            double mean = computeAverage(diffs);
            return computeVariance(diffs, mean);

        } catch (InvalidCycleDataException e) {
            System.out.println("Variance calculation failed: " + e.getMessage());
            return 0;
        }
    }



    private double computeAverage(int[] values) throws InvalidCycleDataException {
        if (values.length == 0) {
            throw new InvalidCycleDataException("Cannot compute average of empty array.");
        }
        double sum = 0;
        for (int v : values) sum += v;
        return sum / values.length;
    }

    private double computeVariance(int[] values, double mean) throws InvalidCycleDataException {
        if (values.length == 0) {
            throw new InvalidCycleDataException("Cannot compute variance of empty array.");
        }
        double sumSq = 0;
        for (int v : values) sumSq += Math.pow(v - mean, 2);
        return sumSq / values.length;
    }
}
