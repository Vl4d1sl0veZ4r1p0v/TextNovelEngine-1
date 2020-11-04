package com.urfu.textnovelengine.backendapi;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    User user;

    @BeforeEach
    void init(){
        user = new User(123);
    }

    @Test
    void testSetterGetterCurrentScript() {
        String expected = "test";
        user.setNewScript(expected);
        assertEquals(expected, user.getCurrentScript());
    }

    @Test
    void clearCurrentScriptTest() {
        String expected = "test";
        user.setNewScript(expected);
        user.clearCurrentScript();
        assertNull(user.getCurrentScript());
    }

    @Test
    void isCurrentScriptExist() {
        assertFalse(user.isCurrentScriptExist());
    }

    @Test
    void testGetterSetterCurrentNodeIndex() {
        int expected = 1;
        user.setCurrentNodeIndex(expected);
        assertEquals(expected, user.getCurrentNodeIndex());
    }
}
