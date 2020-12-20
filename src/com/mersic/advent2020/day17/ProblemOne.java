package com.mersic.advent2020.day17;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class ProblemOne {
    final static int CYCLES = 6;
    final static int BUFFER = CYCLES+12;

    final static char INACTIVE = '.';
    final static char ACTIVE   = '#';

    final static boolean DEBUG = false;

    record Cord(int x, int y, int z) {}


    public static void iterate(char[][][] G, char[][][] F, Cord c) {
        for (int i = 0; i < c.z; i++) {
            for (int j = 0; j < c.y; j++) {
                for (int k = 0; k < c.z; k++) {
                    int n = countNeighbors(G, new Cord(k, j, i));
                    switch (G[i][j][k]) {
                        case ACTIVE: {
                            if (n == 2 || n == 3) {
                                F[i][j][k] = ACTIVE;
                            } else {
                                F[i][j][k] = INACTIVE;
                            }
                            break;
                        }
                        case INACTIVE: {
                            if (n == 3) {
                                F[i][j][k] = ACTIVE;
                            } else {
                                F[i][j][k] = INACTIVE;
                            }
                            break;
                        }
                        default: {
                            throw new RuntimeException("Invalid state.");
                        }
                    }
                }
            }
        }
    }

    public static int countNeighbors(char[][][] G, Cord c) {
        int n = 0;
        try { n += countNeighbors(G[c.z-1], c.y, c.x, true); } catch(IndexOutOfBoundsException e) {}
        n += countNeighbors(G[c.z], c.y, c.x, false);
        try { n += countNeighbors(G[c.z+1], c.y, c.x, true); } catch(IndexOutOfBoundsException e) {}
        if (n != 0) {
            if (DEBUG) System.out.println("c: " + c + " has: " + n);
        }
        return n;
    }

    private static int countNeighbors(char[][] current, int y, int x, boolean includeXY) {
        int n = 0;
        try { n += current[y-1][x-1] == '#' ? 1 : 0; } catch(IndexOutOfBoundsException e) {}
        try { n += current[y-1][x] == '#' ? 1 : 0; } catch(IndexOutOfBoundsException e) {}
        try { n += current[y-1][x+1] == '#' ? 1 : 0; } catch(IndexOutOfBoundsException e) {}
        try { n += current[y][x-1] == '#' ? 1 : 0; } catch(IndexOutOfBoundsException e) {}
        try { n += current[y][x+1] == '#' ? 1 : 0; } catch(IndexOutOfBoundsException e) {}
        try { n += current[y+1][x-1] == '#' ? 1 : 0; } catch(IndexOutOfBoundsException e) {}
        try { n += current[y+1][x] == '#' ? 1 : 0; } catch(IndexOutOfBoundsException e) {}
        try { n += current[y+1][x+1] == '#' ? 1 : 0; } catch(IndexOutOfBoundsException e) {}
        if (includeXY) {
            try { n += current[y][x] == '#' ? 1 : 0; } catch(IndexOutOfBoundsException e) {}
        }
        return n;
    }

    public static int countActive(char[][][] G, Cord c) {
        int count = 0;
        for (int i = 0; i < c.z; i++) {
            for (int j = 0; j < c.y; j++) {
                for (int k = 0; k < c.z; k++) {
                    if (G[i][j][k] == ACTIVE) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    public static void main(String args[]) throws Exception {


        String filename = "./resources/day17.1.input";
        List<String> lines = Files.readAllLines(Path.of(filename));

        Cord c = new Cord(lines.get(0).length()+BUFFER*2, lines.size()+BUFFER*2, 1+BUFFER*2);
        char[][][] G = new char[c.z][c.y][c.x];
        char[][][] F = new char[c.z][c.y][c.x];

        for (int i = 0; i < c.z; i++) {
            for (int j = 0; j < c.y; j++) {
                Arrays.fill(G[i][j], INACTIVE);
            }
        }

        int i = 0;
        for (String line : lines) {
            char[] asChar = line.toCharArray();
            for (int j = 0; j < asChar.length; j++) {
                G[BUFFER][BUFFER+i][BUFFER+j] = asChar[j];
            }
            i++;
        }
        System.out.println("initial state...");
        print(G, c, BUFFER);

        for (i = 0; i < CYCLES; i++) {
            iterate(i%2==0 ? G : F, i%2==0 ? F : G, c);
            if (DEBUG) print(i%2 == 0 ? F : G, c, CYCLES);
        }

        //print(G, c, CYCLES);
        //202
        System.out.println("part1: " + countActive(i%2==0 ? G : F, c));
    }

    private static void print(char[][][] G, Cord c, int z) {
        for (int i = 0; i < c.y; i++) {
            if (i < 10) {
                System.out.print(i + "  ");
            } else {
                System.out.print(i + " ");
            }
            for (int j = 0; j < c.x; j++) {
                System.out.print(G[z][i][j]);
            }
            System.out.println();
        }
        System.out.println();
        System.out.println();
    }

}
