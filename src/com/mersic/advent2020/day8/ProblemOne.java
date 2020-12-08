package com.mersic.advent2020.day8;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ProblemOne {

    enum OP_CODE {
        NOP,
        ACC,
        JMP
    }

    record Op(OP_CODE opCode, int arg) {};

    public static int runUntilLoop(Op[] ins) {
        int programCounter = 0;
        int[] instructionCounter = new int[ins.length];
        int accumulator = 0;
        while (true) {
            if (instructionCounter[programCounter] +1 > 1) {
                System.out.println("infinite loop detected, terminating.");
                return accumulator;
            }
            instructionCounter[programCounter]++;
            switch (ins[programCounter].opCode) {
                case ACC: { accumulator += ins[programCounter++].arg; break; }
                case NOP: { programCounter++; break; }
                case JMP: { programCounter += ins[programCounter].arg; break; }
                default: { throw new RuntimeException("unexpected opcode: " + ins[programCounter].opCode); }
            }
        }
    }

    public static void main(String args[]) throws Exception {
        String filename = "./resources/day8.1.input";
        List<String> lines = Files.readAllLines(Path.of(filename));

        Op[] ins = new Op[lines.size()];

        int counter = 0;
        for (String line : lines) {
            String[] s = line.split(" ");
            ins[counter++] = switch(s[0]) {
                case "nop" -> new Op(OP_CODE.NOP, Integer.parseInt(s[1]));
                case "acc" -> new Op(OP_CODE.ACC, Integer.parseInt(s[1]));
                case "jmp" -> new Op(OP_CODE.JMP, Integer.parseInt(s[1]));
                default -> { throw new RuntimeException("unrecognized opcode: " + s[0] + " with arg: " + s[1]); }
            };
        }

        int accValue = runUntilLoop(ins);

        System.out.println("accValue: " + accValue);

    }
}
