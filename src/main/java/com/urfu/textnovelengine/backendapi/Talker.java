package com.urfu.textnovelengine.backendapi;

public interface Talker {
    String talk(String speech);

    String wrongInputReaction();
}
