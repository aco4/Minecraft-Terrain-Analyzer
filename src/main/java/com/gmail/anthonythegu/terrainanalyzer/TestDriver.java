package com.gmail.anthonythegu.terrainanalyzer;

import java.util.Scanner;

public class TestDriver {
    public static void main(String[] args) {
        RollingMode mode = new RollingMode();

        Scanner in = new Scanner(System.in);

        for (int i = 0; i < 10; i++)
            mode.add(in.nextInt());

        System.out.println();
        System.out.println(mode.get());

        in.close();
    }
}
