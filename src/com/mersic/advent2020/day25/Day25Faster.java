package com.mersic.advent2020.day25;

//Adapted from u/ramuuns-u soln.
public class Day25Faster {
    public static void main(String args[]) throws Exception {
        long startTime = System.currentTimeMillis();

        long pub1 = 12232269l;
        long pub2 = 19452773l;
        final long    r = 20201227;
        final long    s = 7;
        long   v1 = 1;
        long   e1 = 1;
        long   e2 = 1;
        
        while (true) {
            v1 = (v1 * s) % r;
            e1 = (e1 * pub1) % r;
            e2 = (e2 * pub2) % r;
            if (v1 == pub1 || v1 == pub2) {
                break;
            }
        }
        
        long enc = 0;
        if (v1 == pub1) {
            enc = e2;
        } else {
            enc = e1;
        }
        
        long finishTime = System.currentTimeMillis();

        //354320
        System.out.println("enc: " + enc + " in time: " + (finishTime-startTime));

    }
}
