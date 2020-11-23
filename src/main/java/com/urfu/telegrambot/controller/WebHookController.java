package com.urfu.telegrambot.controller;

import com.urfu.telegrambot.ChadNovelEngineTelegramBot;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@RestController
public class WebHookController {
    private final ChadNovelEngineTelegramBot chadNovelEngineTelegramBot;



    public WebHookController(ChadNovelEngineTelegramBot chadNovelEngineTelegramBot) {
        this.chadNovelEngineTelegramBot = chadNovelEngineTelegramBot;
    }

    @RequestMapping(value="/Telegram", method = RequestMethod.POST)
    public BotApiMethod<?> onUpdateReceived(@RequestBody Update update){
        return chadNovelEngineTelegramBot.onWebhookUpdateReceived(update);
    }

}
