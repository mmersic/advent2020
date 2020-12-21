package com.mersic.advent2020.day21;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Day21 {

    public static Map<String, List<Set<String>>> parse(List<String> lines) {
        Map<String, List<Set<String>>> allergenCombos = new HashMap<>();
        
        for (String line : lines) {
            String[] s = line.split("( \\(contains )");
            
            Set<String> igr = new HashSet(Arrays.asList(s[0].split(" ")));
            List<String> allergens = Arrays.asList(s[1].replaceAll("[,\\)]", " ").split(" +"));

            for (String a : allergens) {
                List<Set<String>> ingredients = allergenCombos.get(a);
                if (ingredients == null) {
                    ingredients = new ArrayList<>();
                    allergenCombos.put(a, ingredients);
                }
                ingredients.add(igr);
            }
        }
        
        return allergenCombos;
    }
    
    public static Map<String, Integer> parseCount(List<String> lines) {
        Map<String, Integer> ingCount = new HashMap<>();
        
        for (String line : lines) {
            String[] s = line.split("( \\(contains )");
            List<String> ing = Arrays.asList(s[0].split(" "));
            for (String i : ing) {
                ingCount.put(i, ingCount.getOrDefault(i, 0)+1);
            }
        }
        return ingCount;
    }
    
    public static Map<String, String> mapIngredientToAllergen(Map<String, List<Set<String>>> allergenCombos) {
        Map<String, String> ingredientToAllergen = new HashMap<>();
        Set<String> allergensFound = new HashSet<>();
        for (int prevSize = -1; prevSize != ingredientToAllergen.size();) {
            prevSize = ingredientToAllergen.size();
            for (Map.Entry<String, List<Set<String>>> e : allergenCombos.entrySet()) {
                String allergen = e.getKey();
                if (allergensFound.contains((allergen))) {
                    continue;
                }
                List<Set<String>> ingredients = e.getValue();

                boolean first = true;
                Set<String> possibleIngredients = new HashSet<>();
                for (Set<String> s : ingredients) {
                    if (first) {
                        possibleIngredients.addAll(s);
                        first = false;
                    } else {
                        possibleIngredients.removeIf(r -> !s.contains(r));
                    }
                }

                for (Map.Entry<String,String> foundAllergen : ingredientToAllergen.entrySet()) {
                    possibleIngredients.remove(foundAllergen.getKey()); //remove ingredients with already known allergens.
                }

                if (possibleIngredients.size() == 1) {
                    ingredientToAllergen.put(possibleIngredients.stream().findFirst().get(), allergen);
                    allergensFound.add(allergen);
                }
            }
        }        
        return ingredientToAllergen;
    }
    
    public static String partTwo(Map<String, String> ingredientToAllergen) {
        record AllergenIngredient(String allergen, String ingredient) implements Comparable<AllergenIngredient> {
            @Override
            public int compareTo(AllergenIngredient o) {
                return this.allergen.compareTo(o.allergen);
            }
        }

        List<AllergenIngredient> list = new ArrayList<>();
        for (Map.Entry<String, String> e : ingredientToAllergen.entrySet()) {
            list.add(new AllergenIngredient(e.getValue(), e.getKey()));
        }

        Collections.sort(list);

        StringBuffer sb = new StringBuffer();
        if (list.size() > 0) {
            list.stream().forEach(s -> sb.append(s.ingredient).append(","));
            sb.deleteCharAt(sb.length() - 1);
        }
        
        return sb.toString();
    }


    private static int partOne(Map<String, Integer> ingredientCount, Map<String, String> ingredientToAllergen) {
        int sumNotAllergic = 0;
        for (Map.Entry<String, Integer> e : ingredientCount.entrySet()) {
            if (!ingredientToAllergen.containsKey(e.getKey())) {
                sumNotAllergic += e.getValue();
            }
        }
        return sumNotAllergic;
    }

    //each ingredient contains 0 or 1 allergen
    //each allergen is found in exactly 1 ingredient
    public static void main(String args[]) throws Exception {
        String filename = "./resources/day21.1.input";
        List<String> lines = Files.readAllLines(Path.of(filename));
        
        Map<String, Integer> ingredientCount = parseCount(lines);
        Map<String, String> ingredientToAllergen =  mapIngredientToAllergen(parse(lines));
        
        //2635
        System.out.println("part1: " + partOne(ingredientCount, ingredientToAllergen));
        
        //xncgqbcp,frkmp,qhqs,qnhjhn,dhsnxr,rzrktx,ntflq,lgnhmx
        System.out.println("part2: " + partTwo(ingredientToAllergen));
    }
}
