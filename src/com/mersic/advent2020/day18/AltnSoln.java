package com.mersic.advent2020.day18;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

//Found in C# by hadopire.
//My implementation here in Java.
public class AltnSoln {

    private static Map<Character,Integer> part1Prec = new HashMap<>();
    private static Map<Character,Integer> part2Prec = new HashMap<>();

    static {
        part1Prec.put('*', 1); part1Prec.put('+',1); part1Prec.put('(',0);
        part2Prec.put('*', 1); part2Prec.put('+',2); part2Prec.put('(',0);
    }

    public static void consume(Stack<Long> operands, Stack<Character> operators) {
        switch (operators.pop()) {
            case '+': { operands.push(operands.pop()+operands.pop()); return; }
            case '*': { operands.push(operands.pop()*operands.pop()); return; }
        }
    }


    public static long evaluate(char[] exp, Map<Character,Integer> prec) {
        Stack<Long> operands = new Stack<>();
        Stack<Character> operators = new Stack<>();

        for (int i = 0; i < exp.length; i++) {
            while (exp[i] == ' ')
                i++;

            switch (exp[i]) {
                case '+', '*': {
                    if (operators.size() > 0 && prec.get(operators.peek()) >= prec.get(exp[i])) {
                        consume(operands, operators);
                    }
                }
                case '(': {
                    operators.push(exp[i]);
                    break;
                }
                case ')': {
                    while (operators.peek() != '(') {
                        consume(operands, operators);
                    }
                    operators.pop();
                    break;
                }
                default: {
                    long n = exp[i]-'0';
                    while (i+1 < exp.length && '0' <= exp[i+1] && exp[i+1] <= '9') {
                        n = (n * 10) + (exp[++i] - '0');
                    }
                    operands.push(n);
                    break;
                }
            }
        }

        while (operators.size() > 0) {
            consume(operands, operators);
        }

        return operands.pop();
    }

    public static void main(String args[]) throws Exception {
        String filename = "./resources/day18.1.input";
        List<String> lines = Files.readAllLines(Path.of(filename));

        long part1 = lines.stream().map(l->evaluate(l.toCharArray(), part1Prec)).reduce(0l, Long::sum);
        long part2 = lines.stream().map(l->evaluate(l.toCharArray(), part2Prec)).reduce(0l, Long::sum);

        System.out.println("part1: " + part1);
        System.out.println("part2: " + part2);
    }
}
