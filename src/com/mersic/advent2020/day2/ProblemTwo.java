package com.mersic.advent2020.day2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;

public class ProblemTwo {
    //Example
    //2-5 j: bjjjj
    public static boolean valid(String pass) {
        String[] s = pass.split(":");
        String pattern = s[0];
        String[] t = pattern.split(" ");
        String charCountRange = t[0];
        char c = t[1].charAt(0);
        String password = s[1];
        password = password.substring(1);
        String[] r = charCountRange.split("-");
        int p1 = Integer.parseInt(r[0]);
        int p2 = Integer.parseInt(r[1]);

        int len = password.length();
        int c1 = password.charAt(p1-1);
        int c2 = password.charAt(p2-1);

        if (p1 <= password.length() && password.charAt(p1-1) == c) {
            if (p2 <= password.length() && password.charAt(p2-1) == c) {
                return false;
            } else {
                return true;
            }
        } else if (p2 <= password.length() && password.charAt(p2-1) == c) {
            if (p1 <= password.length() && password.charAt(p1-1) == c) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public static void main(String args[]) throws Exception {
        String filename = "./resources/day2.1.input";

        FileReader fr = new FileReader(filename);
        BufferedReader br = new BufferedReader(fr);

        List<String> passwords = new LinkedList<>();


        String nextLine = null;
        int sum = 0;
        while ((nextLine = br.readLine()) != null) {
            passwords.add(nextLine);
        }

        long count = passwords.stream().filter(x->valid(x)).count();

        System.out.println("result: " + count);
    }
}
