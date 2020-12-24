package com.mersic.advent2020.day23;

public class Day23Altn {

    /**
     * No need for prev pointers.  So can use an array.
     * cups[cup] = next
     * 
     * @param input
     * @return
     */
    public static int[] part1(int[] input) {
        int[] cups = new int[input.length+1];
        int first = input[0];
        for (int i = 1; i < input.length; i++) {
            cups[input[i-1]] = input[i];
        }
        cups[input[input.length-1]] = first;
        return cups;
    }
    
    public static void print(int[] cups, int c) {
        int start = c;
        do {
            System.out.print(c + ",");
            c = cups[c];
        } while (start != c);
        System.out.println();
    }
    
    public static void printPart1(int[] cups) {
        int current = cups[1];
        do {
            System.out.print(current);
        } while ((current = cups[current]) != 1);
    }
    
    public static void main(String args[]) throws Exception {
        //example input
        //int[] input = new int[] { 3,8,9,1,2,5,4,6,7 };
        int max = 9;
        long startTime = System.currentTimeMillis();
        int[] input = new int[]{5, 8, 3, 9, 7, 6, 2, 4, 1};
        int[] cups = part1(input);
        
        int moves = 100;
        int current = input[0];
        for (int i = 0; i < moves; i++) {
            int p1 = cups[current];
            int p2 = cups[cups[current]];
            int p3 = cups[cups[cups[current]]];
            
            int dest = current;
            do {
                dest--;
                if (dest < 1) dest = max;
            } while (dest == p1 || dest == p2 || dest == p3);
            
            cups[current] = cups[p3];
            cups[p3] = cups[dest];
            cups[dest] = p1;
            current = cups[current];
        }
        print(cups, current);
        printPart1(cups);
    }
}
