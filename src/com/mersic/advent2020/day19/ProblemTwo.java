package com.mersic.advent2020.day19;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ProblemTwo {

    static class Rule {
        enum type { TERMINAL, SEQ, COMBO, LOOP }

        int  id;
        Rule.type rType;
        char terminal;
        List<Integer> seq;
        List<List<Integer>> combo;
        String regEx = null;

        public Rule(int id, String s) {
            this.id = id;

            if (s.length() == 1) {
                this.rType = Rule.type.TERMINAL;
                this.terminal = s.charAt(0);
                this.regEx = ""+this.terminal;
            } else if (s.contains("|")) {
                this.rType = Rule.type.COMBO;
                String[] rules = s.split("\\|");
                this.combo = new ArrayList<>();
                for (String r : rules) {
                    this.combo.add(toIntArray(r));
                }
            } else {
                this.rType = Rule.type.SEQ;
                this.seq = toIntArray(s);
            }

            if (id == 8 || id == 11) {
                this.rType = type.LOOP;
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


    static public String toRegex(Map<Integer, Rule> rMap, int index) {

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
        } else if (root.rType == Rule.type.LOOP) {
            if (index == 8) {
                return "((" + toRegex(rMap, 42) + ")+)";
            } else if (index == 11) {
                StringBuilder sb = new StringBuilder();
                sb.append("(");
                for (int n = 1; n < 40; n++) {
                    for (int i = 0; i < n; i++) {
                        sb.append(toRegex(rMap, 42));
                    }
                    sb.append("|");
                    for (int i = 0; i < n; i++) {
                        sb.append(toRegex(rMap, 42));
                    }
                }
                sb.append(")");
                return sb.toString();
            } else {
                System.out.println("what");
            }
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
        String filename = "./resources/day19.2.input";
        List<String> lines = Files.readAllLines(Path.of(filename));

        List<String> msgs  = lines.stream().dropWhile(l->l.contains(":")||l.length()==0).collect(Collectors.toList());

        List<String> rules = Files.readAllLines(Path.of(filename)).stream().takeWhile(l->l.contains(":")).collect(Collectors.toList());

        Map<Integer, Rule>  R = toRules(rules);

        String r42 = "(?<r42>("+toRegex(R, 42) + ")+)";
        String r31 = "(?<r31>("+toRegex(R, 31) + ")+)";

        String l42 = "("+toRegex(R, 42) + ")";
        String l31 = "("+toRegex(R, 31) + ")";;

        String regTry = r42 + r31;

        Pattern P = Pattern.compile(regTry);

        int counter = 0;
        int maxI = 0;
        for (String msg : msgs) {
            Matcher REGTRY = P.matcher(msg);
            if (REGTRY.matches()) {
                for (int i = 0; i < 10; i++) {
                    String temp = "(?<r42>(" + l42 + "{" + (2+i) + ",}))"+ "(?<r31>(" + l31 + "{1," + (1+i) + "}))";
                    Matcher M = Pattern.compile(temp).matcher(msg);
                    if (M.matches()) {
                        if (i > maxI) {
                            maxI = i;
                        }
                        counter++;
                        break;
                    }
                }
            }
        }

        //409
        System.out.println("part1: " + counter);
        System.out.println("maxi: " + maxI);
    }

}
