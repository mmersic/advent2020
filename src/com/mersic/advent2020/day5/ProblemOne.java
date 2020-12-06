package com.mersic.advent2020.day5;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

public class ProblemOne {
    public static void main(String args[]) throws Exception {
        record rcpair(int r, int c) {};
        //correct answer is 955
        System.out.println("max: " + Files.readAllLines(Path.of("./resources/day5.1.input")).stream().map(seat -> new rcpair(Integer.parseInt(seat.substring(0,7).chars().boxed().map(s->s=='B'? "1" : "0").collect(Collectors.joining()),2), Integer.parseInt(seat.substring(7).chars().boxed().map(s->s=='R'? "1" : "0").collect(Collectors.joining()),2))).map(s->s.r*8+s.c).max(Integer::compare).get());
    }
}
