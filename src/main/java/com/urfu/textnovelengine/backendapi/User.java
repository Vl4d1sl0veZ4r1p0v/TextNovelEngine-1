package com.urfu.textnovelengine.backendapi;

public class User {
    public final long ID;
    private String CurrentScript;
    private int CurrentNodeIndex;

    public User(long ID) {
        this.ID = ID;
    }

    public String getCurrentScript() {
        return CurrentScript;
    }

    public void setNewScript(String newScript) {
        CurrentScript = newScript;
        CurrentNodeIndex = 0;
    }

    public void clearCurrentScript() {
        CurrentScript = null;
    }

    public boolean isCurrentScriptExist() {
        return CurrentScript != null;
    }

    public int getCurrentNodeIndex() {
        return CurrentNodeIndex;
    }

    public void setCurrentNodeIndex(int currentNodeIndex) {
        CurrentNodeIndex = currentNodeIndex;
    }
}
