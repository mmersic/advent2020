package com.mersic.advent2020.day7;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class ProblemOne {

    record Edge(String color, int weight) {};

    public static boolean dfs(Map<String,List<Edge>> G, String from, String to) {
       List<Edge> edges = G.get(from);
       for (Edge e : edges) {
           if (e.color.equals(to)) {
               return true;
           } else if (dfs(G, e.color, to)) {
               return true;
           }
        }
        return false;
    }

    public static void main(String args[]) throws Exception {
        String filename = "./resources/day7.1.input";
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

        int count = 0;
        for (String color : G.keySet()) {
            if (dfs(G, color, "shiny_gold")) {
                count++;
            }
        }

        //ans; ex: 4, full: 233
        System.out.println("ans: " + count);
    }

}
