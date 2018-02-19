package edu.kit.informatik.constructs.math;

public class IntRange {
    private int lowerBound;
    private int upperBound;

    public IntRange(int lowerBound, int upperBound) {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    public int getLowerBound() {
        return lowerBound;
    }

    public int getUpperBound() {
        return upperBound;
    }
}
