package com.example.skylink.presentation.SeatSelect;

public class SeatStatus {
    private boolean isBusinessClass;
    private boolean isTaken;

    SeatStatus(boolean isBusinessClass, boolean isTaken) {
        this.isBusinessClass = isBusinessClass;
        this.isTaken = isTaken;
    }

    boolean isBusinessClass() {
        return isBusinessClass;
    }

    boolean isTaken() {
        return isTaken;
    }
}
