package com.urfu.textnovelengine;

import com.urfu.textnovelengine.backendapi.Talker;

public class DialogNode {
    private final Talker talker;
    private final String talkerMessage;
    private String[] answers;
    private int[] responses;

    public DialogNode(Talker talker, String talkerMessage) {
        this.talker = talker;
        this.talkerMessage = talkerMessage;
    }

    public Talker getTalker() {
        return talker;
    }

    public String getTalkerMessage() {
        return talkerMessage;
    }

    public String[] getAnswers() {
        return answers;
    }

    public void setAnswers(String[] answers) {
        this.answers = answers;
    }

    public int[] getResponses() {
        return responses;
    }

    public void setResponses(int[] responses) {
        this.responses = responses;
    }
}
