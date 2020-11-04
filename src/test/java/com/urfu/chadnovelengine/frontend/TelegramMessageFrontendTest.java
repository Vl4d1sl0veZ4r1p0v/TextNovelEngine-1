package com.urfu.chadnovelengine.frontend;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("When running TelegramMessageFrontend")
class TelegramMessageFrontendTest {
    TelegramMessageFrontend telegramMessageFrontend;

    @BeforeEach
    void init(){
        telegramMessageFrontend = new TelegramMessageFrontend();
    }

    @Test
    @DisplayName("setMessage method")
    void setAMessageTest() {
        var message = "Test";
        telegramMessageFrontend.setMessage(message);
        var actual = telegramMessageFrontend.makeMessage();
        assertEquals(message + "\n\n", actual.getText(),
                "should set a message with '\\n\\n'");
    }

    @Test
    @DisplayName("makeMessage method without setMessage")
    void withoutSetMessageTest() {
        var actual = telegramMessageFrontend.makeMessage();
        assertEquals("", actual.getText(), "should return no text'");
    }

    @Test
    @DisplayName("setPossibleReplies method")
    void setPossibleRepliesTest() {
        var message = "Test";
        var replies = new String[]{"/1", "/2", "/3"};
        telegramMessageFrontend.setMessage(message);
        telegramMessageFrontend.setPossibleReplies(replies);
        var actual = telegramMessageFrontend.makeMessage();
        assertEquals(message + "\n\n" + "__Replies__: \n\n" + "/1\n/2\n/3\n",
                actual.getText(), "should set a message with '__Replies__: \\n\\n' " +
                        "and replies each in new line");
    }
}