package com.gmail.anthonythegu.terrainanalyzer;


public class RollingAverage {

    private int sum;
    private int count;

    public int get() {
        return sum / count;
    }

    public void add(int num) {
        sum += num;
        count++;
    }
}