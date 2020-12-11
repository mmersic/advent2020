package com.mersic.advent2020.day9;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ProblemTwo {


    public static long findSumOfPair(long[] nums, long target) {
        for (int start = 0; start < nums.length; start++) {
            long sum = nums[start];
            long min = nums[start];
            long max = nums[start];
            for (int finish = start + 1; finish < nums.length; finish++) {
               sum += nums[finish];
               if (nums[finish] < min) {
                   min = nums[finish];
               }
               if (nums[finish] > max) {
                   max = nums[finish];
               }
               if (sum == target) {
                   return min + max;
               } else if (sum > target) {
                   break;
               }
            }
        }
        throw new RuntimeException(("Not found"));
    }

    public static boolean sum(long[] nums, int target) {
        for (int i = target-25; i < target; i++) {
            for (int j = i+1; j < target; j++) {
                if (nums[i] + nums[j] == nums[target]) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void main(String args[]) throws Exception {
        String filename = "./resources/day9.1.input";
        List<String> lines = Files.readAllLines(Path.of(filename));

        long[] nums = new long[lines.size()];
        int i = 0;
        for (String line : lines) {
            nums[i++] = Long.parseLong(line);
        }

        int trial = 24;
        while (++trial < nums.length && sum(nums, trial));

        if (trial == nums.length) {
            System.out.println("not found :(");
        } else {
            System.out.println("part1: " + nums[trial]);
        }

        long sumOfPair = findSumOfPair(nums, nums[trial]);

        System.out.println("part2: " + sumOfPair);
    }
}
