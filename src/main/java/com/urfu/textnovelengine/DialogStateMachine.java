package com.urfu.textnovelengine;

import com.urfu.textnovelengine.backendapi.IO;
import com.urfu.textnovelengine.backendapi.User;

public class DialogStateMachine {

    public boolean StartDialog(User user, ScriptsManager scripts, IO io) {
        var userCurrentScript = scripts.getScript(user.getCurrentScript());
        return PrintResponse(user, userCurrentScript, io);
    }

    public boolean Update(User user, ScriptsManager scripts, IO io) {
        var userCurrentScript = scripts.getScript(user.getCurrentScript());
        CheckAnswer(user, userCurrentScript, io);
        if (!user.hasRunningScript()) {
            return false;
        }
        return PrintResponse(user, userCurrentScript, io);
    }

    private boolean PrintResponse(User user, Script script, IO io) {
        var currentNode = script.getNode(user.getCurrentNodeIndex());
        var answers = currentNode.getAnswers();
        var talker = currentNode.getTalker();

        io.printMessage((talker.talk(currentNode.getTalkerMessage())));

        if (answers == null) {
            user.clearCurrentScript();
            io.printMessage("Сюжет окончен");
            return false;
        }

        io.printPossibleAnswers(answers);

        return true;
    }

    private void CheckAnswer(User user, Script script, IO io) {
        var currentNode = script.getNode(user.getCurrentNodeIndex());
        var answer = io.getUserAnswer();
        var answers = currentNode.getAnswers();
        var talker = currentNode.getTalker();

        switch (answer) {
            case "/exit":
                user.clearCurrentScript();
                io.printMessage("Выход");
                return;

            case "/repeat":
                return;

            case "/help":
                io.printMessage(
                        """
                                Напишите 'start', чтобы начать диалог. 
                                Чтобы ответить, наберите число перед выбранным ответом.
                                Напишите 'repeat', чтобы повторить последнее сообщение. 
                                Напишите 'exit', чтобы прервать текущий сюжет.
                                """);
                return;
        }

        var answerIndex = io.getAnswerIndex(answer, answers);
        if (answerIndex == -1) {
            io.printMessage(talker.wrongInputReaction());
            return;
        }

        var responses = currentNode.getResponses();
        user.setCurrentNodeIndex(responses[answerIndex]);
    }

}
