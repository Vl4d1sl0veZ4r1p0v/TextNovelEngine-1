package com.urfu.textnovelengine.parsers;

import com.urfu.textnovelengine.DialogNode;
import com.urfu.textnovelengine.Script;
import java.io.IOException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScriptParserTest {

    @Test
    void scriptCreationTest() throws IOException {
        String fileName = "test_dialog";
        Script script = ScriptParser.parse(fileName);
        DialogNode first = script.getNode(0);
        DialogNode second = script.getNode(1);
        DialogNode third = script.getNode(2);
        DialogNode last = script.getNode(3);
        assertAll(() -> assertEquals("HELLO", first.getMessage()),
                () -> assertArrayEquals(new int[]{1, 2}, first.getResponses()),
                () -> assertArrayEquals(new String[]{"YES", "NO"}, first.getAnswers())
        );
        assertAll(() -> assertEquals("GO", second.getMessage()),
                () -> assertArrayEquals(new int[]{3}, second.getResponses()),
                () -> assertArrayEquals(new String[]{"OKAY"}, second.getAnswers())
        );
        assertAll(() -> assertEquals("BYE", third.getMessage()),
                () -> assertArrayEquals(new int[]{3}, third.getResponses()),
                () -> assertArrayEquals(new String[]{"GOODBYE"}, third.getAnswers())
        );
        assertAll(() -> assertEquals("THE END", last.getMessage()),
                () -> assertNull(last.getAnswers()));
    }


}
