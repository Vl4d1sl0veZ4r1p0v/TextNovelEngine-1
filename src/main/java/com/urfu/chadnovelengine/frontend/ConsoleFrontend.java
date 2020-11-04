package com.urfu.chadnovelengine.frontend;

import com.urfu.chadnovelengine.backendapi.IO;

import java.io.InputStream;
import java.util.Scanner;

public class ConsoleFrontend implements IO {
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

    private void printArrayWithIndices(String[] array) {
        for (var i = 0; i < array.length; i++) {
            System.out.println((i + 1) + " " + array[i]);
        }
    }
}
