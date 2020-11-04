package com.urfu.chadnovelengine;


public class MathTools {

    public static boolean isValidAnswer(String answer, int answersAmount) {
        if (isInteger(answer)) {
            var answerIndex = Integer.parseInt(answer);
            return answerIndex > 0 && answerIndex < answersAmount + 1;
        }
        return false;
    }

    public static int getAnswerIndex(String answer, String[] availableAnswers) {
        for (var i = 0; i < availableAnswers.length; ++i) {
            if (answer.equals(availableAnswers[i])) {
                return i;
            }
        }

        return -1;
    }

    public static boolean isInteger(String line) {
        if (line == null || line.length() == 0) {
            return false;
        }
        var firstChar = line.charAt(0);
        if (!(firstChar == '-' || Character.isDigit(firstChar))) {
            return false;
        }

        for (var i = 1; i < line.length(); i++) {
            if (!Character.isDigit(line.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    public static boolean contains(String value, String[] container) {
        for (var guessValue : container) {
            if (guessValue.equals(value)) {
                return true;
            }
        }

        return false;
    }

}
