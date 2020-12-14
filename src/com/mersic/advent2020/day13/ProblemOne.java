package com.mersic.advent2020.day13;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ProblemOne {
    public static void main(String args[]) throws Exception {
        String filename = "./resources/day13.1.input";
        List<String> lines = Files.readAllLines(Path.of(filename));

        int departTime = Integer.parseInt(lines.get(0));
        List<Integer> buses = Arrays.stream((lines.get(1).split(","))).filter(s->!s.equals("x")).map(s->Integer.parseInt(s)).collect(Collectors.toList());

        int minWaitTime = Integer.MAX_VALUE;
        int busId = Integer.MIN_VALUE;

        for (int b : buses) {
            int waitTime = (b - (departTime % b)) % b;
            if (waitTime < minWaitTime) {
                minWaitTime = waitTime;
                busId = b;
            }
        }

        //261
        System.out.println("part1 busId: " + busId + " waitTime: " + minWaitTime + " ans: " + (busId*minWaitTime));
    }

}
