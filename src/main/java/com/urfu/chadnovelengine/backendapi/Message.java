package com.urfu.chadnovelengine.backendapi;

public class Message {
    public final String content;
    public final MessageType messageType;

    public Message(String content, MessageType messageType) {
        this.content = content;
        this.messageType = messageType;
    }
}
