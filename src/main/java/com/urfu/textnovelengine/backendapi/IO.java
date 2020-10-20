package com.urfu.textnovelengine.backendapi;

public interface IO {
    void printMessage(String message);

    void printPossibleAnswers(String[] answers);

    String getUserAnswer();
}
