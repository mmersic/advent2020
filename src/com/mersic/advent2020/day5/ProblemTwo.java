package com.mersic.advent2020.day5;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProblemTwo {
    public static int toInt(String s, char high) {
        int v = 0;
        int len = s.length()-1;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(len-i) == high) {
                v += (Math.pow(2, i));
            }
        }
        return v;
    }


    public static int seatValue(String seat) {

        String row = seat.substring(0,7);
        String column = seat.substring(7);

        int r = toInt(row, 'B');
        int c = toInt(column, 'R');

        return (r*8+c);
    }

    public static void main(String args[]) throws Exception {
        String filename = "./resources/day5.1.input";

        List<String> seats = Files.readAllLines(Path.of(filename));

        List<Integer> seatIds = new ArrayList<>();
        for (String seat : seats) {
            int seatValue = seatValue(seat);
            seatIds.add(seatValue);
        }
        Collections.sort(seatIds);

        for (int i = 1; i < seatIds.size()-1; i++) {
            if (seatIds.get(i)-seatIds.get(i-1) == 2) {
                //correct answer is 569
                System.out.println("seatId: " + (seatIds.get(i-1)+1));
                break;
            }
        }

    }
}
