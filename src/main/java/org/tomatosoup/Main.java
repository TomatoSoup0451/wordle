package org.tomatosoup;

public class Main {
    public static void main(String[] args) {
        CharsValue.calculateCharsWithPosition(5);
        System.out.println("С учетом позиции");
        CharsValue.printMostValuableWithPosition(5);
        CharsValue.calculateChars(5);
        System.out.println("Без учета позиции");
        CharsValue.printMostValuable(5);
        new ConsoleInterface().launchApp();
    }
}