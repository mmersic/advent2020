package com.mersic.advent2020.day6;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ProblemTwo {
    public static void main(String args[]) throws Exception {
        String filename = "./resources/day6.1.input";

        List<String> lines = Files.readAllLines(Path.of(filename));
        List<Set<Integer>> groups = new LinkedList<>();
        Set<Integer> group = new HashSet<>();

        boolean newGroup = true;
        for (String line : lines) {
            if ("".equals(line)) {
                if (!"".equals(group)) {
                    groups.add(group);
                    group = new HashSet<>();
                }
                newGroup = true;
            } else {
                if (newGroup) {
                    group = line.chars().boxed().collect(Collectors.toSet());
                    newGroup = false;
                } else {
                    Set<Integer> l = line.chars().boxed().collect(Collectors.toSet());
                    group = group.stream().filter(s -> l.contains(s)).collect(Collectors.toSet());
                }
            }
        }
        if (!group.isEmpty()) {
            groups.add(group);
        }

        int ans = groups.stream().map(s->s.size()).reduce((x, y)->x+y).get();
        System.out.println("ans: " + ans);

    }

}
