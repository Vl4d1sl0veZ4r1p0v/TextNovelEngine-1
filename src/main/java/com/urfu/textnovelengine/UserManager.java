package com.urfu.textnovelengine;

import com.urfu.textnovelengine.backendapi.User;
import java.util.HashMap;

public class UserManager {
    private final HashMap<Long, User> users;

    public UserManager() {
        users = new HashMap<>();
    }

    public void addUser(long id) {
        users.put(id, new User(id));
    }

    public User getUser(long id) {
        return users.get(id);
    }
}
