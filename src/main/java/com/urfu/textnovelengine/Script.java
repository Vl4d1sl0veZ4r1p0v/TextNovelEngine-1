package com.urfu.textnovelengine;

import com.urfu.textnovelengine.backendapi.IO;

import java.util.ArrayList;

public class Script {
    private DialogNode currentNode;

    public Script(DialogNode startNode) {
        this.currentNode = startNode;
    }

    public boolean StartDialog(IO io) {
        return PrintResponse(io);
    }

    public boolean Update(IO io) {
        CheckAnswer(io);
        return PrintResponse(io);
    }

    private boolean PrintResponse(IO io) {
        var answers = currentNode.getAnswers();
        var talker = currentNode.getTalker();

        io.printMessage((talker.talk(currentNode.getMessage())));

        if (answers == null) {
            io.printMessage("Сюжет окончен");
            return false;
        }

        io.printMessage("Что ответишь?");
        io.printPossibleAnswers(answers);
        io.printMessage("цифра ответа: ");

        return true;
    }

    private void CheckAnswer(IO io) {
        var answer = io.getUserAnswer();
        var answers = currentNode.getAnswers();
        var talker = currentNode.getTalker();

        switch (answer) {
            case "exit":
                io.printMessage("Выход");
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

        var answerIndex = Integer.parseInt(answer) - 1;
        currentNode = currentNode.getResponses()[answerIndex];
    }

    private String[] getPossibleReplies(String[] answers) {
        var replies = new String[answers.length];
        for (var i = 0; i < answers.length; i++) {
            replies[i] = (i + 1) + " " + answers[i];
        }

        return replies;
    }

    private String JoinReplies(ArrayList<String> replies) {
        return String.join("\n", replies);
    }

}
