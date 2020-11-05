package com.urfu.textnovelengine;

import com.urfu.textnovelengine.backendapi.IO;

import java.util.ArrayList;
import java.util.List;

public class TestIO implements IO {
    private String[] repliesFromUser;
    private List<String> talkerMessages;
    private int currentReply;

    public TestIO(String[] testAnswers) {
        this.repliesFromUser = testAnswers;
        this.talkerMessages = new ArrayList<>();
        currentReply = 0;
    }

    @Override
    public void printMessage(String message) {
        talkerMessages.add(message);
    }

    @Override
    public void printPossibleAnswers(String[] answers) {
        for (var i = 0; i < answers.length; ++i)
            talkerMessages.add(answers[i]);
    }

    public List<String> getTalkerMessages() {
        return talkerMessages;
    }

    @Override
    public String getUserAnswer() {
        return repliesFromUser[currentReply++];
    }
}
