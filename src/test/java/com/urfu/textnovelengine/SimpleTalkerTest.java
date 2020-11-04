package com.urfu.textnovelengine;

import com.urfu.textnovelengine.backendapi.Talker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
