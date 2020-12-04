package com.mersic.advent2020.day2;


import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;

/**
 * Each line indicates the password policy and password.
 * 1-3 a: aaaa  -- Must have 1-3 as, this password fails.
 *
 * How many valid passwords in the input file?
 */
public class ProblemOne {

    public static boolean valid(String pass) {
        String[] s = pass.split(":");
        String pattern = s[0];
        String[] t = pattern.split(" ");
        String charCountRange = t[0];
        char c = t[1].charAt(0);
        String password = s[1];
        long charCount = password.chars().filter(x -> x==c).count();

        String[] r = charCountRange.split("-");
        int low = Integer.parseInt(r[0]);
        int high = Integer.parseInt(r[1]);

        boolean valid = charCount >= low && charCount <= high;
        return valid;
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
