package com.urfu.chadnovelengine;

import com.urfu.chadnovelengine.backendapi.Message;

import java.util.ArrayList;

public class DialogNode {
    private final ArrayList<Message> messages;
    private Message wrongInputReaction;
    private String[] answers;
    private int[] responses;

    public DialogNode(ArrayList<Message> messages) {
        if (messages.size() == 0) throw new IllegalArgumentException(
                "Dialog Nodes should have at least one message of any type");
        this.messages = messages;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public Message getWrongInputReaction() {
        return wrongInputReaction;
    }

    public void setWrongInputReaction(Message wrongInputReaction) {
        this.wrongInputReaction = wrongInputReaction;
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
