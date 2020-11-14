package com.urfu.telegrambot.botapi;

import com.urfu.chadnovelengine.backendapi.Content;
import com.urfu.chadnovelengine.backendapi.IO;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import java.util.ArrayList;
import java.util.List;

public class TelegramIO extends IO {
    private SendMessage sendMessage;
    private String currentUserAnswer;
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
        return currentUserAnswer;
    }

    @Override
    public int getAnswerIndex(String answer, String[] answers) {
        for (var i = 0; i < answers.length; ++i) {
            if (answer.equals(answers[i])) {
                return i;
            }
        }

        return -1;
    }

    @Override
    protected void trySendContent(Content content) {
        printMessage("name: " + content.name + ", type: " + content.contentType);
    }

    public void setUserAnswer(String answer) {
        currentUserAnswer = answer;
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

}
