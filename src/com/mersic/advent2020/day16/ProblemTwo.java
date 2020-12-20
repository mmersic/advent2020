package com.mersic.advent2020.day16;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ProblemTwo {

    public static Set<String> possibleFields(List<Integer[]> ticketInts, Map<String, Set<Integer>> fieldMap, int i) {
        Set<String> possibleFields = new HashSet<>();
        next: for (Map.Entry<String, Set<Integer>> E : fieldMap.entrySet()) {
            for (Integer[] t : ticketInts) {
                if (!E.getValue().contains(t[i])) {
                    continue next;
                }
            }
            possibleFields.add(E.getKey());
        }
        return possibleFields;
    }

    public static int validTicket(String ticket, Map<String, Set<Integer>> map) {
        List<Integer> fieldVals = Arrays.stream(ticket.split(",")).map(r -> Integer.parseInt(r)).collect(Collectors.toList());
        int invalidSum = 0;
        for (int i : fieldVals) {
            boolean valid = false;
            for (Map.Entry<String, Set<Integer>> E : map.entrySet()) {
                if (E.getValue().contains(i)) {
                    valid = true;
                    break;
                }
            }
            if (!valid) {
                invalidSum += i;
            }
        }
        return invalidSum;
    }

    public static void main(String args[]) throws Exception {
        String filename = "./resources/day16.1.input";
        List<String> lines = Files.readAllLines(Path.of(filename));

        Map<String, Set<Integer>> fieldMap = new HashMap<>();

        Iterator<String> I = lines.iterator();

        while(I.hasNext()) {
            String line = I.next();
            if (line.length() == 0) {
                break;
            }
            String[] s = line.split(":");
            String fieldName = s[0];
            s = s[1].split(" ");

            String[] r1 = s[1].split("-");
            Set<Integer> set = IntStream.rangeClosed(Integer.parseInt(r1[0]), Integer.parseInt(r1[1])).boxed().collect(Collectors.toSet());
            r1 = s[3].split("-");
            set.addAll(IntStream.rangeClosed(Integer.parseInt(r1[0]), Integer.parseInt(r1[1])).boxed().collect(Collectors.toSet()));
            fieldMap.put(fieldName, set);
            I.remove();
        }
        I.remove(); //space
        I.next();
        I.remove(); //your ticket:
        String myTicket = I.next();
        I.remove(); //ticket value...
        I.next();
        I.remove(); //spaces
        I.next();
        I.remove(); //nearby tickets:

        int part1 = lines.stream().map(s->validTicket(s, fieldMap)).reduce(Integer::sum).get();
        List<String> validTickets = lines.stream().filter(s->validTicket(s, fieldMap)==0).collect(Collectors.toList());

        List<Set<String>> possibleFields = new LinkedList<>();

        List<Integer[]> ticketInts = validTickets
                .stream()
                .map(s->Arrays.stream(s.split(","))
                        .map(r->Integer.parseInt(r)).collect(Collectors.toList()))
                .map(l->l.toArray(new Integer[0]))
                .collect(Collectors.toList());

        for (int i = 0; i < fieldMap.size(); i++) {
            possibleFields.add(possibleFields(ticketInts, fieldMap, i));
        }

        Map<String, Integer> fieldPosition = new HashMap<>();

        next: while (true) {
            for (int i = 0; i < possibleFields.size(); i++) {
                Set<String> s = possibleFields.get(i);
                if (s.size() == 1) {
                    String field = s.stream().findFirst().get();
                    fieldPosition.put(field, i);
                    System.out.println("fieldPosition: " + fieldPosition);
                    for (Set<String> pf : possibleFields) {
                        pf.remove(field);
                    }
                    continue next;
                }
            }
            break;
        }

        Integer[] my_ticket = Arrays.stream(myTicket.split(",")).map(s->Integer.parseInt(s)).collect(Collectors.toList()).toArray(new Integer[0]);

        long mult = 1;
        for (Map.Entry<String, Integer> E : fieldPosition.entrySet()) {
            if (E.getKey().startsWith("departure")) {
                mult *= my_ticket[E.getValue()];
            }
        }

        System.out.println("part1: " + part1);
        System.out.println("part2: " + mult);
    }

}
