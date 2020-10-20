package com.urfu.textnovelengine.frontend;

import com.urfu.textnovelengine.backendapi.IO;

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
        for (var i = 0; i < answers.length; i++) {
            System.out.println((i + 1) + " " + answers[i]);
        }
    }

    @Override
    public String getUserAnswer() {
        return console.nextLine();
    }
}
