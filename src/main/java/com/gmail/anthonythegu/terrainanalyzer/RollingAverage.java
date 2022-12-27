package com.gmail.anthonythegu.terrainanalyzer;


public class RollingAverage {

    private int sum;
    private int count;

    public int get() {
        if (count == 0)
            throw new IllegalStateException("Cannot get rolling average: no data to calculate");
        return sum / count;
    }

    public void add(int num) {
        sum += num;
        count++;
    }
}