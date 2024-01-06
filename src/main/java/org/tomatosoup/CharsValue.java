package org.tomatosoup;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class CharsValue {
    public final static Map<Integer, Map<Character, Integer>> valuesWithPosition = new HashMap<>();
    public static final Path path = Paths.get("src/main/resources/singular.txt");
    public final static Map<Character, Integer> values = new HashMap<>();

    public static void calculateCharsWithPosition(int length) {
        try (Stream<String> lines = Files.lines(path)) {
            lines.filter(w -> w.length() == length)
                    .forEach(w -> {
                        for (int i = 0; i < w.length(); i++) {
                            Map<Character, Integer> charValue = valuesWithPosition.getOrDefault(i, new HashMap<>());
                            if (charValue.computeIfPresent(w.charAt(i), (key, val) -> val + 1) == null) {
                                charValue.put(w.charAt(i), 1);
                            }
                            valuesWithPosition.put(i, charValue);
                        }
                    });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void calculateChars(int length) {
        try (Stream<String> lines = Files.lines(path)) {
            lines.filter(w -> w.length() == length)
                    .forEach(w -> {
                        for (int i = 0; i < w.length(); i++) {
                            if (values.computeIfPresent(w.charAt(i), (key, val) -> val + 1) == null) {
                                values.put(w.charAt(i), 1);
                            }
                        }
                    });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void printMostValuableWithPosition(int length) {
        try (Stream<String> lines = Files.lines(path)) {
            lines.filter(w -> w.length() == length)
                    .sorted(Comparator.comparingInt(w -> {
                        int result = 0;

                        for (int i = 0; i < w.length(); i++) {
                            final char ch = w.charAt(i);
                            result -= (valuesWithPosition.get(i).getOrDefault(w.charAt(i), 0)) /
                                    w.chars().filter(c -> c == ch).count();
                        }
                        return result;
                    }))
                    .limit(10)
                    .forEach(System.out::println);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void printMostValuable(int length) {
        try (Stream<String> lines = Files.lines(path)) {
            lines.filter(w -> w.length() == length)
                    .sorted(Comparator.comparingInt(w -> -w.chars()
                            .distinct()
                            .map(c -> values.get((char) c)).sum()))
                    .limit(10)
                    .forEach(System.out::println);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
