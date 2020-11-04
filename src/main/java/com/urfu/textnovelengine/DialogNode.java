package com.urfu.textnovelengine;

import com.urfu.textnovelengine.backendapi.Talker;

public class DialogNode {
    private Talker talker;
    private String message;
    private String[] answers;
    private int[] responses;

    public Talker getTalker() {
        return talker;
    }

    public void setTalker(Talker talker) {
        this.talker = talker;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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
