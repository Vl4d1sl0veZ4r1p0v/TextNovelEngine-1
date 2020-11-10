package com.urfu.chadnovelengine.backendapi;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserTest {

    private User user;

    @BeforeEach
    void init(){
        user = new User(123);
    }

    @Test
    void testSetterGetterCurrentScript() {
        String expected = "test";
        user.setNewScript(expected);
        Assertions.assertEquals(expected, user.getCurrentScript());
    }

    @Test
    void clearCurrentScriptTest() {
        String expected = "test";
        user.setNewScript(expected);
        user.clearCurrentScript();
        Assertions.assertNull(user.getCurrentScript());
    }

    @Test
    void isCurrentScriptExist() {
        Assertions.assertFalse(user.hasRunningScript());
    }

    @Test
    void testGetterSetterCurrentNodeIndex() {
        int expected = 1;
        user.setCurrentNodeIndex(expected);
        Assertions.assertEquals(expected, user.getCurrentNodeIndex());
    }
}
