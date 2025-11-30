package com.cyclecare.tracker;

interface PhaseDetector {
    String detectPhase(int dayInCycle) throws InvalidCycleDataException;
}

class InvalidCycleDataException extends Exception {
    public InvalidCycleDataException(String msg) {
        super(msg);
    }
}

abstract class AbstractCycleHelper {
    protected int cycleLength;

    public AbstractCycleHelper(int cycleLength) {
        this.cycleLength = cycleLength;
    }

    public abstract boolean isValidDay(int day);
}

public class MyCyclePhase extends AbstractCycleHelper implements PhaseDetector {

    public MyCyclePhase(int cycleLength) {
        super(cycleLength);
    }

    @Override
    public boolean isValidDay(int day) {
        return day >= 1 && day <= cycleLength;
    }

    @Override
    public String detectPhase(int dayInCycle) throws InvalidCycleDataException {
        try {
            if (!isValidDay(dayInCycle)) {
                throw new InvalidCycleDataException("Invalid day: " + dayInCycle +
                        " (must be 1-" + cycleLength + ")");
            }

            // EXACTLY from PDF: "Divides cycle into biological phases using day ranges"
            if (dayInCycle >= 1 && dayInCycle <= 7) {
                return "Menstrual Phase";
            } else if (dayInCycle >= 8 && dayInCycle <= 13) {
                return "Follicular Phase";
            } else if (dayInCycle == 14) {
                return "Ovulation Phase";
            } else {
                return "Luteal Phase";
            }

        } catch (InvalidCycleDataException e) {
            throw e;
        }
    }
}