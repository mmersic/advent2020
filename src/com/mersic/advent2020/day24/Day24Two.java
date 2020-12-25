package com.mersic.advent2020.day24;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Day24Two {
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
    
    public static CubeCord[] neighbors = new CubeCord[] {
            new CubeCord(0, -1),
            new CubeCord(-1, -1),
            new CubeCord(0, 1),
            new CubeCord(1, 1),
            new CubeCord(1, 0),
            new CubeCord(-1, 0)
    };

    public static CubeCord walkTile(String tileWalk) {
        CubeCord dest = new CubeCord(0,0);

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

        long startTime = System.currentTimeMillis();

        Set<CubeCord> blackTiles = new HashSet<>();

        for (String tileWalk : tileWalks) {
            CubeCord dest = walkTile(tileWalk);
            if (!blackTiles.remove(dest)) { //keep track of just the black tiles
                blackTiles.add(dest);
            }
        }

        for (int i = 0; i < 100; i++) {
            blackTiles = flipTiles(blackTiles);
        }
        
        int blackCount = blackTiles.size();
        
        long finishTime = System.currentTimeMillis();

        System.out.println("part1: " + blackCount + " in time: " + (finishTime-startTime));
    }

    private static Set<CubeCord> flipTiles(Set<CubeCord> fromSet) {
        Set<CubeCord> toSet = new HashSet<>();
        Set<CubeCord> toConsider = new HashSet<>();
        
        for (CubeCord c : fromSet) {
            toConsider.add(c);
            for (CubeCord n : neighbors) {
                toConsider.add(new CubeCord(c.x + n.x, c.z + n.z));
            }
        }
        
        for (CubeCord c : toConsider) {
            int blackNeighborCount = 0;
            for (CubeCord n : neighbors) {
                if (fromSet.contains(new CubeCord(c.x+n.x, c.z+n.z))) blackNeighborCount++;
            }
            if (fromSet.contains(c)) {
                if ((blackNeighborCount == 1 || blackNeighborCount == 2)) {
                    toSet.add(c);
                }
            } else {
                if (blackNeighborCount == 2) {
                    toSet.add(c);
                }
            }
        }
        
        return toSet;
    }
}
