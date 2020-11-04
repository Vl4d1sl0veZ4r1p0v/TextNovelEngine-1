package com.urfu.chadnovelengine.backendapi;

public interface IO {
    void printMessage(String message);

    void printPossibleAnswers(String[] answers);

    void printExistingScriptsNames(String[] scriptsNames);

    String getUserAnswer();
}
