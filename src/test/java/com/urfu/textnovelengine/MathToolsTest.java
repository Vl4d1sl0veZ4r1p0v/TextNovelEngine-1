package com.urfu.textnovelengine;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MathToolsTest {
    private final int QuestionsAmount = 3;

    @Test
    void isValidAnswerNotANumber() {
        assertFalse(MathTools.isValidAnswer("ABVC", QuestionsAmount));
        assertFalse(MathTools.isValidAnswer("12efqef", QuestionsAmount));
        assertFalse(MathTools.isValidAnswer("9iq", QuestionsAmount));
        assertFalse(MathTools.isValidAnswer("    ", QuestionsAmount));
    }

    @Test
    void isValidAnswerEmptyString() {
        assertFalse(MathTools.isValidAnswer("", QuestionsAmount));
    }

    @Test
    void isValidAnswerNull() {
        assertFalse(MathTools.isValidAnswer(null, QuestionsAmount));
    }

    @Test
    void isValidAnswerLessThanOne() {
        assertFalse(MathTools.isValidAnswer("-100", QuestionsAmount));
        assertFalse(MathTools.isValidAnswer("-1", QuestionsAmount));
        assertFalse(MathTools.isValidAnswer("0", QuestionsAmount));
    }

    @Test
    void isValidAnswerMoreThanQuestionsAmount() {
        assertFalse(MathTools.isValidAnswer(String.valueOf(QuestionsAmount + 1), QuestionsAmount));
        assertFalse(MathTools.isValidAnswer(String.valueOf(QuestionsAmount + 100), QuestionsAmount));
    }

    @Test
    void isValidAnswerCorrect() {
        for (var i = 1; i <= QuestionsAmount; ++i) {
            assertTrue(MathTools.isValidAnswer(String.valueOf(i), QuestionsAmount));
        }
    }

    @Test
    void isValidAnswerNotAnInt() {
        assertFalse(MathTools.isValidAnswer(String.valueOf(0.9999f), QuestionsAmount));
        for (var i = 1; i <= QuestionsAmount; ++i) {
            assertFalse(MathTools.isValidAnswer(String.valueOf(i + 0.01f), QuestionsAmount));
        }
    }

    @Test
    void isIntegerCorrect() {
        assertTrue(MathTools.isInteger("12"));
        assertTrue(MathTools.isInteger("-12"));
        assertTrue(MathTools.isInteger("0"));
    }

    @Test
    void isIntegerNotANumber() {
        assertFalse(MathTools.isInteger("ABVC"));
        assertFalse(MathTools.isInteger("12efqef"));
        assertFalse(MathTools.isInteger("9iq"));
        assertFalse(MathTools.isInteger("    "));
    }

    @Test
    void isIntegerEmptyString() {
        assertFalse(MathTools.isInteger(""));
    }

    @Test
    void isIntegerNull() {
        assertFalse(MathTools.isInteger(null));
    }

    @Test
    void isIntegerNotAnInteger() {
        assertFalse(MathTools.isInteger("0.5"));
        assertFalse(MathTools.isInteger("-0.5"));
        assertFalse(MathTools.isInteger("-0.0"));
    }
}
