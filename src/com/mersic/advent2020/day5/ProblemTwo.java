package com.mersic.advent2020.day5;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProblemTwo {

    public static void main(String args[]) throws Exception {
        record rcpair(int r, int c) {};
        //correct answer is 569
        int[] prev = new int[] {-1};
        System.out.println("ans: " + (Files.readAllLines(Path.of("./resources/day5.1.input")).stream().map(seat -> new rcpair(Integer.parseInt(seat.substring(0,7).chars().boxed().map(s->s=='B'? "1" : "0").collect(Collectors.joining()),2), Integer.parseInt(seat.substring(7).chars().boxed().map(s->s=='R'? "1" : "0").collect(Collectors.joining()),2))).map(s->s.r*8+s.c).sorted().dropWhile(s -> (s-prev[0] != 2 && (prev[0]=s)==s)).findFirst().get()-1));

//Alternative soln for both parts:
//        List<Integer> list = Files.readAllLines(Path.of("./resources/day5.1.input")).stream().map(seat -> new rcpair(Integer.parseInt(seat.substring(0,7).chars().boxed().map(s->s=='B'? "1" : "0").collect(Collectors.joining()),2), Integer.parseInt(seat.substring(7).chars().boxed().map(s->s=='R'? "1" : "0").collect(Collectors.joining()),2))).map(s->s.r*8+s.c).sorted().collect(Collectors.toCollection(ArrayList::new));
//        System.out.println("part1: " + list.get(list.size()-1));
//        System.out.println("part2: " + (list.stream().dropWhile(s -> (s-prev[0] != 2 && (prev[0]=s)==s)).findFirst().get()-1));
    }
}
