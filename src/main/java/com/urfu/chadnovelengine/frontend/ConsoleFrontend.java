package com.urfu.chadnovelengine.frontend;

import com.urfu.chadnovelengine.backendapi.IO;
import com.urfu.chadnovelengine.MathTools;
import com.urfu.chadnovelengine.backendapi.Message;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class ConsoleFrontend implements IO {
    private final Scanner console;

    public ConsoleFrontend(InputStream source) {
        console = new Scanner(source);
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
    public void sendMessage(Message message) {
        switch (message.messageType) {
            case TEXT -> printMessage(message.content);
            case IMAGE, MUSIC, VIDEO, DOCUMENT -> logContent(message);
        }
    }

    @Override
    public void sendMessages(ArrayList<Message> messages) {
        for (var message : messages) {
            sendMessage(message);
        }
    }

    private void printMessage(String message) {
        System.out.println(message);
    }

    private void printArrayWithIndices(String[] array) {
        for (var i = 0; i < array.length; i++) {
            printMessage((i + 1) + " " + array[i]);
        }
    }

    private void logContent(Message message) {
        printMessage("content path: " + message.content + ", type: " + message.messageType);
    }

}
