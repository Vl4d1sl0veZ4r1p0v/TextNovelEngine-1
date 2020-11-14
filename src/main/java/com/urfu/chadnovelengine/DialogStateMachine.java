package com.urfu.chadnovelengine;

import com.urfu.chadnovelengine.backendapi.IO;
import com.urfu.chadnovelengine.backendapi.Message;
import com.urfu.chadnovelengine.backendapi.MessageType;
import com.urfu.chadnovelengine.backendapi.User;

public class DialogStateMachine {

    public boolean startDialog(User user, ScriptsManager scripts, IO io) {
        var userCurrentScript = scripts.getScript(user.getCurrentScript());
        return printResponse(user, userCurrentScript, io);
    }

    public boolean update(User user, ScriptsManager scripts, IO io) {
        var userCurrentScript = scripts.getScript(user.getCurrentScript());
        checkAnswer(user, userCurrentScript, io);
        if (!user.hasRunningScript()) {
            return false;
        }
        return printResponse(user, userCurrentScript, io);
    }

    private boolean printResponse(User user, Script script, IO io) {
        var currentNode = script.getNode(user.getCurrentNodeIndex());
        var answers = currentNode.getAnswers();

        io.sendMessages(currentNode.getMessages());

        if (answers == null) {
            user.clearCurrentScript();
            io.sendMessage(new Message("Сюжет окончен", MessageType.TEXT));
            return false;
        }

        io.printPossibleAnswers(answers);

        return true;
    }

    private void checkAnswer(User user, Script script, IO io) {
        var currentNode = script.getNode(user.getCurrentNodeIndex());
        var answer = io.getUserAnswer();
        var answers = currentNode.getAnswers();

        switch (answer) {
            case "/exit":
                user.clearCurrentScript();
                io.sendMessage(new Message("Выход", MessageType.TEXT));
                return;

            case "/repeat":
                return;

            case "/help":
                io.sendMessage(new Message(
                                """
                                Напишите 'start', чтобы начать диалог.
                                Чтобы ответить, наберите число перед выбранным ответом.
                                Напишите 'repeat', чтобы повторить последнее сообщение.
                                Напишите 'exit', чтобы прервать текущий сюжет.
                                """, MessageType.TEXT));
                return;
        }

        var answerIndex = io.getAnswerIndex(answer, answers);
        if (answerIndex == -1) {
            io.sendMessage(currentNode.getWrongInputReaction());
            return;
        }

        var responses = currentNode.getResponses();
        user.setCurrentNodeIndex(responses[answerIndex]);
    }

}
