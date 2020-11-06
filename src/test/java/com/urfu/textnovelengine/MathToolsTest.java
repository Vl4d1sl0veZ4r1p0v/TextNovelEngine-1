package com.urfu.textnovelengine;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MathToolsTest {
    private final int QuestionsAmount = 3;

    @Test
    void isValidAnswerNotANumber() {
        Assertions.assertFalse(MathTools.isValidAnswer("ABVC", QuestionsAmount));
        Assertions.assertFalse(MathTools.isValidAnswer("12efqef", QuestionsAmount));
        Assertions.assertFalse(MathTools.isValidAnswer("9iq", QuestionsAmount));
        Assertions.assertFalse(MathTools.isValidAnswer("    ", QuestionsAmount));
    }

    @Test
    void isValidAnswerEmptyString() {
        Assertions.assertFalse(MathTools.isValidAnswer("", QuestionsAmount));
    }

    @Test
    void isValidAnswerNull() {
        Assertions.assertFalse(MathTools.isValidAnswer(null, QuestionsAmount));
    }

    @Test
    void isValidAnswerLessThanOne() {
        Assertions.assertFalse(MathTools.isValidAnswer("-100", QuestionsAmount));
        Assertions.assertFalse(MathTools.isValidAnswer("-1", QuestionsAmount));
        Assertions.assertFalse(MathTools.isValidAnswer("0", QuestionsAmount));
    }

    @Test
    void isValidAnswerMoreThanQuestionsAmount() {
        Assertions.assertFalse(MathTools.isValidAnswer(String.valueOf(QuestionsAmount + 1), QuestionsAmount));
        Assertions.assertFalse(
                MathTools.isValidAnswer(String.valueOf(QuestionsAmount + 100), QuestionsAmount));
    }

    @Test
    void isValidAnswerCorrect() {
        for (var i = 1; i <= QuestionsAmount; ++i) {
            Assertions.assertTrue(MathTools.isValidAnswer(String.valueOf(i), QuestionsAmount));
        }
    }

    @Test
    void isValidAnswerNotAnInt() {
        Assertions.assertFalse(MathTools.isValidAnswer(String.valueOf(0.9999f), QuestionsAmount));
        for (var i = 1; i <= QuestionsAmount; ++i) {
            Assertions.assertFalse(MathTools.isValidAnswer(String.valueOf(i + 0.01f), QuestionsAmount));
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
