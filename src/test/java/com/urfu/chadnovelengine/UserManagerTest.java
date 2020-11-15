package com.urfu.chadnovelengine;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UserManagerTest {
    private UserManager userManager;

    @Test
    void testAddGetUserMechanic() {
        userManager = new UserManager();
        userManager.addUser(123);
        Assertions.assertNotNull(userManager.getUser(123));
    }
}
