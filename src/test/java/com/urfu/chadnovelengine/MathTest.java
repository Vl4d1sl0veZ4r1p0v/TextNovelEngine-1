package com.urfu.chadnovelengine;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MathTest {
    private final int questionsAmount = 3;

    @Test
    void isValidAnswerNotANumber() {
        Assertions.assertFalse(MathTools.isValidAnswer("ABVC", questionsAmount));
        Assertions.assertFalse(MathTools.isValidAnswer("12efqef", questionsAmount));
        Assertions.assertFalse(MathTools.isValidAnswer("9iq", questionsAmount));
        Assertions.assertFalse(MathTools.isValidAnswer("    ", questionsAmount));
    }

    @Test
    void isValidAnswerEmptyString() {
        Assertions.assertFalse(MathTools.isValidAnswer("", questionsAmount));
    }

    @Test
    void isValidAnswerNull() {
        Assertions.assertFalse(MathTools.isValidAnswer(null, questionsAmount));
    }

    @Test
    void isValidAnswerLessThanOne() {
        Assertions.assertFalse(MathTools.isValidAnswer("-100", questionsAmount));
        Assertions.assertFalse(MathTools.isValidAnswer("-1", questionsAmount));
        Assertions.assertFalse(MathTools.isValidAnswer("0", questionsAmount));
    }

    @Test
    void isValidAnswerMoreThanQuestionsAmount() {
        Assertions.assertFalse(
                MathTools.isValidAnswer(String.valueOf(questionsAmount + 1), questionsAmount));
        Assertions.assertFalse(
                MathTools.isValidAnswer(String.valueOf(questionsAmount + 100), questionsAmount));
    }

    @Test
    void isValidAnswerCorrect() {
        for (var i = 1; i <= questionsAmount; ++i) {
            Assertions.assertTrue(MathTools.isValidAnswer(String.valueOf(i), questionsAmount));
        }
    }

    @Test
    void isValidAnswerNotAnInt() {
        Assertions.assertFalse(MathTools.isValidAnswer(String.valueOf(0.9999f), questionsAmount));
        for (var i = 1; i <= questionsAmount; ++i) {
            Assertions.assertFalse(
                    MathTools.isValidAnswer(String.valueOf(i + 0.01f), questionsAmount));
        }
    }

    @Test
    void isIntegerCorrect() {
        Assertions.assertTrue(MathTools.isInteger("12"));
        Assertions.assertTrue(MathTools.isInteger("-12"));
        Assertions.assertTrue(MathTools.isInteger("0"));
    }

    @Test
    void isIntegerNotANumber() {
        Assertions.assertFalse(MathTools.isInteger("ABVC"));
        Assertions.assertFalse(MathTools.isInteger("12efqef"));
        Assertions.assertFalse(MathTools.isInteger("9iq"));
        Assertions.assertFalse(MathTools.isInteger("    "));
    }

    @Test
    void isIntegerEmptyString() {
        Assertions.assertFalse(MathTools.isInteger(""));
    }

    @Test
    void isIntegerNull() {
        Assertions.assertFalse(MathTools.isInteger(null));
    }

    @Test
    void isIntegerNotAnInteger() {
        Assertions.assertFalse(MathTools.isInteger("0.5"));
        Assertions.assertFalse(MathTools.isInteger("-0.5"));
        Assertions.assertFalse(MathTools.isInteger("-0.0"));
    }
}
