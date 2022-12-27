package com.gmail.anthonythegu.terrainanalyzer;


public class RollingAverage {

    private int sum;
    private int count;

    public int get() {
        return count == 0 ? 0 : sum / count;
    }

    public void add(int num) {
        sum += num;
        count++;
    }
}