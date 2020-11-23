package com.urfu.telegrambot.appconfig;

import com.urfu.vkbot.ChadNovelEngineVkBot;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.ApiContext;
import com.urfu.telegrambot.ChadNovelEngineTelegramBot;

import java.io.IOException;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "config")
public class BotConfig {
    private String telegrambotwebHookPath;
    private String telegrambotuserName;
    private String telegrambotbotToken;

    @Bean
    public ChadNovelEngineTelegramBot textNovelEngineTelegramBot() throws IOException {
        DefaultBotOptions options = ApiContext.getInstance(DefaultBotOptions.class);

        ChadNovelEngineTelegramBot chadNovelEngineTelegramBot = new ChadNovelEngineTelegramBot(options);
        chadNovelEngineTelegramBot.setBotToken(telegrambotbotToken);
        chadNovelEngineTelegramBot.setBotUsername(telegrambotuserName);
        chadNovelEngineTelegramBot.setWebHookPath(telegrambotwebHookPath);

        return chadNovelEngineTelegramBot;
    }
}
