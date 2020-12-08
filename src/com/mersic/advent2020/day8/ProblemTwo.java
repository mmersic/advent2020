package com.mersic.advent2020.day8;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ProblemTwo {

    enum OP_CODE {
        NOP,
        ACC,
        JMP
    }

    enum STATUS {
        INFINITE_LOOP,
        ERROR,
        NORMAL
    }

    record Op(OP_CODE opCode, int arg) {}
    record ProgramResult(STATUS status, int value) {}

    public static ProgramResult run(Op[] ins) {
        int programCounter = 0;
        int[] instructionCounter = new int[ins.length];
        int accumulator = 0;
        while (programCounter != ins.length) {
            if (instructionCounter[programCounter] +1 > 1) {
                return new ProgramResult(STATUS.INFINITE_LOOP, accumulator);
            }
            instructionCounter[programCounter]++;
            switch (ins[programCounter].opCode) {
                case ACC: { accumulator += ins[programCounter++].arg; break; }
                case NOP: { programCounter++; break;}
                case JMP: { programCounter += ins[programCounter].arg; break;}
                default: { System.out.println("unexpected opcode: " + ins[programCounter].opCode); return new ProgramResult(STATUS.ERROR, Integer.MIN_VALUE); }
            }
        }

        return new ProgramResult(STATUS.NORMAL, accumulator);
    }

    public static void main(String[] args) throws Exception {
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

        for (int i = 0; i < ins.length; i++) {
            Op old = ins[i];
            ProgramResult pr = switch (ins[i].opCode) {
                case NOP -> { ins[i] = new Op(OP_CODE.JMP, ins[i].arg); yield run(ins); }
                case JMP -> { ins[i] = new Op(OP_CODE.NOP, ins[i].arg); yield run(ins); }
                default -> new ProgramResult(STATUS.ERROR, -1);
            };

            if (pr.status == STATUS.NORMAL) {
                System.out.println("accValue: " + pr.value);
                return;
            } else {
                ins[i] = old;
            }
        }


    }
}
