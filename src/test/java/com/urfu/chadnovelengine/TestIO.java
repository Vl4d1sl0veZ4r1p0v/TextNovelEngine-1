package com.urfu.chadnovelengine;

import com.urfu.chadnovelengine.backendapi.IO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestIO extends IO {
    private String[] repliesFromUser;
    private List<String> talkerMessages;
    private int currentReply;

    public TestIO(String[] testAnswers) {
        repliesFromUser = testAnswers;
        talkerMessages = new ArrayList<>();
        currentReply = 0;
    }

    @Override
    public void printMessage(String message) {
        talkerMessages.add(message);
    }

    @Override
    public void printPossibleAnswers(String[] answers) {
        talkerMessages.addAll(Arrays.asList(answers));
    }

    @Override
    public void printExistingScriptsNames(String[] scriptsNames) {

    }

    public List<String> getTalkerMessages() {
        return talkerMessages;
    }

    @Override
    public String getUserAnswer() {
        return repliesFromUser[currentReply++];
    }

    @Override
    public int getAnswerIndex(String answer, String[] answers) {
        return MathTools.isValidAnswer(answer, answers.length) ? Integer.parseInt(answer) - 1 : -1;
    }

    @Override
    protected void trySendContent(String contentName) {
    }
}
