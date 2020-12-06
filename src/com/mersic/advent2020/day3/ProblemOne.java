package com.mersic.advent2020.day3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;

public class ProblemOne {
    public static void main(String args[]) throws Exception {
        String filename = "./resources/day3.1.input";

        FileReader fr = new FileReader(filename);
        BufferedReader br = new BufferedReader(fr);

        List<String> land = new LinkedList<>();


        String nextLine = null;
        while ((nextLine = br.readLine()) != null) {
            land.add(nextLine);
        }

        int dx = 3;
        int dy = 1;
        int x = 0;
        int y = 0;
        int rlength = land.get(0).length();
        int trees = 0;
        while (y < land.size()-1) {
            y += dy;
            x += dx;
            int fx = x % rlength;
            if (land.get(y).charAt(fx) == '#') {
                trees++;
            }
        }

        System.out.println("result: " + trees);
    }
}
