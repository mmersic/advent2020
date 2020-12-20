package com.mersic.advent2020.day19;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

//See AltSoln3.py for original source.
public class AltnSoln3 {

    private static class Rule {
        String terminal;
        List<List<Integer>> productions;
        public Rule (String terminal, List<List<Integer>> productions) {
            this.terminal = terminal;
            this.productions = productions;
        }
    }

    private static long test(String m, List<Integer> seq, Map<Integer, Rule> rules) {
        if ("".equals(m) || seq.size() == 0)
            return "".equals(m) && seq.size() == 0 ? 1 : 0;

        Rule r = rules.get(seq.get(0));
        if (r.terminal != null) {
            if (r.terminal.contains(""+m.charAt(0))) {
                return test(m.substring(1), seq.subList(1, seq.size()), rules);
            } else {
                return 0;
            }
        } else {
            for (List<Integer> L : r.productions) {
                List<Integer> temp = new LinkedList<>();
                temp.addAll(L);
                temp.addAll(seq.size() == 1 ? seq.subList(0,0) : seq.subList(1, seq.size()));
                if (test(m, temp, rules) == 1) {
                    return 1;
                }
            }
        }

        return 0;
    }


    private static Map<Integer, Rule> parseRules(List<String> ruleText) {
        Map<Integer, Rule> ruleMap = new HashMap<>();

        for (String rule : ruleText) {
            String[] r = rule.split(": ");
            if (r[1].contains("\"")) {
                ruleMap.put(Integer.parseInt(r[0]), new Rule(r[1], null));
            } else {
                ruleMap.put(Integer.parseInt(r[0]), new Rule(null, Arrays.stream(r[1].split("\\|")).map(s->Arrays.stream(s.split(" ")).filter(s1->s1.trim().length()>0).map(t->Integer.parseInt(t)).collect(Collectors.toList())).collect(Collectors.toList())));
            }
        }

        return ruleMap;
    }

    public static void main(String args[]) throws Exception {
        String filename = "./resources/day19.1.input";
        String[] lines = Files.readString(Path.of(filename)).split("\n\n");
        List<String> ruleText = Arrays.stream(lines[0].split("\n")).collect(Collectors.toList());
        String[] msgs  = lines[1].split("\n");

        System.out.println("part 1: " + Arrays.stream(msgs).map(m->test(m, List.of(0), parseRules(ruleText))).reduce(0l, Long::sum));

        ruleText.add("8: 42 | 42 8");
        ruleText.add("11: 42 31 | 42 11 31");
        System.out.println("part 2: " + Arrays.stream(msgs).map(m->test(m, List.of(0), parseRules(ruleText))).reduce(0l, Long::sum));
    }
}