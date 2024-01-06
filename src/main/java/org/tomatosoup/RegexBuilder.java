package org.tomatosoup;

import java.util.*;

public class RegexBuilder {
    private final String[] regex;
    private final Set<Character> mandatoryLetters = new HashSet<>();
    private final Set<Character> missingLetters = new HashSet<>();
    private final Map<Integer, List<Character>> wrongPositionLetters = new HashMap<>();

    public RegexBuilder(int wordLength) {
        regex = new String[wordLength];
        for (int i = 0; i < wordLength; i++) {
            regex[i] = ".";
        }
    }

    public void setLetter(int pos, char letter) {
        regex[pos] = String.valueOf(letter);
    }

    public void addMandatoryLetter(char c) {
        mandatoryLetters.add(c);
    }

    public void addMissingLetter(char c) {
        missingLetters.add(c);
    }

    public void setMultipleLetters(String word) {
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (Character.isLetter(c)) {
                setLetter(i, c);
            }
        }
    }

    public Set<Character> getMandatoryLetters() {
        return mandatoryLetters;
    }

    public Set<Character> getMissingLetters() {
        return missingLetters;
    }

    public String getRegex() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < regex.length; i++) {
            char c = regex[i].charAt(0);
            result.append(c != '.' ? c : getPossibleLettersForPosition(i));
        }
        return result.toString();
    }

    private String getPossibleLetters() {
        if (missingLetters.size() > 0) {
            StringBuilder result = new StringBuilder();
            result.append("[^");
            for (char c : missingLetters) {
                result.append(c);
            }
            result.append("]");
            return result.toString();
        } else {
            return ".";
        }
    }

    private String getPossibleLettersForPosition(int index) {
        if (missingLetters.size() > 0 || wrongPositionLetters.containsKey(index)) {
            StringBuilder result = new StringBuilder();
            result.append("[^");
            for (char c : missingLetters) {
                result.append(c);
            }
            wrongPositionLetters.computeIfPresent(index, (key, val) -> {
                for (char ch : val) {
                    result.append(ch);
                }
                return val;
            });
            result.append("]");
            return result.toString();
        } else {
            return ".";
        }
    }

    public void setLetterInWrongPlace(int i, char c) {
        addMandatoryLetter(c);
        List<Character> chars = wrongPositionLetters.getOrDefault(i, new ArrayList<>());
        chars.add(c);
        wrongPositionLetters.put(i, chars);
    }

    public void setMultipleWrongLetters(String word) {
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (Character.isLetter(c)) {
                setLetterInWrongPlace(i, c);
            }
        }
    }
}
