package com.mersic.advent2020.day25;

public class Day25 {
    
    static long findLoop(long pub, long r, long s) {
        long loop = 0;
        long val   = 1;
        
        while (val != pub) {
            val = val * s;
            val %= r;
            loop++;
        }
        
        return loop;
    }
    
    static long findEnc(long pub, long loop, long r) {
        long val = 1;
        for (int i = 0; i < loop; i++) {
            val *= pub;
            val %= r;
        }
        return val;
    }
    
    public static void main(String args[]) throws Exception {
        long startTime = System.currentTimeMillis();
        
        long pub1 = 12232269l;
        long pub2 = 19452773l;
        long    r = 20201227;
        long    s = 7;
        
        long loop1 = findLoop(pub1, r, s);
        long loop2 = findLoop(pub2, r, s);
        
        long enc1  = findEnc(pub1, loop2, r);
        long enc2  = findEnc(pub2, loop1, r);
        long finishTime = System.currentTimeMillis();
        
        System.out.println("loop1: " + loop1 + " loop2: " + loop2);
        
        //354320
        System.out.println("enc1: " + enc1 + " enc2: " + enc2 + " in time: " + (finishTime-startTime));
        
    }
}
