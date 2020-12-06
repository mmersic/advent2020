package com.mersic.advent2020.day6;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class Streamify {

    public static <T> Set<T> setIntersection(List<Set<T>> sets) {
        return sets.get(0).stream().filter(s -> sets.stream().skip(1).filter(l->l.contains(s)).count()==sets.size()-1).collect(Collectors.toSet());
    }

    public static void main(String args[]) throws Exception {
        String filename = "./resources/day6.1.input";
        String input = Files.readAllLines(Path.of(filename)).stream().collect(Collectors.joining("\n"));
        List<String> groups = Arrays.stream(input.split("\n\n")).collect(Collectors.toList());

        long ans = groups.stream().map(s->s.chars().filter(r->r!='\n').distinct().count()).reduce((x,y)->x+y).get();
        System.out.println("part1: " + ans);

        ans = groups.stream()
                .map(s->setIntersection(Arrays.stream(s.split("\n")).map(r->r.chars().boxed().collect(Collectors.toSet())).collect(Collectors.toList())).size())
                .reduce((x,y)->x+y)
                .get();

        System.out.println("part2: " + ans);
    }
}
