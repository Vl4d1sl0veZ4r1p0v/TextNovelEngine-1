package com.urfu.telegrambot;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

@Slf4j
public class CharacterTelegramBot extends TelegramWebhookBot {
    private String webHookPath;
    private String botToken;

    private ChadNovelEngineTelegramBot mainBot;

    public CharacterTelegramBot(DefaultBotOptions options) {
        super(options);
    }

    private String botUsername;

    public void pipeInteraction() throws IOException {
        RandomAccessFile pipe = new RandomAccessFile("./pipe", "rw");
        try{
            String echoText = "Hello world\n";
            pipe.write(echoText.getBytes());
            String echoRespose = pipe.readLine();
            System.out.println("Response: " + echoRespose);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            pipe.close();
        }
    }

    @Override
    public BotApiMethod onWebhookUpdateReceived(Update update) {
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
        return new SendMessage(chatID, "Hui");
    }

    @Override
    public String getBotUsername() {
        return botUsername;
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

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public String getBotPath() {
        return webHookPath;
    }
}
