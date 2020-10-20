package com.urfu.textnovelengine;

import com.urfu.textnovelengine.parsers.ScriptParser;
import com.urfu.textnovelengine.backendapi.IO;
import com.urfu.textnovelengine.frontend.ConsoleFrontend;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        run("./assets/" + args[0] + ".sc", new ConsoleFrontend(System.in));
    }

    public static void run(String script, IO io) throws IOException {
        var currentNode = ScriptParser.parse(script);

        while (true) {
            var answers = currentNode.getAnswers();
            var talker = currentNode.getTalker();

            io.printMessage(talker.talk(currentNode.getMessage()));

            if (answers == null) {
                return;
            }

            io.printMessage("Что ответишь?");
            io.printPossibleAnswers(answers);
            io.printMessage("цифра ответа: ");

            var answer = io.getUserAnswer();
            if (answer.equals("exit")) {
                return;
            }

            if (!MathTools.isValidAnswer(answer, answers.length)) {
                io.printMessage(talker.wrongInputReaction());
                continue;
            }

            var answerIndex = Integer.parseInt(answer) - 1;

            currentNode = currentNode.getResponses()[answerIndex];
            if (currentNode == null) {
                return;
            }
        }
    }

}
