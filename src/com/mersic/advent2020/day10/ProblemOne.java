package com.mersic.advent2020.day10;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class ProblemOne {
    public static void main(String args[]) throws Exception {
        String filename = "./resources/day10.1.input";
        List<String> lines = Files.readAllLines(Path.of(filename));
        List<Integer> adapters = lines.stream().map(s -> Integer.parseInt(s)).sorted().peek(s->System.out.println(s)).collect(Collectors.toList());

        int[] prev = new int[] { -10 };
        long oneDiffCount = adapters.stream().filter(a -> {int p = prev[0]; prev[0] = a; return a - p == 1;}).count() + 1; //+1 for the charing port
        prev[0] = -10;
        long threeDiffCount = adapters.stream().filter(a -> {int p = prev[0]; prev[0] = a; return a - p == 3;}).count() + 1; //+1 for the laptop

        //ex1 - 7 * 5 = 35
        //ex2 - 22 * 10 = 220
        //part 1 - 71 * 31 = 2201

        System.out.println("one: " + oneDiffCount + " three: " + threeDiffCount);
        System.out.println("part1: " + (oneDiffCount * threeDiffCount));

    }
}