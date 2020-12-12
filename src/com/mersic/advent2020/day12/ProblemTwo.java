package com.mersic.advent2020.day12;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class ProblemTwo {

    record cmd(char c, int v){};
    record pos(int x, int y){};

    public static pos move(pos p, cmd c) {
        int x = p.x;
        int y = p.y;
        switch (c.c) {
            case 'N': {
                y += c.v;
                break;
            }
            case 'S': {
                y -= c.v;
                break;
            }
            case 'E': {
                x += c.v;
                break;
            }
            case 'W': {
                x -= c.v;
                break;
            }
        }
        return new pos(x, y);
    }

    public static pos rotate(cmd c, pos w) {
        int v = c.v % 360; //no circles.
        if (c.c == 'L') {
            v = 360 - v; //only rotate right ;)
        }
        while (v > 0) {
            v-=90;
            w = new pos(w.y, -w.x);
        }
        return w;
    }

    public static void main(String args[]) throws Exception {
        String filename = "./resources/day12.1.input";
        List<String> lines = Files.readAllLines(Path.of(filename));

        List<cmd> cmds = lines.stream().map(s -> new cmd(s.charAt(0), Integer.parseInt(s.substring(1)))).collect(Collectors.toList());

        pos w = new pos(10, 1);
        pos p = new pos(0, 0);
        for (cmd c : cmds) {
            switch (c.c) {
                case 'L', 'R': {
                    w = rotate(c, w);
                    break;
                }
                case 'F': {
                    p = new pos(p.x + (w.x*c.v), p.y + (w.y*c.v));
                    break;
                }
                case 'N', 'S', 'E', 'W': {
                    w = move(w, c);
                    break;
                }
                default: {
                    System.err.println("unrecognized command: " + c);
                    System.exit(-1);
                }
            }
        }

        System.out.println("final pos: " + p + " manhatten distance from start: " + (Math.abs(p.x) + Math.abs(p.y)));
    }

}
