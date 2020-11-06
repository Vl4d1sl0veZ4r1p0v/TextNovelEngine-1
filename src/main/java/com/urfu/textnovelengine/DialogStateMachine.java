package com.urfu.textnovelengine;

import com.urfu.textnovelengine.backendapi.IO;
import com.urfu.textnovelengine.backendapi.User;

public class DialogStateMachine {
    private final ScriptsManager scriptsManager;

    public DialogStateMachine(ScriptsManager scriptsManager) {
        this.scriptsManager = scriptsManager;
    }

    public boolean StartDialog(User user, IO io) {
        var userCurrentScript = scriptsManager.getScript(user.getCurrentScript());
        return PrintResponse(user, userCurrentScript, io);
    }

    public boolean Update(User user, IO io) {
        var userCurrentScript = scriptsManager.getScript(user.getCurrentScript());
        CheckAnswer(user, userCurrentScript, io);
        if (!user.isCurrentScriptExist()) {
            return false;
        }
        return PrintResponse(user, userCurrentScript, io);
    }

    private boolean PrintResponse(User user, Script script, IO io) {
        var currentNode = script.getNode(user.getCurrentNodeIndex());
        var answers = currentNode.getAnswers();
        var talker = currentNode.getTalker();

        io.printMessage((talker.talk(currentNode.getMessage())));

        if (null == answers) {
            user.clearCurrentScript();
            io.printMessage("Сюжет окончен");
            return false;
        }

        io.printMessage("Что ответишь?");
        io.printPossibleAnswers(answers);
        io.printMessage("цифра ответа: ");

        return true;
    }

    private void CheckAnswer(User user, Script script, IO io) {
        var currentNode = script.getNode(user.getCurrentNodeIndex());
        var answer = io.getUserAnswer();
        var answers = currentNode.getAnswers();
        var talker = currentNode.getTalker();

        switch (answer) {
            case "exit":
                user.clearCurrentScript();
                io.printMessage("Выход");
                return;
            case "repeat":
                return;
            case "help":
                io.printMessage(
                        """
                                Напишите 'start', чтобы начать диалог. 
                                Чтобы ответить, наберите число перед выбранным ответом.
                                Напишите 'repeat', чтобы повторить последнее сообщение. 
                                Напишите 'exit', чтобы прервать текущий сюжет.
                                """);
                return;
        }

        if (!MathTools.isValidAnswer(answer, answers.length)) {
            io.printMessage(talker.wrongInputReaction());
            return;
        }

        var responses = currentNode.getResponses();
        user.setCurrentNodeIndex(responses[Integer.parseInt(answer) - 1]);
    }

}
