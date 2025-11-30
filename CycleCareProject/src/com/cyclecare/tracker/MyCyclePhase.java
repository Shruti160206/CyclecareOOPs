package com.cyclecare.tracker;
import java.util.InputMismatchException;
import java.util.Scanner;




class InvalidCycleDayException extends Exception {
    public InvalidCycleDayException(String message) {
        super(message);
    }
}


public class MyCyclePhase {

    private int cycleLength = 28;

    public MyCyclePhase() {
    }

    public MyCyclePhase(int cycleLength) {
        if (cycleLength > 0) {
            this.cycleLength = cycleLength;
        }
    }

    public int getCycleLength() {
        return cycleLength;
    }

    public void setCycleLength(int cycleLength) {
        if (cycleLength > 0) {
            this.cycleLength = cycleLength;
        }
    }


    public String detectPhase(Integer dayInCycle) throws InvalidCycleDayException {
        if (dayInCycle == null) {
            throw new InvalidCycleDayException("Day in cycle cannot be null.");
        }
        if (dayInCycle < 1 || dayInCycle > cycleLength) {
            throw new InvalidCycleDayException(
                    "Day in cycle must be between 1 and " + cycleLength + ".");
        }

        if (dayInCycle >= 1 && dayInCycle <= 7) {
            return "Menstrual Phase";
        } else if (dayInCycle >= 8 && dayInCycle <= 13) {
            return "Follicular Phase";
        } else if (dayInCycle == 14) {
            return "Ovulation Phase";
        } else {
            return "Luteal Phase";
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MyCyclePhase myCyclePhase = new MyCyclePhase(); // default 28 days

        try {
            System.out.print("Enter days since your last period started (1-28): ");
            int dayInCycle = scanner.nextInt();

            String phase = myCyclePhase.detectPhase(dayInCycle);
            System.out.println("You are currently in: " + phase);

        } catch (InputMismatchException ime) {
            System.out.println("Invalid input. Please enter a whole number.");
        } catch (InvalidCycleDayException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
