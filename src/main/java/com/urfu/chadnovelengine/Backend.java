package com.urfu.chadnovelengine;

import com.urfu.chadnovelengine.backendapi.IO;

import java.io.IOException;

public class Backend {
    public final UserManager Users;
    public final ScriptsManager Scripts;
    private final DialogStateMachine DialogStateMachine;

    public Backend() throws IOException {
        Users = new UserManager();
        Scripts = new ScriptsManager();
        DialogStateMachine = new DialogStateMachine();
    }

    public void UpdateUser(int userID, IO io) {
        var scriptsNames = Scripts.getAllScriptsNames();
        var user = Users.isUserRegistered(userID) ? Users.getUser(userID) : Users.addUser(userID);

        if (user.hasRunningScript()) {
            checkContinuity(
                    DialogStateMachine.Update(user, Scripts, io),
                    scriptsNames,
                    io);
        } else {
            var userAnswer = io.getUserAnswer();
            if (MathTools.contains(userAnswer, scriptsNames)) {
                user.setNewScript(userAnswer);
                checkContinuity(
                        DialogStateMachine.StartDialog(user, Scripts, io),
                        scriptsNames,
                        io);
            } else {
                io.printMessage("Выберите сюжет:");
                io.printExistingScriptsNames(scriptsNames);
            }
        }
    }

    private void checkContinuity(boolean doesContinue, String[] scriptsNames, IO io) {
        if (!doesContinue) {
            io.printMessage("Выберите новый сюжет:");
            io.printExistingScriptsNames(scriptsNames);
        }
    }

}
