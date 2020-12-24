package com.mersic.advent2020.day23;

public class Day23AltnTwo {
    /**
     * No need for prev pointers.  So can use an array.
     * cups[cup] = next
     * 
     * //about 200ms
     */
    
    final private static int max = 1000000;
    final private static int moves = 10000000;
    //example input
    //final private static int[] input = new int[]{3, 8, 9, 1, 2, 5, 4, 6, 7};
    
    final private static int[] input = new int[]{5, 8, 3, 9, 7, 6, 2, 4, 1};

    public static int[] part2(int[] input) {
        int[] cups = new int[max+1];
        int first = input[0];
        for (int i = 1; i < input.length; i++) {
            cups[input[i-1]] = input[i];
        }
        
        for (int i = 10; i <= max;) {
            cups[i] = ++i; 
        }
        
        cups[input[input.length-1]] = 10;
        cups[max] = first;
        
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

    public static void main(String[] args) throws Exception {
        long startTime = System.currentTimeMillis();
        int[] cups = part2(input);
        int current = input[0];
        for (int i = 0; i < moves; i++) {
            int p1 = cups[current];
            int p2 = cups[p1];
            int p3 = cups[p2];
            int dest = current;
            do {
                dest--;
                if (dest < 1) dest = max;
            } while (dest == p1 || dest == p2 || dest == p3);
            int next = cups[p3];
            cups[current] = next;
            cups[p3] = cups[dest];
            cups[dest] = p1;
            current = next;
        }
        
        long n1 = cups[1];
        long n2 = cups[cups[1]];
        long result = n1*n2;
        long finishTime = System.currentTimeMillis();
        
        //442938711161
        System.out.println("part2: " + result + " in time: " + (finishTime-startTime));
    }    
}
