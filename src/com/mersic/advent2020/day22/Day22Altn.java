package com.mersic.advent2020.day22;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class Day22Altn {
    private static boolean VERBOSE = false;

    private static LinkedList<Integer> parsePlayerString(String playerString) {
        LinkedList<Integer> player = new LinkedList<>();
        String[] temp = playerString.split("\n");
        for (int i = 1; i < temp.length; i++) {
            player.addLast(Integer.parseInt(temp[i]));
        }
        return player;
    }

    private static int hash(Deque<Integer> pOne, Deque<Integer> pTwo) {
        int p1 = 0;
        for (int i : pOne) {
            p1 *= 11;
            p1 += i;
        }
        for (int i : pTwo) {
            p1 *= 13;
            p1 += i << 8;
        }
        return p1;
    }

    //rewrote to be more like u/VeeArr soln.
    public static int combat(LinkedList<Integer> pOne, LinkedList<Integer> pTwo) {
        Set<Integer> memo = new HashSet<>();
        while (pOne.size() > 0 && pTwo.size() > 0) {
            int hash = hash(pOne, pTwo);
            if (!memo.add(hash)) { if (VERBOSE) System.out.println("same game -- winner player 1"); return 1; }

            int c1 = pOne.pop();
            int c2 = pTwo.pop();
            
            int winner = 0;
            if (c1 <= pOne.size() && c2 <= pTwo.size()) {
                winner = combat(new LinkedList<>(pOne.subList(0, c1)), new LinkedList<>(pTwo.subList(0, c2)));
            } else {
                winner = (c1 > c2) ? 1 : 2;
            }
            
            if (winner == 1) { 
                if (VERBOSE) System.out.println("winner player 1: " + pOne.peek() + " vs " + pTwo.peek());
                pOne.addLast(c1);
                pOne.addLast(c2);
            } else {
                if (VERBOSE) System.out.println("winner player 2: " + pOne.peek() + " vs " + pTwo.peek());
                pTwo.addLast(c2);
                pTwo.addLast(c1);
            }
        }

        if (pOne.size() > 0) {
            return 1;
        } else {
            return 2;
        }
    }

    public static void main(String args[]) throws Exception {
        String filename = "./resources/day22.input";
        String[] playerStrings = Files.readString(Path.of(filename)).split("\n\n");
        LinkedList<Integer> pOne = parsePlayerString(playerStrings[0]);
        LinkedList<Integer> pTwo = parsePlayerString(playerStrings[1]);

        long startTime = System.currentTimeMillis();
        Deque<Integer> winner = combat(pOne, pTwo) == 1 ? pOne : pTwo;
        long score = 0;
        int  i = winner.size();
        for (Integer w : winner) {
            score += (i-- * w);
        }
        long finishTime = System.currentTimeMillis();

        //35436
        System.out.println("part2: " + score + " in time: " + (finishTime-startTime) + "ms.");
    }
}
