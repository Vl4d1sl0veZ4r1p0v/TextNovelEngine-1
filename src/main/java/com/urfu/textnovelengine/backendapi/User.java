package com.urfu.textnovelengine.backendapi;

public class User {
    public final long ID;
    private String currentScript;
    private int currentNodeIndex;

    public User(long ID) {
        this.ID = ID;
    }

    public String getCurrentScript() {
        return currentScript;
    }

    public void setNewScript(String newScript) {
        currentScript = newScript;
        currentNodeIndex = 0;
    }

    public void clearCurrentScript() {
        currentScript = null;
    }

    public boolean isCurrentScriptExist() {
        return null != currentScript;
    }

    public int getCurrentNodeIndex() {
        return currentNodeIndex;
    }

    public void setCurrentNodeIndex(int currentNodeIndex) {
        this.currentNodeIndex = currentNodeIndex;
    }
}
