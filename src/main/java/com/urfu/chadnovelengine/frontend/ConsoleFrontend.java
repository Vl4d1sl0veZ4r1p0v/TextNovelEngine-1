package com.urfu.chadnovelengine.frontend;

import com.urfu.chadnovelengine.backendapi.IO;
import com.urfu.chadnovelengine.MathTools;
import java.io.InputStream;
import java.util.Scanner;

public class ConsoleFrontend extends IO {
    private final Scanner console;

    public ConsoleFrontend(InputStream source) {
        console = new Scanner(source);
    }

    @Override
    public void printMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void printPossibleAnswers(String[] answers) {
        printArrayWithIndices(answers);
    }

    @Override
    public void printExistingScriptsNames(String[] scriptsNames) {
        printArrayWithIndices(scriptsNames);
    }

    @Override
    public String getUserAnswer() {
        return console.nextLine();
    }

    @Override
    public int getAnswerIndex(String answer, String[] answers) {
        return MathTools.isValidAnswer(answer, answers.length) ? Integer.parseInt(answer) - 1 : -1;
    }

    @Override
    protected void trySendContent(String contentName) {
        printMessage(contentName);
    }

    private void printArrayWithIndices(String[] array) {
        for (var i = 0; i < array.length; i++) {
            System.out.println((i + 1) + " " + array[i]);
        }
    }
}
