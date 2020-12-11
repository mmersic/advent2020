package com.mersic.advent2020.day10;

import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProblemTwoShorter {
    private static Map<String, BigInteger> memo = new HashMap<>();
    private static BigInteger countValidAdapterCombinations(List<Integer> adapters, int prev) {
        if (memo.containsKey(adapters.get(0)+"_prev_"+prev)) { return memo.get(adapters.get(0)+"_prev_"+prev); }
        if (adapters.size() == 1) {
            return adapters.get(0) - prev <= 3 ? BigInteger.ONE : BigInteger.ZERO;
        } else if (adapters.get(0) - prev > 3) {
            return BigInteger.ZERO; //dead end, no combinations will work from here.
        } else {
            //sum of combinations with and without this adapter.
            BigInteger result = countValidAdapterCombinations(adapters.subList(1, adapters.size()), adapters.get(0)).add(countValidAdapterCombinations(adapters.subList(1, adapters.size()), prev));
            memo.put(adapters.get(0)+"_prev_"+prev,result);
            return result;
        }
    }

    public static void main(String args[]) throws Exception {
        //part 2 - ex 1 - 8
        //part 2 - ex 2 - 19208
        //part 2 - 169255295254528
        System.out.println("part2: " + countValidAdapterCombinations(Files.readAllLines(Path.of("./resources/day10.1.input")).stream().map(s -> Integer.parseInt(s)).sorted().collect(Collectors.toList()), 0));
    }
}
