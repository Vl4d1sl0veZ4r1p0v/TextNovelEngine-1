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
        var user = Users.getUser(userID);
        if (user.hasRunningScript()) {
            DialogStateMachine.Update(user, Scripts, io);
        } else {
            var userAnswer = io.getUserAnswer();
            var scriptsNames = Scripts.getAllScriptsNames();
            if (MathTools.contains(userAnswer, scriptsNames)) {
                user.setNewScript(userAnswer);
                DialogStateMachine.StartDialog(user, Scripts, io);
            } else {
                io.printMessage("Выберите сюжет:");
                io.printExistingScriptsNames(scriptsNames);
            }
        }
    }

}
