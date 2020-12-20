package com.mersic.advent2020.day18;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ProblemOne {

    record Symbol(type stype, long numValue){
        enum type { NUMBER, ADD, MULT, LEFT_PAREN, RIGHT_PAREN }
        public String toString() {
            if (stype == type.NUMBER) {
                return ""+numValue;
            } else {
                return stype.toString();
            }
        }
    }

    public static List<Symbol> parse(String line) {
        List<Symbol> s = new LinkedList<>();

        char[] chars = line.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == '*') {
                s.add(new Symbol(Symbol.type.MULT, 0));
            } else if (chars[i] == '+') {
                s.add(new Symbol(Symbol.type.ADD, 0));
            } else if (chars[i] == '(') {
                s.add(new Symbol(Symbol.type.LEFT_PAREN, 0));
            } else if (chars[i] == ')') {
                s.add(new Symbol(Symbol.type.RIGHT_PAREN, 0));
            } else if (chars[i] == ' ') {
                continue;
            } else {
                String num = "";
                while (chars.length > i && chars[i] >= '0' && chars[i] <= '9') {
                    num += chars[i++];
                }
                i--;
                if (num.length() > 0)
                    s.add(new Symbol(Symbol.type.NUMBER, Integer.parseInt(num)));
            }
        }
        return s;
    }

    public static List<Symbol> sub(List<Symbol> exp) {
        int lCount = 1;
        int rCount = 0;
        List<Symbol> subExp = new LinkedList<Symbol>();
        while (exp.size() > 0 && lCount != rCount) {
            Symbol next = exp.remove(0);
            if (next.stype == Symbol.type.RIGHT_PAREN && lCount-rCount == 1) {
                return subExp;
            } else if (next.stype == Symbol.type.LEFT_PAREN){
                lCount++;
            } else if (next.stype == Symbol.type.RIGHT_PAREN){
                rCount++;
            }
            subExp.add(next);
        }
        throw new RuntimeException("unbalanced parens");
    }

    public static Symbol eval(List<Symbol> exp) {
        List<Symbol> work = new LinkedList<>();
        while (exp.size() > 0) {
            Symbol s = exp.remove(0);
            if (s.stype == Symbol.type.LEFT_PAREN) {
                work.add(eval(sub(exp)));
            } else {
                work.add(s);
            }
        }

        return evalSimple(work.iterator());
    }

    private static Symbol evalSimple(Iterator<Symbol> I) {
        Symbol prev = null;
        while (I.hasNext()) {
            Symbol s = I.next();
            if (s.stype == Symbol.type.ADD) {
                prev = new Symbol(Symbol.type.NUMBER, prev.numValue + I.next().numValue);
            } else if (s.stype == Symbol.type.MULT) {
                prev = new Symbol(Symbol.type.NUMBER, prev.numValue * I.next().numValue);
            } else if (s.stype == Symbol.type.NUMBER) {
                prev = s;
            }
        }

        return prev;
    }


    public static void main(String args[]) throws Exception {
        String filename = "./resources/day18.1.input";
        List<String> lines = Files.readAllLines(Path.of(filename));

        long sum = lines.stream().map(l->eval(parse(l)).numValue).reduce(0l, Long::sum);

        //24650385570008
        System.out.println("part1: " + sum);
    }
}
