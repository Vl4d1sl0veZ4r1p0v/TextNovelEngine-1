package com.urfu.textnovelengine;

import com.urfu.textnovelengine.backendapi.User;
import org.junit.jupiter.api.BeforeEach;
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
