package com.urfu.chadnovelengine;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SimpleTalkerTest {
    private SimpleTalker talker;

    @BeforeEach
    void init() {
        talker = new SimpleTalker("Test", "Test?");
    }

    @Test
    void talkTest() {
        Assertions.assertEquals("Test: Hey", talker.talk("Hey"));
    }

    @Test
    void wrongInputReactionTest() {
        Assertions.assertEquals("Test: Test?", talker.wrongInputReaction());
    }
}
