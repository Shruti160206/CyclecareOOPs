package com.cyclecare.tracker;

import exception.InvalidCycleDataException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

//Using Inheritance class
public class CycleDataProcessor extends CycleCalculator {

    private List<LocalDate> periodDates = new ArrayList<>();
    private double regularityScore = 0;


    // adding the period date, mentioning the invalid message
    public void addPeriodDate(LocalDate date) {
        if (date == null) {
            throw new InvalidCycleDataException("Date cannot be null.");
        }
        periodDates.add(date);
    }

    //Calculating the avg length
    public void calculateAverageLength() {
        try {
            if (periodDates.size() < 2) {
                throw new InvalidCycleDataException("At least 2 dates required.");
            }

            int avg = avgCycleLength(periodDates); // from parent class
            System.out.println("Average Cycle Length: " + avg);

        } catch (InvalidCycleDataException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    //Method to predict the period dates

    public void estimateNextPeriod() {
        try {
            if (periodDates.isEmpty()) {
                throw new InvalidCycleDataException("At least 1 period date required.");
            }

            int avg = avgCycleLength(periodDates);
            LocalDate last = periodDates.get(periodDates.size() - 1);

            LocalDate next = periodEstimator(last, avg);
            System.out.println("Next Estimated Period: " + next);

        } catch (InvalidCycleDataException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    //Calculating the cycle regularity

    public void calculateCycleRegularity() {
        try {

            if (periodDates.size() < 3) {
                regularityScore = 1.0;  // assume perfectly regular
                System.out.println("Regularity Score: " + regularityScore);
                return;
            }

            // build cycle lengths array list
            List<Integer> cycleLengths = new ArrayList<>();

            for (int i = 0; i < periodDates.size() - 1; i++) {
                int diff = periodDates.get(i).until(periodDates.get(i + 1)).getDays();

                if (diff <= 0) {   // prevent invalid cycle lengths
                    throw new InvalidCycleDataException("Cycle length must be positive.");
                }

                cycleLengths.add(diff);
            }

            double avg = checkAverage(cycleLengths);
            double variance = checkVariance(cycleLengths, avg);

            regularityScore = 1 / (1 + variance);
            System.out.println("Regularity Score: " + regularityScore);

        } catch (InvalidCycleDataException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    //Finding the average and also using the exception
    private double checkAverage(List<Integer> values) throws InvalidCycleDataException {
        if (values.isEmpty()) {
            throw new InvalidCycleDataException("List cannot be empty.");
        }

        double sum = 0;
        for (int v : values) sum += v;

        return sum / values.size();
    }


    //throws usage
    private double checkVariance(List<Integer> values, double mean) throws InvalidCycleDataException {
        if (values.isEmpty()) {
            throw new InvalidCycleDataException("List cannot be empty.");
        }

        double sumSq = 0;
        for (int v : values) {
            double diff = v - mean;
            sumSq += diff * diff;
        }

        return sumSq / values.size();  // population variance
    }
}
