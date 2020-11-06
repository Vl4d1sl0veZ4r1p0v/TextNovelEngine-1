package com.urfu.textnovelengine;

import com.urfu.textnovelengine.backendapi.IO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestIO implements IO {
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

    public List<String> getTalkerMessages() {
        return talkerMessages;
    }

    @Override
    public String getUserAnswer() {
        return repliesFromUser[currentReply++];
    }
}
