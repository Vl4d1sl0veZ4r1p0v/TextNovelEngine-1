package com.urfu.chadnovelengine;

import com.urfu.chadnovelengine.backendapi.User;

import java.util.HashMap;

public class UserManager {
    private final HashMap<Integer, User> users;

    public UserManager() {
        users = new HashMap<>();
    }

    public User addUser(int id) {
        var newUser = new User(id);
        users.put(id, newUser);
        return newUser;
    }

    public User getUser(int id) {
        return users.get(id);
    }

    public boolean isUserRegistered(int id) {
        return users.containsKey(id);
    }
}
