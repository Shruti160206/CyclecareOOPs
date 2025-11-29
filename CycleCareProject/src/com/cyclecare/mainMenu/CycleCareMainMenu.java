package com.cyclecare.mainMenu;

import com.cyclecare.tracker.CycleDataProcessor;
import java.time.LocalDate;
import java.util.List;

public class CycleCareMainMenu {
    public static void main(String[] args) throws Exception {

        List<LocalDate> history = List.of(
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2024, 1, 30),
                LocalDate.of(2024, 2, 28)
        );

        CycleDataProcessor processor = new CycleDataProcessor(history);

        processor.getAverageDifference();
        processor.getVariance();
    }
}

