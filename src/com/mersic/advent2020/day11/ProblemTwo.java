package com.mersic.advent2020.day11;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ProblemTwo {
    // '.' == floor
    // '#' == occupied
    // 'L' == empty
    public static void iterate(int[][] current, int[][] next, int X, int Y) {
        for (int i = 0; i < Y; i++) {
            for (int j = 0; j < X; j++) {
                if (current[i][j] == '.') {
                    next[i][j] = current[i][j];
                } else if (current[i][j] == 'L') {
                    if (countNeighbors(current, i, j) == 0) {
                        next[i][j] = '#';
                    } else {
                        next[i][j] = 'L';
                    }
                } else if (current[i][j] == '#'){
                    if (countNeighbors(current, i, j) >= 5) {
                        next[i][j] = 'L';
                    } else {
                        next[i][j] = '#';
                    }
                }
            }
        }
//        print(next, X, Y);
    }

    private static void print(int[][] next, int X, int Y) {
        for (int i = 0; i < Y; i++) {
            for (int j = 0; j < X; j++) {
                char c = (char) next[i][j];
                System.out.print(c);
            }
            System.out.println();
        }
        System.out.println();
        System.out.println();
    }

    private static int countNeighbors(int[][] current, int y, int x) {
        int n = 0;
        try { int i = 1; while (true) { int s = current[y-i][x-i++]; if (s == '.') continue; if (s == '#') { n++; } break; }} catch(IndexOutOfBoundsException e) {}
        try { int i = 1; while (true) { int s = current[y-i++][x]; if (s == '.') continue; if (s == '#') { n++; } break; } } catch(IndexOutOfBoundsException e) {}
        try { int i = 1; while (true) { int s = current[y-i][x+i++];  if (s == '.') continue; if (s == '#') { n++; } break; }} catch(IndexOutOfBoundsException e) {}

        try { int i = 1; while (true) { int s = current[y][x-i++];  if (s == '.') continue; if (s == '#') { n++; } break; }} catch(IndexOutOfBoundsException e) {}
        try { int i = 1; while (true) { int s = current[y][x+i++];  if (s == '.') continue; if (s == '#') { n++; } break; }} catch(IndexOutOfBoundsException e) {}

        try { int i = 1; while (true) { int s = current[y+i][x-i++];  if (s == '.') continue; if (s == '#') { n++; } break; }} catch(IndexOutOfBoundsException e) {}
        try { int i = 1; while (true) { int s = current[y+i++][x];  if (s == '.') continue; if (s == '#') { n++; } break; }} catch(IndexOutOfBoundsException e) {}
        try { int i = 1; while (true) { int s = current[y+i][x+i++];  if (s == '.') continue; if (s == '#') { n++; } break; }} catch(IndexOutOfBoundsException e) {}

        return n;
    }

    public static boolean compare(int[][] G, int[][] F, int X, int Y) {
        for (int i = 0; i < Y; i++) {
            for (int j = 0; j < X; j++) {
                if (G[i][j] != F[i][j]) {
//                    System.out.println("not same at: " + i + ", " + j + " " + G[i][j] + " " + F[i][j]);
                    return false;
                }
            }
        }
        return true;
    }

    public static int occupiedSeats(int[][] G, int X, int Y) {
        int counter = 0;
        for (int i = 0; i < Y; i++) {
            for (int j = 0; j < X; j++) {
                if (G[i][j] == '#') counter++;
            }
        }
        return counter;
    }


    public static void main(String args[]) throws Exception {
        String filename = "./resources/day11.1.input";
        List<String> lines = Files.readAllLines(Path.of(filename));

        int X = lines.get(0).length();
        int Y = lines.size();

        int[][] G = new int[Y][X];
        int[][] F = new int[Y][X];

        int i = 0;
        for (String line : lines) {
            G[i] = line.chars().toArray();
            F[i++] = line.chars().toArray();
        }


//        print(G, X, Y);
//        print(F, X, Y);

        long startTime = System.currentTimeMillis();
        i = 0;
        do {
            iterate(i%2==0?G:F, i%2==0?F:G, X, Y);
            //if (i%2 == 0) { print(F, X, Y); } else { print(G, X, Y); }
            i++;
        } while (!compare(G,F, X, Y));
        long duration = System.currentTimeMillis() - startTime;
        System.out.println("part2: " + occupiedSeats(i%2==0?G:F, X, Y) + " in " + i + " rounds and " + duration + "ms.");

    }
}
