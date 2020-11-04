package ru.urfu.chadnovelengine;


public class MathTools {

    public static boolean isValidAnswer(String answer, int answersAmount) {
        if (isInteger(answer)) {
            var answerIndex = Integer.parseInt(answer);
            return answerIndex > 0 && answerIndex < answersAmount + 1;
        }
        return false;
    }

    public static boolean isInteger(String line) {
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

}
