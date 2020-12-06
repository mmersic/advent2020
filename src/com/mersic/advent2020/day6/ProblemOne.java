package com.mersic.advent2020.day6;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

public class ProblemOne {
    public static void main(String args[]) throws Exception {
        String filename = "./resources/day6.1.input";

        List<String> lines = Files.readAllLines(Path.of(filename));
        List<String> groups = new LinkedList<>();
        String group = "";
        for (String line : lines) {
            if ("".equals(line)) {
                if (!"".equals(group)) {
                    groups.add(group);
                    group = "";
                }
            } else {
                group += line;
            }
        }
        groups.add(group);

        long ans = groups.stream().map(s->s.chars().distinct().count()).reduce((x,y)->x+y).get();

        System.out.println("ans: " + ans);
    }
}
