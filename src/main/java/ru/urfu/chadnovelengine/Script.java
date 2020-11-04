package ru.urfu.chadnovelengine;

import ru.urfu.chadnovelengine.parsers.ScriptParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Script {
    private DialogNode currentNode;

    public Script(String scriptPath) throws IOException {
        currentNode = ScriptParser.parse(scriptPath);
    }

    public String startDialog() {
        var reply = new ArrayList<String>();
        var answers = currentNode.getAnswers();
        var talker = currentNode.getTalker();

        reply.add(talker.talk(currentNode.getMessage()));

        if (answers == null) {
            reply.add("Сюжет окончен");
            return JoinReplies(reply);
        }

        var possibleReplies = getPossibleReplies(answers);
        reply.add("Что ответишь?");
        Collections.addAll(reply, possibleReplies);
        reply.add("цифра ответа: ");

        return JoinReplies(reply);
    }

    public String Update(String answer) {
        var reply = new ArrayList<String>();
        var answers = currentNode.getAnswers();
        var talker = currentNode.getTalker();

        reply.add(talker.talk(currentNode.getMessage()));

        if (answers == null) {
            reply.add("Сюжет окончен");
            return JoinReplies(reply);
        }

        var possibleReplies = getPossibleReplies(answers);
        reply.add("Что ответишь?");
        Collections.addAll(reply, possibleReplies);
        reply.add("цифра ответа: ");


        if (answer.equals("exit")) {
            reply.add("Выход");
            return JoinReplies(reply);
        }

        if (!MathTools.isValidAnswer(answer, answers.length)) {
            reply.add(talker.wrongInputReaction());
            return JoinReplies(reply);
        }

        var answerIndex = Integer.parseInt(answer) - 1;
        currentNode = currentNode.getResponses()[answerIndex];

        return JoinReplies(reply);
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
