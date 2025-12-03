package com.cyclecare.wellness;

import com.cyclecare.exceptions.InvalidInputException;
import java.util.ArrayList;


public abstract class Monitor<M> {
    protected ArrayList<M> list = new ArrayList<>();

    public void addRecord(M record) throws InvalidInputException {
        if (record == null) {
            throw new InvalidInputException("Record cannot be null.");
        }
        list.add(record);
    }
    public ArrayList<M> getRecords() {
        return list;
    }

    public abstract void displayRecords();

}
