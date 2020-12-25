package com.mersic.advent2020.day24;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Day24 {
    record CubeCord(int x, int z) {
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CubeCord cubeCord = (CubeCord) o;
            return x == cubeCord.x && z == cubeCord.z;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, z);
        }
    };
    
    public static CubeCord walkTile(CubeCord start, String tileWalk) {
        CubeCord dest = start;
        
        char[] walk = tileWalk.toCharArray();
        
        next: for (int i = 0; i < walk.length; i++) {
            switch (walk[i]) {
                case 's': {
                    switch (walk[++i]) {
                        case 'e': {
                            dest = new CubeCord(dest.x, dest.z-1);
                            continue next;
                        }
                        case 'w': {
                            dest = new CubeCord(dest.x-1, dest.z-1);
                            continue next;
                        }
                        default: {
                            throw new RuntimeException("unexpected code: " + walk[i]);
                        }
                    }
                }
                case 'n': {
                    switch (walk[++i]) {
                        case 'e': {
                            dest = new CubeCord(dest.x+1, dest.z+1);
                            continue next;
                        }
                        case 'w': {
                            dest = new CubeCord(dest.x, dest.z+1);
                            continue next;
                        }
                        default: {
                            throw new RuntimeException("unexpected code: " + walk[i]);
                        }
                    }
                }
                case 'w': {
                    dest = new CubeCord(dest.x-1, dest.z);
                    continue next;
                }
                case 'e': {
                    dest = new CubeCord(dest.x+1, dest.z);
                    continue next;
                }
                default: {
                    throw new RuntimeException("unexpected code: " + walk[i]);
                }
            }
        }
        
        return dest;
    }
    
    
    public static void main(String args[]) throws Exception {
        String filename = "./resources/day24.input";
        String[] tileWalks = Files.readString(Path.of(filename)).split("\n");

        CubeCord start = new CubeCord(0,0);
        
        Map<CubeCord, Integer> map = new HashMap<>();
                
        for (String tileWalk : tileWalks) {
            CubeCord dest = walkTile(start, tileWalk);
            if (!map.remove(dest, 1)) {
                map.put(dest, 1);
            }
        }
        
        int blackCount = 0;
        for (Map.Entry<CubeCord, Integer> e : map.entrySet()) {
            blackCount += e.getValue() == 1 ? 1 : 0;
        }
        
        System.out.println("part1: " + blackCount);
    }
}
