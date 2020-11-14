package com.urfu.chadnovelengine;

import com.urfu.chadnovelengine.backendapi.IO;
import com.urfu.chadnovelengine.backendapi.Message;
import com.urfu.chadnovelengine.backendapi.MessageType;

import java.io.IOException;

public class Backend {
    public final UserManager users;
    public final ScriptsManager scripts;
    private final DialogStateMachine dialogStateMachine;

    public Backend() throws IOException {
        users = new UserManager();
        scripts = new ScriptsManager();
        dialogStateMachine = new DialogStateMachine();
    }

    public void updateUser(int userID, IO io) {
        var scriptsNames = scripts.getAllScriptsNames();
        var user = users.isUserRegistered(userID) ? users.getUser(userID) : users.addUser(userID);

        if (user.hasRunningScript()) {
            checkContinuity(
                    dialogStateMachine.update(user, scripts, io),
                    scriptsNames,
                    io);
        } else {
            var userAnswer = io.getUserAnswer();
            if (MathTools.contains(userAnswer, scriptsNames)) {
                user.setNewScript(userAnswer);
                checkContinuity(
                        dialogStateMachine.startDialog(user, scripts, io),
                        scriptsNames,
                        io);
            } else {
                io.sendMessage(new Message("Выберите сюжет:", MessageType.TEXT));
                io.printExistingScriptsNames(scriptsNames);
            }
        }
    }

    private void checkContinuity(boolean doesContinue, String[] scriptsNames, IO io) {
        if (!doesContinue) {
            io.sendMessage(new Message("Выберите новый сюжет:", MessageType.TEXT));
            io.printExistingScriptsNames(scriptsNames);
        }
    }

}
