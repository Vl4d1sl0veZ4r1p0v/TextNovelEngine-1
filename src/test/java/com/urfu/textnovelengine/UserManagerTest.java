package com.urfu.textnovelengine;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserManagerTest {
    UserManager userManager;

    @Test
    void testAddGetUserMechanic() {
        userManager = new UserManager();
        userManager.addUser(123);
        assertNotNull(userManager.getUser(123));
    }
}
