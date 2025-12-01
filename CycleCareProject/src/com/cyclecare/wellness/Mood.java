package com.cyclecare.wellness;

public class Mood {
private final String moodName;

    public Mood(String moodName) {
        this.moodName = moodName;
    }

    public String getMoodName() {
        return moodName;
    }

    @Override
    public String toString() {
        return "Mood: " + moodName;
    }
}
