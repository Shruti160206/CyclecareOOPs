package com.cyclecare.user;

public class PeriodRecords {
    private int[] startDay = new int[50];
    private int[] startMonth = new int[50];
    private int[] startYear = new int[50];

    private int[] endDay = new int[50];
    private int[] endMonth = new int[50];
    private int[] endYear = new int[50];

    private int count = 0;  // variable for number of stored records

    // Method to add a record
    public void addPeriodRecord(int sd, int sm, int sy, int ed, int em, int ey) {
        try {
            if (ey < sy ||
                    (ey == sy && em < sm) ||
                    (ey == sy && em == sm && ed < sd))
            {
                throw new Exception("End date cannot be before start date!");
            }
            startDay[count] = sd;
            startMonth[count] = sm;
            startYear[count] = sy;

            endDay[count] = ed;
            endMonth[count] = em;
            endYear[count] = ey;

            count++;

            System.out.println("Period record added successfully.");

        } catch (Exception e) {
            System.out.println("Error adding period record: " + e.getMessage());
        }
    }

    // Method to calculate duration
    public int calculateDuration(int index) {
        if (startMonth[index] == endMonth[index] &&
                startYear[index] == endYear[index])
        {
            return (endDay[index] - startDay[index] + 1);
        }

        // If the month changes in between, this condition is applied
        return (endDay[index] + (30 - startDay[index]) + 1);
    }

    //Method to display the stored Records
    public void printRecords() {

        if (count == 0) {
            System.out.println("No period records found.");
            return;
        }

        System.out.println("\nPERIOD HISTORY:");

        for (int i = 0; i < count; i++) {
            System.out.println((i + 1) + ") "
                    + startDay[i] + "/" + startMonth[i] + "/" + startYear[i]
                    + " : "
                    + endDay[i] + "/" + endMonth[i] + "/" + endYear[i]
                    + "  | Duration: " + calculateDuration(i) + " days"
            );
        }
    }

    public int getCount() {
        return count;
    }

}
