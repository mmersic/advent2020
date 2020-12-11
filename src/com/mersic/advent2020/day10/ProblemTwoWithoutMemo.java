package com.mersic.advent2020.day10;

import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProblemTwoWithoutMemo {

    public static int min = Integer.MAX_VALUE;
    private static BigInteger countValidAdapterCombinations(List<Integer> adapters, int prev) {
        if (adapters.size() == 1) {
            if (adapters.get(0) - prev <= 3) {
                return BigInteger.ONE;
            }
            else {
                return BigInteger.ZERO;
            }
        } else if (adapters.get(0) - prev > 3) {
            return BigInteger.ZERO; //won't work without the previous adapter
        } else {
            //sum of combinations with and without this adapter.
                BigInteger with = countValidAdapterCombinations(adapters.subList(1, adapters.size()), adapters.get(0));
                BigInteger withOut = countValidAdapterCombinations(adapters.subList(1, adapters.size()), prev);
                if (adapters.get(0) < min) {
                    System.out.println("solved: " + adapters.get(0));
                    min = adapters.get(0);
                }
            return with.add(withOut);
        }
    }


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
        //part 2 - 169255295254528
        long start = System.currentTimeMillis();
        BigInteger total = countValidAdapterCombinations(adapters, 0);
        long finish = System.currentTimeMillis();
        System.out.println("part2: " + total + " time: " + (finish-start));
    }

}
