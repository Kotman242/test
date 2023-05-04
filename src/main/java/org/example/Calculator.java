package org.example;


import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Calculator {

    private TreeMap<Integer, String> romanNumerals;
    public Map<String, Integer> arabianNumerals;

    public void start() {

        try (Scanner scanner = new Scanner(System.in)) {
            arabianNumerals = fillArabianMap();
            romanNumerals = fillRomanMap();

            while (true) {
                String task = scanner.nextLine();
                String[] taskArr = task.split(" ");
                int solution = checkTask(taskArr);

                if (isNumeral(taskArr[1])) {
                    System.out.println(solution);
                } else {
                    System.out.println(fromArabianToRoman(solution));
                }
            }
        }
    }

    private int checkTask(String[] taskArr) {

        isValidTask(taskArr);

        int numberFirst;
        int numberSecond;
        if (isNumeral(taskArr[0])) {
            numberFirst = Integer.parseInt(taskArr[0]);
            numberSecond = Integer.parseInt(taskArr[2]);
        } else {
            numberFirst = arabianNumerals.getOrDefault(taskArr[0].toUpperCase(), 0);
            numberSecond = arabianNumerals.getOrDefault(taskArr[2].toUpperCase(), 0);
        }
        if (numberFirst > 10 || numberFirst < 1 || numberSecond > 10 || numberSecond < 1) {
            throw new NumberFormatException("неверны числа диапазон чисел 1-10");
        }

        int solution = solvingTask(numberFirst, numberSecond, taskArr[1]);

        if (solution < 1 && !isNumeral(taskArr[0])) {
            throw new NumberFormatException("в римской системе нет отрицательных чисел");
        }
        return solution;
    }


    private boolean isNumeral(String number) {
        try {
            Integer.parseInt(number);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void isValidTask(String[] task) {
        if (task.length != 3) {
            throw new NumberFormatException("формат математической операции не удовлетворяет заданию - " +
                    "два операнда и один оператор (+, -, /, *)");
        }
        if (isNumeral(task[0]) != isNumeral(task[2])) {
            throw new NumberFormatException("используются одновременно разные системы счисления");
        }
    }

    private int solvingTask(int first, int second, String sign) {
        if (sign.equals("+")) {
            return first + second;
        } else if (sign.equals("-")) {
            return first - second;
        } else if (sign.equals("/")) {
            return first / second;
        } else if (sign.equals("*")) {
            return first * second;
        } else {
            throw new NumberFormatException("неверный оператор, доступные операторы (+, -, /, *)");
        }
    }

    private String fromArabianToRoman(int number) {
        int smallestNumber = romanNumerals.floorKey(number);
        if (smallestNumber == number) {
            return romanNumerals.get(number);
        }
        return romanNumerals.get(smallestNumber) + fromArabianToRoman(number - smallestNumber);
    }


    private TreeMap<Integer, String> fillRomanMap() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "I");
        map.put(4, "IV");
        map.put(5, "V");
        map.put(9, "IX");
        map.put(10, "X");
        map.put(40, "XL");
        map.put(50, "X");
        map.put(90, "XC");
        map.put(100, "C");
        return map;
    }

    private Map<String, Integer> fillArabianMap() {
        return Map.of("I", 1, "II", 2, "III", 3, "IV", 4, "V", 5,
                "VI", 6, "VII", 7, "VIII", 8, "IX", 9, "X", 10);
    }
}
