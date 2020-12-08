package com.mersic.advent2020.day7;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class ProblemTwo {
    record Edge(String color, int weight) {};

    public static int countBags(Map<String,List<Edge>> G, String from) {
        int count = 1;
        List<Edge> edges = G.get(from);
        for (Edge e : edges) {
            count += e.weight*countBags(G, e.color);
        }
        return count;
    }

    public static void main(String args[]) throws Exception {
        String filename = "./resources/day7.ex.input";
        List<String> lines = Files.readAllLines(Path.of(filename));

        Map<String, List<Edge>> G = new HashMap<>();

        for (String line : lines) {
            String s[] = line.split(" ");
            String color = s[0]+"_"+s[1];
            List<Edge> edges = new LinkedList<>();
            for (int i = 4; i < s.length && !"bags.".equals(s[i]) && !"no".equals(s[i]);) {
                int weight = Integer.parseInt(s[i]);
                String destColor = s[i+1] + "_" + s[i+2];
                Edge E = new Edge(destColor, weight);
                edges.add(E);
                i += 4;
            }
            G.put(color, edges);
        }
        long start = System.currentTimeMillis();
        int part2 = countBags(G, "shiny_gold")-1;
        //ans; ex: 32, full: 421550
        System.out.println("part2: " + part2 + " time: " + (System.currentTimeMillis()-start));
    }
}
