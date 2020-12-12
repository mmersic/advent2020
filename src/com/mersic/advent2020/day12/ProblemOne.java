package com.mersic.advent2020.day12;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProblemOne {

    record cmd(char c, int v){};
    record pos(int x, int y){};

    public static Map<Character,Character> map = new HashMap<>();

    static {
        map.put('E', 'S');
        map.put('S', 'W');
        map.put('W', 'N');
        map.put('N', 'E');
    }

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
            }
        }
        return new pos(x, y);
    }

    public static char rotate(char d, cmd c) {
        int v = c.v % 360;
        if (c.c == 'L') {
            v = 360 - v; //only turn right ;)
        }
        while (v > 0) {
            v-=90;
            d = map.get(d);
        }
        return d;
    }

    public static void main(String args[]) throws Exception {
        String filename = "./resources/day12.1.input";
        List<String> lines = Files.readAllLines(Path.of(filename));

        List<cmd> cmds = lines.stream().map(s -> new cmd(s.charAt(0), Integer.parseInt(s.substring(1)))).collect(Collectors.toList());

        pos p = new pos(0, 0);
        char d = 'E';
        for (cmd c : cmds) {
            switch (c.c) {
                case 'L', 'R': {
                    d = rotate(d, c);
                    break;
                }
                case 'F': {
                    p = move(p, new cmd(d, c.v));
                    break;
                }
                case 'N', 'S', 'E', 'W': {
                    p = move(p, c);
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