package com.mersic.advent2020.day20;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Day20 {

    private static class Image {
        int id;
        char[][] image;
        int rotation = 0; //0 == none, 1 == right, 2 == 2xright, 3 == 3xright
        int flip     = 0; //0 == none, 1 == vertical
        char[][] transform;
        char[][] prevTransform;

        public Image(int id, char[][] image) {
            this.id    = id;
            this.image = image;
            this.transform = new char[image.length][image[0].length];
            this.prevTransform = new char[image.length][image[0].length];
            for (int i = 0; i < image.length; i++) {
                for (int j = 0; j < image[0].length; j++) {
                    transform[i][j] = image[i][j];
                }
            }
        }

        void transform() {
            if (flip == 1) {
                for (int i = 0; i < image.length; i++) {
                    for (int j = 0; j < image[0].length; j++) {
                        transform[i][j] = image[(image.length-1)-i][j];
                    }
                }
            } else {
                for (int i = 0; i < image.length; i++) {
                    for (int j = 0; j < image[0].length; j++) {
                        transform[i][j] = image[i][j];
                    }
                }
            }
            for (int rr = 0; rr < rotation; rr++) {
                char[][] temp = prevTransform;
                prevTransform = transform;
                transform = temp;
                for (int y = 0; y < image.length; y++) {
                    for (int x = 0; x < image[0].length; x++) {
                        //w = new pos(w.y, -w.x); //day12 part 2
                        int new_x = y;
                        int new_y = -x+(image.length-1);
                        transform[y][x] = prevTransform[new_y][new_x];
                    }
                }
            }
        }
    }

    public static boolean fit(Image[][] I, Image img, int x, int y) {
        if (x > 0) { //check image to left
            Image C = I[y][x-1];
            for (int i = 0; i < img.image.length; i++) {
                if (C.transform[i][C.image.length-1] != img.transform[i][0]) {
                    return false;
                }
            }
        }

        if (y > 0) { //check image above
            Image C = I[y-1][x];
            for (int i = 0; i < img.image[0].length; i++) {
                if (C.transform[img.image.length-1][i] != img.transform[0][i]) {
                    return false;
                }
            }
        }

        return true;
    }

    static int maxDepth = 0;
    static long imgAttempts = 0;
    public static boolean findFit(List<Image> images, Image[][] I, int x, int y, Set<Image> usedImages, int depth) {
        if (depth > maxDepth) {
            maxDepth = depth;
            if (maxDepth % 10 == 0) 
                System.out.println("maxDepth: " + maxDepth + " on image: " + imgAttempts);
        }
        if (usedImages.size() == images.size()) { return true; }
        for (Image img : images) {
            if (usedImages.contains(img)) { continue; }
            for (int r = 0; r <=3; r++) {
                nextFlip: for (int f = 0; f <= 1; f++) {
                    imgAttempts++;
                    img.rotation = r;
                    img.flip = f;
                    img.transform();
                    if (fit(I, img, x, y)) {
                        I[y][x] = img;
                        usedImages.add(img);
                        int dim = I.length;
                        int num = y*dim+x; num++;
                        boolean next = findFit(images, I, num%dim, num/dim, usedImages, depth+1);
                        if (!next) {
                            usedImages.remove(img);
                            I[y][x] = null;
                            continue nextFlip;
                        } else {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public static Image[][] findFit(List<Image> images) {
        Set<Image> usedImages = new HashSet<>();
        int n = (int) Math.sqrt(images.size());
        Image[][] I = new Image[n][n];
        if (findFit(images, I, 0, 0, usedImages, 0)) {
            return I;
        } else {
            return null;
        }
    }

    public static List<Image> parse(String input) {
        List<Image> images = new ArrayList<Image>();
        String[] S = input.split("\n\n");
        for (String I : S) {
            String[] img = I.split("\n");
            char[][] c = new char[img.length-1][img[0].length()];
            for (int i = 1; i < img.length; i++) {
                c[i-1] = img[i].toCharArray();
            }
            images.add(new Image(Integer.parseInt(img[0].split("[ :]")[1]), c));
        }
        return images;
    }

    public static Image parseMonster(String input) {
        String[] img = input.split("\n");
        char[][] c = new char[img.length][img[0].length()];
        for (int i = 0; i < img.length; i++) {
            c[i] = img[i].toCharArray();
        }
        return new Image(-1, c);
    }

    private static Image toBigImage(Image[][] I) {
        int X = I[0].length * 8;
        int Y = X;
        char[][] bigImage = new char[Y][X];

        int bigX = 0;
        int bigY = 0;
        for (int y = 0; y < I.length; y++) {
            for (int x = 0; x < I.length; x++) {
                char[][] smallImage = I[y][x].transform;
                int startX = bigX;
                int startY = bigY;
                for (int yy = 1; yy < 9; yy++) {
                    for (int xx = 1; xx < 9; xx++) {
                        bigImage[startY][startX++] = smallImage[yy][xx];
                    }
                    startY++;
                    startX = bigX;
                }
                bigX += 8;
            }
            bigY += 8;
            bigX = 0;
        }
        return new Image(-1, bigImage);
    }


    public static void main(String args[]) throws Exception {
        String filename = "./resources/day20.1.input";
        String seaMonsterFileName = "./resources/day20.monster.input";
        List<Image> images = parse(Files.readString(Path.of(filename)));

        long startTime = System.currentTimeMillis();
        Image[][] I = findFit(images);
        long finishTime = System.currentTimeMillis();
        System.out.println("part1 time took: " + (finishTime-startTime) + "ms. image permutations: " + imgAttempts);

        int l = I.length-1;
        long mult = 1l * I[0][0].id * I[l][l].id * I[0][l].id * I[l][0].id;
        System.out.println("part1: " + mult);

        Image bigImage = toBigImage(I);

//        print(bigImage);

        Image seaMonster = parseMonster(Files.readString(Path.of(seaMonsterFileName)));

        int monsterCount = 0;
        done: for (int r = 0; r < 4; r++) {
            for (int f = 0; f < 2; f++) {
                bigImage.rotation = r;
                bigImage.flip = f;
                bigImage.transform();
                monsterCount = monsterCount(bigImage, seaMonster);
                if (monsterCount > 0) {
//                    System.out.println("found: " + monsterCount);
//                    print(bigImage);
//                    markMonsters(bigImage, seaMonster);
//                    print(bigImage);
                    break done;
                }
            }
        }
        
        int waveCount = 0;
        for (int y = 0; y < bigImage.transform.length; y++) {
            for (int x = 0; x < bigImage.transform[0].length; x++) {
                if (bigImage.transform[y][x] == '#') {
                    waveCount++;
                }
            }
        }
        System.out.println("part2: " + (waveCount-(monsterCount*15)));
        System.out.println("part2 took another: " + (System.currentTimeMillis()-finishTime) + "ms.");
    }

    private static int monsterCount(Image bigImage, Image seaMonster) {
        int monsters = 0;
        nextY: for (int y = 0; y < bigImage.transform.length; y++) {
            nextX: for (int x = 0; x < bigImage.transform[0].length; x++) {
                for (int yy = 0; yy < seaMonster.image.length; yy++) {
                    for (int xx = 0; xx < seaMonster.image[0].length; xx++) {
                        try {
                            if (seaMonster.image[yy][xx] == '#' && bigImage.transform[y + yy][x + xx] != '#') {
                                continue nextX;
                            }
                        } catch (IndexOutOfBoundsException e) {
                            continue nextY;
                        }
                    }
                }
                monsters++;
            }
        }
        return monsters;
    }

    /** For debugging, not part of generating the soln */
    private static void markMonsters(Image bigImage, Image seaMonster) {
        nextY: for (int y = 0; y < bigImage.transform.length; y++) {
            nextX: for (int x = 0; x < bigImage.transform[0].length; x++) {
                for (int yy = 0; yy < seaMonster.image.length; yy++) {
                    for (int xx = 0; xx < seaMonster.image[0].length; xx++) {
                        try {
                            if (seaMonster.image[yy][xx] == '#' && bigImage.transform[y + yy][x + xx] != '#') {
                                continue nextX;
                            }
                        } catch (IndexOutOfBoundsException e) {
                            continue nextY;
                        }
                    }
                }
                //found monster, mark it.
                for (int yy = 0; yy < seaMonster.image.length; yy++) {
                    for (int xx = 0; xx < seaMonster.image[0].length; xx++) {
                        try {
                            if (seaMonster.image[yy][xx] == '#' &&  bigImage.transform[y + yy][x + xx] == '#') {
                                bigImage.transform[y + yy][x + xx] = 'O';
                            }
                        } catch (IndexOutOfBoundsException e) {
                            continue nextY;
                        }
                    }
                }
            }
        }
    }

    /** For debugging, not part of generating the soln */
    public static void print(Image img) {
        System.out.println("id: " + img.id + " r: " + img.rotation + " f: " + img.flip);
        for (int i = 0; i < img.image.length; i++) {
            for (int j = 0; j < img.image[0].length; j++) {
                System.out.print(img.transform[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    /** For debugging, not part of generating the soln */
    public static void printDebug(Image img) {
        int r = img.rotation;
        int f = img.flip;
        img.flip = 0;
        img.rotation = 0;
        System.out.println("id: " + img.id + " r: " + img.rotation + " f: " + img.flip);
        print(img);
        if (f > 0) {
            img.flip = f;
            System.out.println("id: " + img.id + " r: " + img.rotation + " f: " + img.flip);
            img.transform();
            print(img);
        }

        for (int rr = 1; rr <= r; rr++) {
            img.rotation = rr;
            System.out.println("id: " + img.id + " r: " + img.rotation + " f: " + img.flip);
            img.transform();
            print(img);
        }
        System.out.println();
    }    
}
