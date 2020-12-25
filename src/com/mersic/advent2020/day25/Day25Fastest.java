package com.mersic.advent2020.day25;

import java.math.BigInteger;

// Adapted from linl33's soln: https://github.com/linl33/adventofcode/blob/year2020/year2020/src/main/java/dev/linl33/adventofcode/year2020/Day25.java
public class Day25Fastest {
    final static long pub1 = 12232269l;
    final static long pub2 = 19452773l;
    final static long    r = 20201227;
    final static long    s = 7;

    public static void main(String args[]) throws Exception {
        long startTime = System.currentTimeMillis();

        int rounds = 0;
        long    v1 = 1;

        for (; v1 != pub1 && v1 != pub2; rounds++) {
            v1 = (v1 * s) % r;
        }

        /* v == pub1 or v == pub2
         * if v == pub1 then v1 ^ pub1 is 0 and v1 ^ pub1 ^ pub2 == pub2
         * if v == pub2 then v2 ^ pub2 is 0 and v1 ^ pub1 ^ pub2 == pub1
         */
        BigInteger enc = BigInteger.valueOf(v1 ^ pub1 ^ pub2).modPow(BigInteger.valueOf(rounds), BigInteger.valueOf(r));

        long finishTime = System.currentTimeMillis();

        //354320
        System.out.println("enc: " + enc + " in time: " + (finishTime-startTime) + " rounds: " + rounds);

    }
}
