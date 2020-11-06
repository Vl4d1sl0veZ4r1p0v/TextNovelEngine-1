package com.urfu.textnovelengine;

import com.urfu.textnovelengine.backendapi.IO;
import com.urfu.textnovelengine.backendapi.User;
import com.urfu.textnovelengine.frontend.ConsoleFrontend;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        testRun(args[0], new ConsoleFrontend(System.in));
    }

    public static void testRun(String scriptName, IO io) throws IOException {
        var scriptsManager = new ScriptsManager();
        var dialogMachine = new DialogStateMachine(scriptsManager);

        var testUser = new User(1);
        testUser.setNewScript(scriptName);

        var doesContinue = dialogMachine.startDialog(testUser, io);
        while (doesContinue) {
            doesContinue = dialogMachine.update(testUser, io);
        }
    }

}
