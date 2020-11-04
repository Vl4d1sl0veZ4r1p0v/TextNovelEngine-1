package ru.urfu.chadnovelengine.backendapi;

public interface IO {
    void printMessage(String message);

    void printPossibleAnswers(String[] answers);

    String getUserAnswer();
}
