package com.urfu.textnovelengine.parsers;

import com.urfu.textnovelengine.DialogNode;
import com.urfu.textnovelengine.Script;
import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ScriptParserTest {

    @Test
    void scriptCreationTest() throws IOException {
        String fileName = "test_dialog";
        Script script = ScriptParser.parse(fileName);
        DialogNode first = script.getNode(0);
        DialogNode second = script.getNode(1);
        DialogNode third = script.getNode(2);
        DialogNode last = script.getNode(3);
        Assertions.assertAll(() -> Assertions.assertEquals("HELLO", first.getMessage()),
                () -> Assertions.assertArrayEquals(new int[]{1, 2}, first.getResponses()),
                () -> Assertions.assertArrayEquals(new String[]{"YES", "NO"}, first.getAnswers())
        );
        Assertions.assertAll(() -> Assertions.assertEquals("GO", second.getMessage()),
                () -> Assertions.assertArrayEquals(new int[]{3}, second.getResponses()),
                () -> Assertions.assertArrayEquals(new String[]{"OKAY"}, second.getAnswers())
        );
        Assertions.assertAll(() -> Assertions.assertEquals("BYE", third.getMessage()),
                () -> Assertions.assertArrayEquals(new int[]{3}, third.getResponses()),
                () -> Assertions.assertArrayEquals(new String[]{"GOODBYE"}, third.getAnswers())
        );
        Assertions.assertAll(() -> Assertions.assertEquals("THE END", last.getMessage()),
                () -> Assertions.assertNull(last.getAnswers()));
    }


}
