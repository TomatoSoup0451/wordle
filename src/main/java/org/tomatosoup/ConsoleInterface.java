package org.tomatosoup;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ConsoleInterface {

    private RegexBuilder builder;
    private final Scanner sc = new Scanner(System.in);
    boolean isEnded = false;
    Path path = Paths.get("src/main/resources/singular.txt");

    public void launchApp() {
        chooseWordLength();
        while (!isEnded) {
            chooseAction();
        }
    }

    private void chooseAction() {
        printMenu();
        try {
            switch (sc.nextInt()) {
                case 1 -> builder.setMultipleLetters(sc.next());
                case 2 -> sc.next().chars().forEach(c -> builder.addMandatoryLetter((char) c));
                case 3 -> sc.next().chars().forEach(c -> builder.addMissingLetter((char) c));
                case 4 -> {
                    System.out.println(builder.getRegex());
                    System.out.println(builder.getMandatoryLetters());
                    System.out.println(builder.getMissingLetters());
                    System.out.println(showPossibleWords());
                }
                case 5 -> {
                    System.out.println("Введите позицию буквы");
                    int index = sc.nextInt();
                    System.out.println("Введите букву");
                    char c = sc.next().charAt(0);
                    builder.setLetter(index - 1, c);
                }
                case 6 -> {
                    System.out.println("Введите позицию буквы");
                    int index = sc.nextInt();
                    System.out.println("Введите букву");
                    char c = sc.next().charAt(0);
                    builder.setLetterInWrongPlace(index - 1, c);
                }
                case 0 -> isEnded = true;
                default -> {
                    System.out.println("Неизвестная команда");
                    chooseAction();
                }
            }

        } catch (
                InputMismatchException e) {
            System.out.println("Должно быть введено целое число");
            chooseAction();
        }

    }

    private List<String> showPossibleWords() {
        try (Stream<String> lines = Files.lines(path)) {
            return lines.filter(s -> s.matches(builder.getRegex()))
                    .filter(s -> builder.getMandatoryLetters().stream().allMatch(c -> s.contains(c.toString())))
                    .limit(10)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void printMenu() {
        System.out.println("Выберете требуемое действие:\n" +
                "1 - Ввести известные буквы\n" +
                "2 - Добавить обязательные буквы\n" +
                "3 - Добавить отсутсвующие буквы\n" +
                "4 - Показать известную информацию\n" +
                "5 - Указать одну букву на своем месте\n" +
                "6 - Указать одну букву не на своем месте\n" +
                "0 - Выход");
    }

    private void chooseWordLength() {
        System.out.println("Введите требуемую длину слова:");
        try {
            builder = new RegexBuilder(sc.nextInt());
        } catch (InputMismatchException e) {
            System.out.println("Должно быть введено целое число");
            chooseWordLength();
        }
    }
}
