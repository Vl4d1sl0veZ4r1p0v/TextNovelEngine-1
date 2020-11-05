package com.urfu.telegrambot;

import com.urfu.chadnovelengine.Backend;
import com.urfu.telegrambot.botapi.TelegramIO;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

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
            chadNovelEngineBackend.UpdateUser(userID, io);

            var replyMessage = io.makeMessage(chatID);

            return replyMessage;
        } catch (Exception ex) {
            log.error(ex.toString());
        }

        return null;
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
}
