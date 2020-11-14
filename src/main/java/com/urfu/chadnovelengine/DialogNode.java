package com.urfu.chadnovelengine;

import com.urfu.chadnovelengine.backendapi.Talker;

public class DialogNode {
    private final Talker talker;
    private final String talkerMessage;

    private String content;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
