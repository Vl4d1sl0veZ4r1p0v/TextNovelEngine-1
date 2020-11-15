package com.urfu.telegrambot;

import com.urfu.chadnovelengine.Backend;
import com.urfu.telegrambot.botapi.TelegramIO;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ResourceUtils;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.*;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

@Slf4j
public class ChadNovelEngineTelegramBot extends TelegramWebhookBot {
    private String webHookPath;
    private String botToken;
    private String botUsername;
    private final Backend chadNovelEngineBackend;

    public ChadNovelEngineTelegramBot(DefaultBotOptions botOptions) throws IOException {
        super(botOptions);
        chadNovelEngineBackend = new Backend();
    }

    @Synchronized
    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        Message message = update.getMessage();
        if (message == null || !message.hasText()) {
            return null;
        }

        var userName = message.getFrom().getUserName();
        var userID = message.getFrom().getId();
        var chatID = message.getChatId();
        var messageText = message.getText();

        log.info("New message from User: {}, userId: {}, chatId: {}, with text: {}",
                userName, userID, chatID, messageText);

        try {
            var io = new TelegramIO();
            io.setUserAnswer(messageText);
            chadNovelEngineBackend.updateUser(userID, io);
            var messages = io.getMessages();

            for (var i = 0; i < messages.size() - 1; ++i) {
                executeMessage(messages.get(i), chatID);
            }

            var buttons = io.getButtons();
            var m = messages.get(messages.size() - 1);
            switch (m.messageType) {
                case IMAGE -> execute(sendImage(chatID, m.content).setReplyMarkup(buttons));
                case MUSIC -> execute(sendMusic(chatID, m.content).setReplyMarkup(buttons));
                case VIDEO -> execute(sendVideo(chatID, m.content).setReplyMarkup(buttons));
                case DOCUMENT -> execute(sendDocument(chatID, m.content).setReplyMarkup(buttons));
                default -> execute(sendText(chatID, m.content).setReplyMarkup(buttons));
            }  // It's a copy-paste, but it's the best you can do

        } catch (Exception ex) {
            log.error(ex.toString());
        }

        return null;
    }

    private void executeMessage(com.urfu.chadnovelengine.backendapi.Message m, long chatID)
            throws FileNotFoundException, TelegramApiException {
        switch (m.messageType) {
            case IMAGE -> execute(sendImage(chatID, m.content));
            case MUSIC -> execute(sendMusic(chatID, m.content));
            case VIDEO -> execute(sendVideo(chatID, m.content));
            case DOCUMENT -> execute(sendDocument(chatID, m.content));
            default -> execute(sendText(chatID, m.content));
        }
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public String getBotPath() {
        return webHookPath;
    }

    public void setWebHookPath(String webHookPath) {
        this.webHookPath = webHookPath;
    }

    public void setBotToken(String botToken) {
        this.botToken = botToken;
    }

    public void setBotUsername(String botUsername) {
        this.botUsername = botUsername;
    }

    public SendMessage sendText(long chatId, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(text);
        sendMessage.setChatId(chatId);

        return sendMessage;
    }

    public SendPhoto sendImage(long chatId, String imagePath) throws FileNotFoundException {
        File image = ResourceUtils.getFile("classpath:" + imagePath);
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(chatId);
        sendPhoto.setPhoto(image);

        return sendPhoto;
    }

    public SendAudio sendMusic(long chatId, String musicPath) throws FileNotFoundException {
        File audio = ResourceUtils.getFile("classpath:" + musicPath);
        SendAudio sendAudio = new SendAudio();
        sendAudio.setChatId(chatId);
        sendAudio.setAudio(audio);

        return sendAudio;
    }

    public SendVideo sendVideo(long chatId, String videoFilePath) throws FileNotFoundException {
        File video = ResourceUtils.getFile("classpath:" + videoFilePath);
        SendVideo sendVideo = new SendVideo();
        sendVideo.setChatId(chatId);
        sendVideo.setVideo(video);

        return sendVideo;
    }

    public SendDocument sendDocument(long chatId, String docFilePath) throws FileNotFoundException {
        File file = ResourceUtils.getFile("classpath:" + docFilePath);
        SendDocument sendDocument = new SendDocument();
        sendDocument.setChatId(chatId);
        sendDocument.setDocument(file);

        return sendDocument;
    }

}
