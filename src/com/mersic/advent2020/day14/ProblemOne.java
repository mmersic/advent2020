package com.mersic.advent2020.day14;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProblemOne {
    public static void main(String args[]) throws Exception {
        String filename = "./resources/day14.1.input";
        List<String> lines = Files.readAllLines(Path.of(filename));

        Map<Long, Long> mem = new HashMap<>();

        long one_mask = 0;
        long zero_mask = 0;

        for (String line : lines) {

            if (line.startsWith("mask")) {
                char[] mask = line.substring(7).toCharArray();
                one_mask = 0;
                zero_mask = 0;
                for (int i = 0; i < mask.length; i++) {
                    if ('1' == mask[35 - i]) {
                        one_mask |= 1l << i;
                    } else if ('0' == mask[35 - i]) {
                        zero_mask |= 1l << i;
                    }
                }
                zero_mask = ~zero_mask;
                continue;
            } else {
                String s[] = line.split(" = ");
                long location = Long.parseLong(s[0].split("\\[")[1].split("\\]")[0]);
                long value = Long.parseLong(s[1]);
                value |= one_mask;
                value &= zero_mask;
                mem.put(location, value);
            }

        }

        long sum = 0;
        for (Long l : mem.values()) {
            sum += l;
        }

        System.out.println("part1: " + sum);

    }
}
