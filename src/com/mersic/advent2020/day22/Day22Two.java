package com.mersic.advent2020.day22;

import java.nio.file.Files;
import java.nio.file.Path;
import java.security.SecureRandom;
import java.util.*;

public class Day22Two {
    
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
            p1 *= 10;
            p1 += i;
        }
        for (int i : pTwo) {
            p1 += i << 8;
        }
        return p1;
    }
    
    private static Deque<Set<Integer>> memo = new LinkedList<>();
    public static int combat(LinkedList<Integer> pOne, LinkedList<Integer> pTwo) {
        memo.push(new HashSet<>());
        while (pOne.size() > 0 && pTwo.size() > 0) {
            int hash = hash(pOne, pTwo);
            if (memo.peek().contains(hash)) { memo.pop(); if (VERBOSE) System.out.println("same game -- winner player 1"); return 1; }
            memo.peek().add(hash);
            if (pOne.peek() <= pOne.size()-1 && pTwo.peek() <= pTwo.size()-1) {
                int c1 = pOne.pop();
                int c2 = pTwo.pop();
                LinkedList<Integer> p1Copy = new LinkedList<>(pOne.subList(0,c1));
                LinkedList<Integer> p2Copy = new LinkedList<>(pTwo.subList(0,c2));
                if (combat(p1Copy, p2Copy) == 1) {
                    if (VERBOSE) System.out.println("r - winner player 1: " + c1 + " vs " + c2);
                    pOne.addLast(c1);
                    pOne.addLast(c2);
                } else {
                    if (VERBOSE) System.out.println("r - winner player 2: " + c1 + " vs " + c2);
                    pTwo.addLast(c2);
                    pTwo.addLast(c1);
                }
            } else if (pOne.peek() > pTwo.peek()) {
                if (VERBOSE) System.out.println("winner player 1: " + pOne.peek() + " vs " + pTwo.peek());
                pOne.addLast(pOne.pop());
                pOne.addLast(pTwo.pop());
            } else {
                if (VERBOSE) System.out.println("winner player 2: " + pOne.peek() + " vs " + pTwo.peek());
                pTwo.addLast(pTwo.pop());
                pTwo.addLast(pOne.pop());
            }
        }

        memo.pop();
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
