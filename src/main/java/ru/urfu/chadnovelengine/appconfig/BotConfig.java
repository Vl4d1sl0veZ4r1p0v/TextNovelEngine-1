package ru.urfu.chadnovelengine.appconfig;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.ApiContext;
import ru.urfu.chadnovelengine.ChadNovelEngineTelegramBot;
import ru.urfu.chadnovelengine.botapi.TelegramFacade;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "telegrambot")
public class BotConfig {
    private String webHookPath;
    private String botUsername;
    private String botToken;

    @Bean
    public ChadNovelEngineTelegramBot textNovelEngineTelegramBot(TelegramFacade telegramFacade){
        DefaultBotOptions options = ApiContext.getInstance(DefaultBotOptions.class);

        ChadNovelEngineTelegramBot chadNovelEngineTelegramBot = new ChadNovelEngineTelegramBot(options, telegramFacade);
        chadNovelEngineTelegramBot.setBotToken(botToken);
        chadNovelEngineTelegramBot.setBotUsername(botUsername);
        chadNovelEngineTelegramBot.setWebHookPath(webHookPath);

        return chadNovelEngineTelegramBot;
    }
}
