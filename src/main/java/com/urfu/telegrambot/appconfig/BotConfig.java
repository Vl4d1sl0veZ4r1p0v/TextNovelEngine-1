package com.urfu.telegrambot.appconfig;

import com.urfu.telegrambot.CharacterTelegramBot;
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
@ConfigurationProperties(prefix = "telegrambot")
public class BotConfig {
    private String webHookPath;
    private String botUsername;
    private String botToken;

    private String botTokenCharacter;
    private String botUsernameCharacter;

    @Bean
    public ChadNovelEngineTelegramBot textNovelEngineTelegramBot() throws IOException {
        DefaultBotOptions options = ApiContext.getInstance(DefaultBotOptions.class);

        ChadNovelEngineTelegramBot chadNovelEngineTelegramBot = new ChadNovelEngineTelegramBot(options);
        chadNovelEngineTelegramBot.setBotToken(botToken);
        chadNovelEngineTelegramBot.setBotUsername(botUsername);
        chadNovelEngineTelegramBot.setWebHookPath(webHookPath);

        return chadNovelEngineTelegramBot;
    }

    @Bean
    public CharacterTelegramBot characterTelegramBot(){
        DefaultBotOptions options = ApiContext.getInstance(DefaultBotOptions.class);

        CharacterTelegramBot characterTelegramBot = new CharacterTelegramBot(options);
        characterTelegramBot.setBotToken(botTokenCharacter);
        characterTelegramBot.setWebHookPath(webHookPath);
        characterTelegramBot.setBotUsername(botUsernameCharacter);
        return characterTelegramBot;
    }
}
