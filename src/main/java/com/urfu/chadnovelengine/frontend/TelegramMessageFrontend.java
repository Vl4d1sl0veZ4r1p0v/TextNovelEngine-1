package com.urfu.chadnovelengine.frontend;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import com.urfu.chadnovelengine.backendapi.TelegramIO;

public class TelegramMessageFrontend implements TelegramIO {
    private SendMessage sendMessage;
    private StringBuilder messageBuilder;

    public TelegramMessageFrontend() {
        this.sendMessage = new SendMessage();
        this.sendMessage.enableMarkdown(true);
        this.messageBuilder = new StringBuilder();
    }


    @Override
    public void setMessage(String message) {
        messageBuilder.append(message + "\n\n");
    }

    @Override
    public void setPossibleReplies(String[] replies) {
        messageBuilder.append("__Replies__: " + "\n\n");
        for (String reply :
                replies) {
            messageBuilder.append(reply + "\n");
        }
    }

    @Override
    public SendMessage makeMessage() {
        sendMessage.setText(messageBuilder.toString());
        return sendMessage;
    }

    //need to include into Facade
    //message.chatId
    //log.info("For user {} prepared message with text: {}", userName, message.getText());
}
