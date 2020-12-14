package com.mersic.advent2020.day13;

import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class ProblemTwo {


    public static void main(String args[]) throws Exception {
        String filename = "./resources/day13.1.input";
        List<String> lines = Files.readAllLines(Path.of(filename));

        Integer[] buses = Arrays.stream((lines.get(1).split(","))).map(s->{ if (s.equals("x")) return -1; else return Integer.parseInt(s); }).toArray(Integer[]::new);

        long lcm = 1;
        long time = 0;
        for (int i = 0; i < buses.length; i++) {
            if (buses[i] == -1) {
                continue;
            } else {
                for (;; time += lcm) {
                    if ((time + i) % buses[i] == 0) {
                        lcm *= buses[i];
                        break;
                    }
                }
            }
        }

        System.out.println("part2: " + time);

    }
}
