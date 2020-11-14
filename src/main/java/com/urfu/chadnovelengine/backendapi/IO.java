package com.urfu.chadnovelengine.backendapi;

public abstract class IO {
    public abstract void printMessage(String message);

    public abstract void printPossibleAnswers(String[] answers);

    public abstract void printExistingScriptsNames(String[] scriptsNames);

    public abstract String getUserAnswer();

    public abstract int getAnswerIndex(String answer, String[] answers);

    protected abstract void trySendContent(String contentName);

    public void sendContent(String contentName) {
        if (contentName != null) {
            trySendContent(contentName);
        }
    }

}
