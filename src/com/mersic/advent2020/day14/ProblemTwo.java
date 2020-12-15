package com.mersic.advent2020.day14;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class ProblemTwo {
    public static void main(String args[]) throws Exception {
        String filename = "./resources/day14.1.input";
        List<String> lines = Files.readAllLines(Path.of(filename));
        long startTime = System.currentTimeMillis();
        Map<Long, Long> mem = new HashMap<>();

        record MaskPair(long one_mask, long zero_mask) {};

        long one_mask = 0;
        Set<MaskPair> floatMasks = new HashSet<>();
        for (String line : lines) {
            if (line.startsWith("mask")) {
                char[] mask = line.substring(7).toCharArray();
                one_mask = 0;
                floatMasks = new HashSet<>();
                for (int i = 0; i < mask.length; i++) {
                    if ('1' == mask[35 - i]) {
                        one_mask |= 1l << i;
                    } else if ('0' == mask[35 - i]) {
                        continue;
                    } else if ('X' == mask[35 - i]) {
                        long fone_mask = 0l;
                        fone_mask = 1l<<i;
                        long fzero_mask = ~fone_mask;

                        if (floatMasks.size() == 0) {
                            floatMasks.add(new MaskPair(fone_mask, ~0l)); //bit on
                            floatMasks.add(new MaskPair(0l, fzero_mask)); //bit off
                        } else {
                            Set<MaskPair> tempSet = new HashSet<>();
                            for (MaskPair p : floatMasks) {
                                tempSet.add(new MaskPair(p.one_mask | fone_mask, p.zero_mask));
                                tempSet.add(new MaskPair(p.one_mask, p.zero_mask & fzero_mask));
                            }
                            floatMasks = tempSet;
                        }
                    }
                }
                continue;
            } else {
                String s[] = line.split(" = ");
                Long location = Long.parseLong(s[0].split("\\[")[1].split("\\]")[0]);
                Long value = Long.parseLong(s[1]);
                location |= one_mask;

                long l = 0;

                for (MaskPair m : floatMasks) {
                    location |= m.one_mask;
                    location &= m.zero_mask;
                    mem.put(location, value);
                }
            }

        }

        long sum = 0;
        for (Long l : mem.values()) {
            sum += l;
        }

        System.out.println("part1: " + sum + " in time: " + (System.currentTimeMillis()-startTime));

    }
}
