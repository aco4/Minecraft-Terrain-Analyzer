package com.gmail.anthonythegu.terrainanalyzer;

import java.util.Hashtable;
import java.util.Map;

public class RollingMode {
    // Hashtable<Y-coordinate, occurrences>
    private Hashtable<Integer, Integer> frequencies;
    private static final int WORLD_HEIGHT = 384;

    // Hashtable that will not resize
    public RollingMode() {frequencies = new Hashtable<>(WORLD_HEIGHT + 1, 1.0f);}
    public void reset() {frequencies = new Hashtable<>(WORLD_HEIGHT + 1, 1.0f);}

    public int get() {
        if (frequencies.size() == 0)
            throw new IllegalStateException("RollingMode contains no values");

        int mode = 0;
        int highestFreq = 0;
        for (Map.Entry<Integer, Integer> e : frequencies.entrySet()) {
            if (e.getValue() > highestFreq) {
                highestFreq = e.getValue();
                mode = e.getKey();
            }
        }
        return mode;
    }

    public void add(int num) {
        // If an existing key is passed then the previous value gets replaced by the new value.

        // Increase the frequency of this number by 1
        if (frequencies.containsKey(num)) {
            frequencies.put(num, frequencies.get(num) + 1);
        } else {
            frequencies.put(num, 1);
        }
    }
}