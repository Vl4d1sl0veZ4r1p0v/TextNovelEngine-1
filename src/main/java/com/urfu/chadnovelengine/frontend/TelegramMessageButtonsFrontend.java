package com.urfu.chadnovelengine.frontend;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import com.urfu.chadnovelengine.backendapi.TelegramIO;

import java.util.ArrayList;
import java.util.List;

public class TelegramMessageButtonsFrontend implements TelegramIO {
    private SendMessage sendMessage;

    public TelegramMessageButtonsFrontend() {
        this.sendMessage = new SendMessage();
    }


    @Override
    public void setMessage(String message) {
        sendMessage.setText(message);
    }

    @Override
    public void setPossibleReplies(String[] answers) {
        final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();
                
        for (int i = 0; i < answers.length; ++i){
            KeyboardRow row = new KeyboardRow();
            KeyboardButton button = new KeyboardButton();
            button.setText(answers[i]);
            row.add(button);
            keyboard.add(row);
        }
        replyKeyboardMarkup.setKeyboard(keyboard);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
    }

    @Override
    public SendMessage makeMessage() {
        return sendMessage;
    }
}
