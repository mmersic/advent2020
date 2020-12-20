package com.mersic.advent2020.day16;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class ProblemOne {
    public static void main(String args[]) throws Exception {
        String filename = "./resources/day16.1.input";
        List<String> lines = Files.readAllLines(Path.of(filename));

        record Range(int low, int high) {
            public boolean in(int i) {
                return low <= i && i <= high;
            }
        };
        Map<String, Set<Range>> map = new HashMap<>();

        Iterator<String> I = lines.iterator();

        while(I.hasNext()) {
            String line = I.next();
            if (line.length() == 0) {
                break;
            }
            String[] s = line.split(":");
            String fieldName = s[0];
            s = s[1].split(" ");
            System.out.println("1: " + s[1]);
            System.out.println("3: " + s[3]);
            Set<Range> set = new HashSet<>();
            String[] r1 = s[1].split("-");
            set.add(new Range(Integer.parseInt(r1[0]), Integer.parseInt(r1[1])));
            r1 = s[3].split("-");
            set.add(new Range(Integer.parseInt(r1[0]), Integer.parseInt(r1[1])));
            map.put(fieldName, set);
            I.remove();
        }
        I.remove(); //space
        I.next();
        I.remove(); //your ticket:
        I.next();
        I.remove(); //ticket value...
        I.next();
        I.remove(); //spaces
        I.next();
        I.remove(); //nearby tickets:

        int failedCount = 0;
        int passedCount = 0;

        int invalidSum = 0;
        while (I.hasNext()) {
            String[] s = I.next().split(",");
            List<Integer> fieldVals = Arrays.stream(s).map(r -> Integer.parseInt(r)).collect(Collectors.toList());

            for (int i : fieldVals) {
                boolean valid = false;
                next: for (Map.Entry<String, Set<Range>> E : map.entrySet()) {
                    for (Range R : E.getValue()) {
                        if (R.in(i)) {
                            valid = true;
                            break next;
                        }
                    }
                }
                if (!valid) {
                    invalidSum += i;
                }
            }
        }

        //part1: 25059
        System.out.println("passed: " + passedCount + " failed: "+ failedCount + " invalidSum: " + invalidSum);

    }
}
