package com.urfu.telegrambot.botapi;

import com.urfu.chadnovelengine.backendapi.IO;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class TelegramIO implements IO {
    private SendMessage sendMessage;
    private String CurrentUserAnswer;
    private ArrayList<String> messagesList;

    public TelegramIO() {
        this.sendMessage = new SendMessage();
        messagesList = new ArrayList<>();
    }

    public SendMessage makeMessage() {
        sendMessage.setText(String.join("\n", messagesList));
        return sendMessage;
    }

    @Override
    public void printMessage(String message) {
        messagesList.add(message);
    }

    @Override
    public void printPossibleAnswers(String[] answers) {
        setPossibleReplies(answers);
    }

    @Override
    public void printExistingScriptsNames(String[] scriptsNames) {
        setPossibleReplies(scriptsNames);
    }

    @Override
    public String getUserAnswer() {
        return CurrentUserAnswer;
    }

    public void setUserAnswer(String answer) {
        CurrentUserAnswer = answer;
    }

    private void setPossibleReplies(String[] answers) {
        final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();

        for (String answer : answers) {
            KeyboardRow row = new KeyboardRow();
            KeyboardButton button = new KeyboardButton();
            button.setText(answer);
            row.add(button);
            keyboard.add(row);
        }
        replyKeyboardMarkup.setKeyboard(keyboard);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
    }

    public void ClearButtons() {

    }
}
