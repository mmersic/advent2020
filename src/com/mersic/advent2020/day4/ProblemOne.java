package com.mersic.advent2020.day4;

        import java.io.BufferedReader;
        import java.io.FileReader;
        import java.util.HashMap;
        import java.util.LinkedList;
        import java.util.List;
        import java.util.Map;

public class ProblemOne {
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

        int valid = 0;
        for (Map<String, String> e : entries) {
            if (!e.containsKey("byr")) continue;
            if (!e.containsKey("iyr")) continue;
            if (!e.containsKey("eyr")) continue;
            if (!e.containsKey("hgt")) continue;
            if (!e.containsKey("hcl")) continue;
            if (!e.containsKey("ecl")) continue;
            if (!e.containsKey("pid")) continue;

            valid++;
        }

        System.out.println("result: " + valid);
    }
}
