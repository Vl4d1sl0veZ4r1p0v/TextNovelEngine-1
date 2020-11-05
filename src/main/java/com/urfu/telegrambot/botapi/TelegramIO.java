package com.urfu.telegrambot.botapi;

import com.urfu.chadnovelengine.backendapi.IO;
import com.urfu.telegrambot.ChadNovelEngineTelegramBot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@PropertySource("classpath:application.properties")
public class TelegramIO implements IO {
    private SendMessage sendMessage;
    private String CurrentUserAnswer;
    private ArrayList<String> messagesList;

    public TelegramIO() {
        this.sendMessage = new SendMessage();
        messagesList = new ArrayList<>();
    }

    private static String encodeValue(String value) {
        try {
            return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex.getCause());
        }
    }

    public SendMessage makeMessage(long chatID) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            for (var i = 0; i < messagesList.size() - 1; ++i) {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(new URI("https://api.telegram.org/bot1447217539:AAFCHgFAyL7K9qNkMh9xZgdj_r8kICWAPyk/sendmessage?chat_id=" + chatID + "&text=" + encodeValue(messagesList.get(i))))
                        .GET()
                        .build();
                HttpResponse<String> response = client
                        .send(request, HttpResponse.BodyHandlers.ofString());
                log.info(response.body());
            }
        } catch (URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return sendMessage
                .setText(messagesList.get(messagesList.size() - 1))
                .setChatId(chatID);
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

    @Override
    public int getAnswerIndex(String answer, String[] answers) {
        for (var i = 0; i < answers.length; ++i) {
            if (answer.equals(answers[i])) {
                return i;
            }
        }

        return -1;
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
