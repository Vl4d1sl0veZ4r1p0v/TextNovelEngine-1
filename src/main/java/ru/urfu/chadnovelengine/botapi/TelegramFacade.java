package ru.urfu.chadnovelengine.botapi;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import ru.urfu.chadnovelengine.Script;
import ru.urfu.chadnovelengine.frontend.TelegramMessageButtonsFrontend;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Data
@Component
@Slf4j
public class TelegramFacade {
    public TelegramFacade() {
    }

    private Script currentScript;

    public BotApiMethod<?> handleUpdate(Update update) throws IOException {
        SendMessage replyMessage = null;

        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            log.info("New message from User:{}, userId: {}, chatId: {},  with text: {}",
                    message.getFrom().getUserName(), message.getFrom().getId(), message.getChatId(), message.getText());
            replyMessage = handleInputMessage(message);
        }
        return replyMessage;
    }

    private SendMessage handleInputMessage(Message message) throws IOException {
        String inputMsg = message.getText();
        final long chatId = message.getChatId();
        final int userId = message.getFrom().getId();
        SendMessage replyMessage = new SendMessage();

        switch (inputMsg) {
            case "/start":
                replyMessage = new SendMessage(chatId, "Hui");
                ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
                replyKeyboardMarkup.setSelective(true);
                replyKeyboardMarkup.setResizeKeyboard(true);
                replyKeyboardMarkup.setOneTimeKeyboard(true);

                List<KeyboardRow> keyboard = new ArrayList<>();
                KeyboardRow row1 = new KeyboardRow();
                row1.add(new KeyboardButton("Помощь"));
                keyboard.add(row1);
                replyKeyboardMarkup.setKeyboard(keyboard);
                replyMessage.setReplyMarkup(replyKeyboardMarkup);
                break;
            case "/help":
                replyMessage = new SendMessage(chatId, "help message");
                break;
            case "/exit":
                replyMessage = new SendMessage(chatId, "bye!");
                break;
            default:
                TelegramMessageButtonsFrontend messageFrontend = new TelegramMessageButtonsFrontend();
                messageFrontend.setMessage("Привет, как дела?");
                String[] replies = new String[]{"1. Good", "2. Збс", "3. Все ок!"};
                messageFrontend.setPossibleReplies(replies);
                replyMessage = messageFrontend.makeMessage();
                replyMessage.setChatId(chatId);
                break;
        }
        return replyMessage;
    }



    private String getScriptNames(String scriptsFolder) {
        File f = new File(scriptsFolder);
        String[] pathNames = f.list();
        for (var i = 0; i < pathNames.length; ++i) {
            pathNames[i] = (i + 1) + " " + pathNames[i].replaceFirst("[.][^.]+$", "");
        }

        return String.join("\n", pathNames);
    }

    private String[] getScriptPaths(String scriptsFolder) {
        File f = new File(scriptsFolder);
        return f.list();
    }

}
