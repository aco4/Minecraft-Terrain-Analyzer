package com.gmail.anthonythegu.terrainanalyzer;

import java.util.Arrays;
import java.util.Scanner;

public class TestDriver {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        String test = "";

        String out1 = test.toUpperCase();

        String[] out = out1.split(",");
        System.out.println(Arrays.toString(out));

        in.close();
    }
}
