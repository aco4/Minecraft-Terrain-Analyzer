package com.gmail.anthonythegu.terrainanalyzer;

public class Statistics {
    private RollingAverage rollingAverage;
    private RollingMode rollingMode;
    private int count;

    public Statistics() {
        rollingAverage = new RollingAverage();
        rollingMode = new RollingMode();
    }

    public void add(int num) {
        rollingAverage.add(num);
        rollingMode.add(num);
        count++;
    }

    public float average() {
        return rollingAverage.get();
    }

    public int mode() {
        return rollingMode.get();
    }

    public int count() {
        return count;
    }
}
