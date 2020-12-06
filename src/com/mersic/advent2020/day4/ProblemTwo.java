package com.mersic.advent2020.day4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class ProblemTwo {

    static Set<String> eyeColors = new HashSet<>();


    public static boolean checkPassportId(String value) {
        if (value == null || value.length() != 9) {
            return false;
        }
        return value.matches("[0-9]{9}");
    }

    public static boolean checkEyeColor(String value) {
        if (value == null) {
            return false;
        }
        return eyeColors.contains(value);
    }

    public static boolean checkHairColor(String value) {
        if (value == null) {
            return false;
        }
        return value.matches("#[0-9a-f]{6}");
    }

    public static boolean checkHeight(String value) {
        try {
            if (value == null) {
                return false;
            }
            String v1 = value.substring(0, value.length()-2);
            String v2 = value.substring(value.length()-2);
            int h = Integer.parseInt(v1);
            if ("in".equals(v2)) {
                if (59 <= h && h <= 76) {
                    return true;
                } else {
                    return false;
                }
            } else if ("cm".equals(v2)) {
                if (150 <= h && h <= 193) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println("invalid hieght: " + value);
            return false;
        }
    }

    public static boolean checkRange(String value, int low, int high) {
        try {
            if (value == null) {
                return false;
            }
            int v = Integer.parseInt(value);
            if (low <= v && v <= high) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println("not a number: " + value);
            return false;
        }
    }

    public static void main(String args[]) throws Exception {
        String filename = "./resources/day4.1.input";

        FileReader fr = new FileReader(filename);
        BufferedReader br = new BufferedReader(fr);

        List<String> land = new LinkedList<>();

        List<Map<String,String>> entries = new LinkedList<>();

        String nextLine = null;
        Map<String, String> entry = new HashMap<>();
        while ((nextLine = br.readLine()) != null) {
            if ("".equals(nextLine)) {
                entries.add(entry);
                entry = new HashMap<>();
                continue;
            }
            String[] fields = nextLine.split(" ");
            for (String field : fields) {
                String[] kv = field.split(":");
                entry.put(kv[0], kv[1]);
            }
        }
        if (!entry.isEmpty())
            entries.add(entry);

        eyeColors.add("amb");
        eyeColors.add("blu");
        eyeColors.add("brn");
        eyeColors.add("gry");
        eyeColors.add("grn");
        eyeColors.add("hzl");
        eyeColors.add("oth");


        int valid = 0;
        for (Map<String, String> e : entries) {
            if (!checkRange(e.get("byr"), 1920, 2002)) continue;
            if (!checkRange(e.get("iyr"), 2010, 2020)) continue;
            if (!checkRange(e.get("eyr"), 2020, 2030)) continue;
            if (!checkHeight(e.get("hgt"))) continue;
            if (!checkHairColor(e.get("hcl"))) continue;
            if (!checkEyeColor(e.get("ecl"))) continue;
            if (!checkPassportId(e.get("pid"))) continue;
            valid++;
        }

        System.out.println("result: " + valid);
    }
}
