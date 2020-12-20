package com.mersic.advent2020.day19;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class ProblemOne {

    static class Rule {
        enum type { TERMINAL, SEQ, COMBO }

        int  id;
        type rType;
        char terminal;
        List<Integer> seq;
        List<List<Integer>> combo;
        String regEx = null;

        public Rule(int id, String s) {
            this.id = id;
            if (s.length() == 1) {
                this.rType = type.TERMINAL;
                this.terminal = s.charAt(0);
                this.regEx = ""+this.terminal;
            } else if (s.contains("|")) {
                this.rType = type.COMBO;
                String[] rules = s.split("\\|");
                this.combo = new ArrayList<>();
                for (String r : rules) {
                    this.combo.add(toIntArray(r));
                }
            } else {
                this.rType = type.SEQ;
                this.seq = toIntArray(s);
            }
        }

        public static List<Integer> toIntArray(String s) {
            String[] r = s.split(" ");
            List<Integer> result = new ArrayList<>();
            for (String t : r) {
                 try {  result.add(Integer.parseInt(t)); } catch (Exception e) { }
            }
            return result;
        }

    }

    static public String toRegex(Map<Integer,Rule> rMap, int index) {

        Rule root = rMap.get(index);

        if (root.rType == Rule.type.TERMINAL) {
            return root.regEx;
        } else if (root.rType == Rule.type.SEQ){
            StringBuilder sb = new StringBuilder();
            for (int id : root.seq) {
                sb.append("(");
                sb.append(toRegex(rMap, id));
                sb.append(")");
            }
            return sb.toString();
        } else if (root.rType == Rule.type.COMBO) {
            StringBuilder sb = new StringBuilder();
            for (List<Integer> operands : root.combo) {
                for (int id : operands) {
                    sb.append("(");
                    sb.append(toRegex(rMap, id));
                    sb.append(")");
                }
                sb.append("|");
            }
            sb.deleteCharAt(sb.length()-1);
            return sb.toString();
        }

        throw new RuntimeException("Invalid type.");
    }

    public static Map<Integer, Rule> toRules(List<String> rules) {
        List<Rule> R = new ArrayList<>();

        for (String r : rules) {
            String[] s = r.split(":");
            int id = Integer.parseInt(s[0]);
            R.add(new Rule(id, s[1].substring(1).replace("\"","")));
        }

        Map<Integer, Rule> rMap = new HashMap<>();
        for (Rule r : R) {
            rMap.put(r.id, r);
        }

        return rMap;
    }



    public static void main(String args[]) throws Exception {
        String filename = "./resources/day19.ex.2.input";
        List<String> lines = Files.readAllLines(Path.of(filename));

        List<String> rules = lines.stream().takeWhile(l->l.contains(":")).collect(Collectors.toList());
        List<String> msgs  = lines.stream().dropWhile(l->l.contains(":")||l.length()==0).collect(Collectors.toList());
        Map<Integer, Rule>  R = toRules(rules);
        System.out.println("rules: " + rules);
        System.out.println("msgs: " + msgs);

        String regex = toRegex(R, 0);

        System.out.println("regex: " + regex);

        int counter = 0;
        for (String msg : msgs) {
            counter += msg.matches(regex) ? 1 : 0;
        }

        System.out.println("part1: " + counter);
    }

}
