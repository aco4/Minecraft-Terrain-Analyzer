package com.gmail.anthonythegu.terrainanalyzer;

public class Statistics {
    private RollingAverage rollingAverage;
    private RollingMode rollingMode;

    public Statistics() {
        rollingAverage = new RollingAverage();
        rollingMode = new RollingMode();
    }

    public void add(int num) {
        rollingAverage.add(num);
        rollingMode.add(num);
    }

    public int average() {
        return rollingAverage.get();
    }

    public int mode() {
        return rollingMode.get();
    }
}
