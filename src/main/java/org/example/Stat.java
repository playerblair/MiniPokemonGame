package org.example;

public class Stat {
    private final double[] STAGES = {2.0 / 8.0, 2.0 / 7.0, 2.0 / 6.0, 2.0 / 5.0, 2.0 / 4.0, 2.0 / 3.0, 1.0, 3.0 / 2.0, 4.0 / 2.0, 5.0 / 2.0, 6.0 / 2.0, 7.0 / 2.0, 8.0 / 2.0};
    private final int value;
    private int stagePointer;

    public Stat(int value) {
        this.value = value;
        this.stagePointer = 6;
    }

    public double getValue() {
        return Math.round((value * STAGES[stagePointer]) * 100.00) / 100.0;
    }

    public void lower() {
        if (stagePointer > 0) {
            stagePointer--;
        }
    }

    public void raise() {
        if (stagePointer < 12) {
            stagePointer++;
        }
    }
}
