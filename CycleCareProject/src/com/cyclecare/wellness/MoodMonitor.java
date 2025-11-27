package com.cyclecare.wellness;

import com.cyclecare.exceptions.InvalidInputException;

public class MoodMonitor extends Monitor<Mood> {

    public void addMood(String moodName) {
        try {
            if (moodName == null || moodName.trim().isEmpty()) {
                throw new InvalidInputException(" Mood cannot be empty.");
            }
            //add record
            addRecord(new Mood(moodName));
        }
        catch (InvalidInputException e) {
            System.out.println("Error! " + e.getMessage());
        }
    }

    @Override
    public void displayRecords() {
        if (list.isEmpty()) {
            System.out.println("No mood records available.");
            return;
        }

        System.out.println("Your Mood Records:");
        for (Mood m : list) {
            System.out.println("- " + m);
        }
    }
}
