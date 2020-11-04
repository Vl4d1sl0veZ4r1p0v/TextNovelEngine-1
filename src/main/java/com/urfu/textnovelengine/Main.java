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

        io.printMessage("Для начала наберите: start");
        backend.UpdateUser(testUserID, io);
        var user = backend.Users.getUser(testUserID);
        do {
            backend.UpdateUser(testUserID, io);
        } while (user.hasRunningScript());
    }

}
