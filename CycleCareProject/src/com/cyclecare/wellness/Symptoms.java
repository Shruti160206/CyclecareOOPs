package com.cyclecare.wellness;

public class Symptoms {
    private String name;
    private int severity;

    public Symptoms(String name, int severity) {
        this.name = name;
        this.severity = severity;
    }

    public String getName(){
        return name;
    }
    public int getSeverity(){
        return severity;
    }

    @Override
    public String toString() {
        return name + "(Severity: " + severity + ")";
    }

}
