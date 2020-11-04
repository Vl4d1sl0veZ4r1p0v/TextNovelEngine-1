package com.urfu.textnovelengine;

import com.urfu.textnovelengine.frontend.ConsoleFrontend;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        testRun();
    }

    public static void testRun() throws IOException {
        var backend = new Backend();
        var io = new ConsoleFrontend(System.in);

        var testUserID = 1;

        backend.UpdateUser(testUserID, io);
        var user = backend.Users.getUser(testUserID);
        while (user.hasRunningScript()) {
            backend.UpdateUser(testUserID, io);
        }
    }

}
