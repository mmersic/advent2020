package com.mersic.advent2020.day1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class ProblemTwo {

    /* Given a list of numbers, find three that sum to 2020 and return the result of multiplying them. */

    /* If the given list contains two numbers that sum to toSum, return one such number. */
    public static Integer sum(Set<Integer> nums, int toSum) {
        Optional<Integer> first = nums.stream().filter(x -> nums.contains(toSum-x)).findFirst();
        if (first.isPresent()) {
            return first.get();
        } else {
            return null;
        }
    }

    public static void main(String args[]) throws Exception {
        String filename = "./resources/day1.1.input";

        FileReader fr = new FileReader(filename);
        BufferedReader br = new BufferedReader(fr);

        Set<Integer> nums = new HashSet<>();


        String nextLine = null;
        int sum = 0;
        while ((nextLine = br.readLine()) != null) {
            int input = Integer.parseInt(nextLine);
            nums.add(input);
        }

        int[] second = new int[1];
        int first = nums.stream().filter(x -> {
            Integer v = sum(nums, 2020-x);
            if (v != null) {
                second[0] = v;
                return true;
            } else {
                return false;
            }
        }).findFirst().get();

        int third = (2020-(first+second[0]));

        System.out.println("first: " + first);
        System.out.println("second: " + second[0]);
        System.out.println("third: " + third);
        System.out.println("result: " + (first*second[0]*third));
    }
}
