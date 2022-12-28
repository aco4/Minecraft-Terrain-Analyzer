package com.gmail.anthonythegu.terrainanalyzer;


public class RollingAverage {

    private int sum;
    private int count;

    public float get() {
        if (count == 0)
            throw new IllegalStateException("Cannot get rolling average: no data to calculate");
        return (float) sum / count;
    }

    public void add(int num) {
        sum += num;
        count++;
    }
}