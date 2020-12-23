package com.mersic.advent2020.day23;

import java.nio.file.Files;
import java.nio.file.Path;

public class Day23 {
    
    public static class Node {
        Node next;
        Node prev;
        Node minus;
        int cup;
        
        public Node(int cup) {
            this.cup = cup;
        }
        
        public String toString() {
            return "" + cup;
        }
    }
    
    public static Node setup(int input[]) {
        
        Node currentCup = new Node(input[0]);
        Node tempCup = currentCup;
        Node prevCup = null;
        for (int i = 1; i < input.length; i++) {
            tempCup.next = new Node(input[i]);
            prevCup = tempCup;
            tempCup = tempCup.next;
            tempCup.prev = prevCup;
        }
        tempCup.next = currentCup;
        currentCup.prev = tempCup;
        
        tempCup = currentCup; 
        Node oneCup = null;
        do {
            if (tempCup.cup == 1) {
                oneCup = tempCup; 
            } else {
                int minusCup = tempCup.cup - 1;
                Node t = tempCup.next;
                while (true) {
                    if (t.cup == minusCup) {
                        tempCup.minus = t;
                        break;
                    } else {
                        t = t.next;
                    }
                }
            }
            tempCup = tempCup.next;
        } while (tempCup != currentCup);
        
        tempCup = currentCup;
        while (tempCup.cup != 9) {
            tempCup = tempCup.next;
        }
        
        oneCup.minus = tempCup;
        
        return currentCup;
    }
    
    public static void placePickedUpCups(Node p, Node d) {
        Node next = d.next;
        
        d.next = p;
        p.prev = d;
        
        next.prev = p.next.next;
        p.next.next.next = next;
    }
    
    public static Node findDestinationCup(Node currentCup, Node p) {
        Node dest = currentCup.minus;
        
        while (dest == p || dest == p.next || dest == p.next.next) {
            dest = dest.minus;
        }         
        
        return dest;
    }
    
    
    public static Node removeNext3(Node currentCup) {
        Node p1 = currentCup.next;
        Node p3 = currentCup.next.next.next;
        Node l  = p3.next;
        
        currentCup.next = l;
        l.prev = currentCup;
        
        p1.prev = null;
        p3.next = null;
        

        return p1;
    }
    
    public static void main(String args[]) throws Exception {
        //example input
//        int[] input = new int[] { 3,8,9,1,2,5,4,6,7 };
        
        //part 1
        int [] input = new int[] { 5,8,3,9,7,6,2,4,1 };
        
        Node currentCup = setup(input);
        
        int moves = 100;
        
        for (int i = 0; i < moves; i++) {
            Node p = removeNext3(currentCup);
            Node d = findDestinationCup(currentCup, p);
            placePickedUpCups(p, d);
            currentCup = currentCup.next;
            //System.out.println("move: " + i + " picked up: " + p + ", " + p.next + ", " + p.next.next);
        }
        
        Node tempCup = currentCup;
        do {
            tempCup = tempCup.next;
            if (tempCup.cup == 1) {
                tempCup = tempCup.next;
                break;
            }
        } while (tempCup != currentCup);

        Node lastCup = tempCup.prev;
        System.out.print("part1: ");
        do {
            System.out.print(tempCup.cup);
            tempCup = tempCup.next;
        } while (tempCup != lastCup);
        System.out.println();
        //ex:    67384529
        //part1: 24987653
        
    }
    
}
