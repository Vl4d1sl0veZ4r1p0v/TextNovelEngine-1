package com.urfu.chadnovelengine.backendapi;

public class User {
    public final int ID;
    private String currentScript;
    private int currentNodeIndex;

    public User(int ID) {
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

    public boolean hasRunningScript() {
        return currentScript != null;
    }

    public int getCurrentNodeIndex() {
        return currentNodeIndex;
    }

    public void setCurrentNodeIndex(int currentNodeIndex) {
        this.currentNodeIndex = currentNodeIndex;
    }
}
