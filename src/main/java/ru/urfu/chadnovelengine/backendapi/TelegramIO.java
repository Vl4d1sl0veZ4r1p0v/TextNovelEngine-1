package ru.urfu.chadnovelengine.backendapi;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface TelegramIO {

    public void setMessage(String message);
    public void setPossibleReplies(String[] answers);
    public SendMessage makeMessage();
}

