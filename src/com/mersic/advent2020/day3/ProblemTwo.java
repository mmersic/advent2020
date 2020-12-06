package com.mersic.advent2020.day3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class ProblemTwo {

    public static int treeCount(List<String> land, int dx, int dy) {
        int x = 0;
        int y = 0;
        int rlength = land.get(0).length();
        int trees = 0;
        while (y+dy < land.size()) {
            y += dy;
            x += dx;
            int fx = x % rlength;
            if (land.get(y).charAt(fx) == '#') {
                trees++;
            }
        }

        return trees;
    }

    public static void main(String args[]) throws Exception {
        String filename = "./resources/day3.1.input";

        FileReader fr = new FileReader(filename);
        BufferedReader br = new BufferedReader(fr);

        List<String> land = new ArrayList<>();


        String nextLine = null;
        while ((nextLine = br.readLine()) != null) {
            land.add(nextLine);
        }

        int total = 1;
        total *= treeCount(land, 1,1);
        total *= treeCount(land, 3, 1);
        total *= treeCount(land, 5,1);
        total *= treeCount(land, 7,1);
        total *= treeCount(land, 1,2);

        System.out.println("result: " + total);
    }
}
