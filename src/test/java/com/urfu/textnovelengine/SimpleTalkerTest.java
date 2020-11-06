package com.urfu.textnovelengine;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SimpleTalkerTest {
    SimpleTalker talker;

    @BeforeEach
    void init() {
        talker = new SimpleTalker("Test", "Test?");
    }

    @Test
    void talkTest() {
        assertEquals("Test: Hey", talker.talk("Hey"));
    }

    @Test
    void wrongInputReactionTest() {
        assertEquals("Test: Test?", talker.wrongInputReaction());
    }
}
