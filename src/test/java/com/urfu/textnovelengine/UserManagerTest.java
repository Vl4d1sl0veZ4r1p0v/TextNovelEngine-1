package com.urfu.textnovelengine;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class UserManagerTest {
    UserManager userManager;

    @Test
    void testAddGetUserMechanic() {
        userManager = new UserManager();
        userManager.addUser(123);
        assertNotNull(userManager.getUser(123));
    }
}
