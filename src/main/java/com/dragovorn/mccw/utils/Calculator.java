package com.dragovorn.mccw.utils;

import java.util.TreeMap;

public class Calculator {

    private static final TreeMap<Integer, String> map = new TreeMap<>();

    static {
        map.put(1000, "M");
        map.put(900, "CM");
        map.put(500, "D");
        map.put(400, "CD");
        map.put(100, "C");
        map.put(90, "XC");
        map.put(50, "L");
        map.put(40, "XL");
        map.put(10, "X");
        map.put(9, "IX");
        map.put(5, "V");
        map.put(4, "IV");
        map.put(1, "I");
    }

    public static String toRoman(int number) {
        int index = map.floorKey(number);

        if (number == index) {
            return map.get(number);
        }

        return map.get(index) + toRoman(number - index);
    }

    public static int formToLine(int i) {
        int lines = 1;

        if (i > 9) {
            lines++;
        }

        if (i > 9 * 2) {
            lines++;
        }

        if (i > 9 * 3) {
            lines++;
        }

        if (i > 9 * 4) {
            lines++;
        }

        if (i > 9 * 5) {
            lines++;
        }

        if (i > 9 * 6) {
            lines++;
        }

        return lines;
    }
}