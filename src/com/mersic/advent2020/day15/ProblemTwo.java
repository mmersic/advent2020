package com.mersic.advent2020.day15;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProblemTwo {
    public static void main(String args[]) throws Exception {
        String filename = "./resources/day15.1.input";
        List<String> lines = Files.readAllLines(Path.of(filename));

        List<Integer> nums = Arrays.stream(lines.get(0).split(",")).map(s->Integer.parseInt(s)).collect(Collectors.toList());

        record SpokenHist(int last, int timeBeforeLast) {};
        Map<Integer, SpokenHist> spoken = new HashMap<>();

        int turn = 1;
        int lastNumberSpoken = -1;
        SpokenHist defaultSpokenHist = new SpokenHist(-1,-1);
        while (!nums.isEmpty()) {
            lastNumberSpoken = nums.remove(0);
            spoken.put(lastNumberSpoken, new SpokenHist(turn++, -1));
        }

        for (; turn <= 30000000; turn++) {
            SpokenHist last = spoken.get(lastNumberSpoken);
            if (last.timeBeforeLast == -1) {
                lastNumberSpoken = 0;
                SpokenHist zero = spoken.get(lastNumberSpoken);
                spoken.put(lastNumberSpoken, new SpokenHist(turn, zero.last));
            } else {
                lastNumberSpoken = last.last - last.timeBeforeLast;
                spoken.put(lastNumberSpoken, new SpokenHist(turn, spoken.getOrDefault(lastNumberSpoken, defaultSpokenHist).last));
            }
        }

        //16671510
        System.out.println("2020th: " + lastNumberSpoken);

    }
}
