package com.mersic.advent2020.day1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Set;

public class ProblemOne {
    /* Given a list of numbers, find the two that sum to 2020 and return the result of multiplying them. */

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

        int first = nums.stream().filter(x -> nums.contains(2020-x)).findFirst().get();
        int second = 2020-first;

        System.out.println("result: " + (first*second));
    }
}
