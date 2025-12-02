package com.cyclecare.wellness;
import java.util.ArrayList;
public class WellnessGuide implements Advisor {
    private final MoodMonitor moodMonitor;
    private final SymptomMonitor symptomMonitor;
    // Stores final advice lines
    private final ArrayList<String> adviceList = new ArrayList<>();
    public WellnessGuide(MoodMonitor moodMonitor, SymptomMonitor symptomMonitor) {
        if (moodMonitor == null || symptomMonitor == null) {
            throw new IllegalArgumentException("Monitors cannot be null.");
        }
        this.moodMonitor = moodMonitor;
        this.symptomMonitor = symptomMonitor;
    }
    @Override
    public String giveAdvice() {
        // regenerate advice lines
        ArrayList<String> lines = getAdviceLines();
        StringBuilder sb = new StringBuilder();
        sb.append("\n--- Wellness Advice ---\n");
        for (String tip : lines) {
            sb.append(tip).append("\n");
        }
        sb.append("\nNote: This advice is general and not medical guidance.\n");
        return sb.toString();
    }
    public ArrayList<String> getAdviceLines() {
        adviceList.clear(); // remove old advice
        // Collect advice
        adviceList.addAll(analyzeMood());
        adviceList.addAll(analyzeSymptoms());
        return new ArrayList<>(adviceList);
    }
    // MOOD ANALYSIS
    private ArrayList<String> analyzeMood() {
        ArrayList<String> moodAdvice = new ArrayList<>();

        if (moodMonitor.getRecords().isEmpty()) {
            moodAdvice.add("Mood Advice: No mood data recorded.");
            return moodAdvice;
        }
        // Get latest mood
        Mood lastMood = moodMonitor.getRecords()
                .get(moodMonitor.getRecords().size() - 1);

        String mood = lastMood.getMoodName();
        if (mood == null) {
            moodAdvice.add("Mood Advice: Mood information is unclear.");
            return moodAdvice;
        }
        switch (mood.toLowerCase()) {
            case "happy":
                moodAdvice.add("Mood Advice: You seem happy! Keep doing activities you enjoy.");
                break;
            case "sad":
                moodAdvice.add("Mood Advice: Feeling sad is okay. Try gentle movement or talk to someone you trust.");
                break;
            case "stressed":
                moodAdvice.add("Mood Advice: Try deep breathing or take a short break to relax.");
                break;
            case "angry":
                moodAdvice.add("Mood Advice: Slow breathing or a short walk may help calm your mind.");
                break;
            case "anxious":
                moodAdvice.add("Mood Advice: Try grounding techniques and limit screen time.");
                break;
            default:
                moodAdvice.add("Mood Advice: Be kind to yourself and take small breaks when needed.");
        }

        return moodAdvice;
    }
    // SYMPTOM ANALYSIS
    private ArrayList<String> analyzeSymptoms() {
        ArrayList<String> symptomAdvice = new ArrayList<>();

        if (symptomMonitor.getRecords().isEmpty()) {
            symptomAdvice.add("Symptom Advice: No symptoms recorded.");
            return symptomAdvice;
        }

        // Severity-based general advice
        double avg = symptomMonitor.calculateAverageSeverity();

        if (avg >= 7) {
            symptomAdvice.add("Symptom Advice: Symptoms are severe. Consider resting more or consulting a doctor.");
        } else if (avg >= 4) {
            symptomAdvice.add("Symptom Advice: Moderate symptoms. Drink water, rest, and avoid stress.");
        } else {
            symptomAdvice.add("Symptom Advice: Mild symptoms. Keep tracking your health.");
        }

        // Specific tips for each of the 7 symptoms
        symptomMonitor.getRecords().forEach(s -> {
            String name = s.getName().toLowerCase();

            if (name.contains("cramp")) {
                symptomAdvice.add("• Cramps Tip: Use a heating pad and do light stretching.");
            }
            else if (name.contains("bloating")) {
                symptomAdvice.add("• Bloating Tip: Avoid salty foods and drink warm water.");
            }
            else if (name.contains("back pain")) {
                symptomAdvice.add("• Back Pain Tip: Apply heat and avoid heavy lifting.");
            }
            else if (name.contains("fatigue") || name.contains("tired")) {
                symptomAdvice.add("• Fatigue Tip: Prioritize sleep and eat iron-rich foods.");
            }
            else if (name.contains("headache")) {
                symptomAdvice.add("• Headache Tip: Drink water, rest your eyes, and sit in low light.");
            }
            else if (name.contains("mood swings")) {
                symptomAdvice.add("• Mood Swings Tip: Try calming activities and maintain regular meals.");
            }
            else if (name.contains("acne") || name.contains("breakout")) {
                symptomAdvice.add("• Acne Tip: Use gentle skincare and avoid touching your face.");
            }
        });

        return symptomAdvice;
    }
}