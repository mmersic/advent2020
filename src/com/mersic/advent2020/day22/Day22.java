package com.mersic.advent2020.day22;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Deque;
import java.util.LinkedList;

public class Day22 {

    private static Deque<Integer> parsePlayerString(String playerString) {
        Deque<Integer> player = new LinkedList<>();
        String[] temp = playerString.split("\n");
        for (int i = 1; i < temp.length; i++) {
            player.addLast(Integer.parseInt(temp[i]));
        }
        return player;
    }
    
    public static void main(String args[]) throws Exception {
        String filename = "./resources/day22.input";
        String[] playerStrings = Files.readString(Path.of(filename)).split("\n\n");
        Deque<Integer> pOne = parsePlayerString(playerStrings[0]);
        Deque<Integer> pTwo = parsePlayerString(playerStrings[1]);
        
        int roundCount = 0;
        while (pOne.size() > 0 && pTwo.size() > 0) {
            if (pOne.peek() > pTwo.peek()) {
                pOne.addLast(pOne.pop());
                pOne.addLast(pTwo.pop());
            } else {
                pTwo.addLast(pTwo.pop());
                pTwo.addLast(pOne.pop());
            }
            roundCount++;
        }
        
        Deque<Integer> winner = null;
        if (pOne.size() > 0) {
            winner = pOne;
        } else {
            winner = pTwo;
        }
        
        long score = 0;
        int  i = winner.size();
        for (Integer w : winner) {
            score += (i-- * w);
        }
        
        System.out.println("part1: " + score + " in " + roundCount + " rounds");
    }
}
