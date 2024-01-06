package org.tomatosoup;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class RegexBuilder {
    private String[] regex;
    private final Set<Character> mandatoryLetters = new HashSet<>();
    private final Set<Character> missingLetters = new HashSet<>();
    private final Map<Integer, Character> wrongPositionLetters = new HashMap<>();
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
        for (int i = 0; i < regex.length; i++){
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
        if (missingLetters.size() > 0) {
            StringBuilder result = new StringBuilder();
            result.append("[^");
            for (char c : missingLetters) {
                result.append(c);
            }
            if (wrongPositionLetters.containsKey(index)){
                result.append(wrongPositionLetters.get(index));
            }
            result.append("]");
            return result.toString();
        } else {
            return ".";
        }
    }

    public void setLetterInWrongPlace(int i, char c) {
        addMandatoryLetter(c);
        wrongPositionLetters.put(i, c);
    }

    public void setMultipleWrongLetters(String word) {
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (Character.isLetter(c)) {
                addMandatoryLetter(c);
                wrongPositionLetters.put(i, c);
            }
        }
    }
}
