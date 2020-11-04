package com.urfu.chadnovelengine;

import com.urfu.chadnovelengine.backendapi.Talker;

public class SimpleTalker implements Talker {
    private final String name;
    private final String wrongInputSpeech;

    public SimpleTalker(String name, String wrongInputSpeech) {
        this.name = name;
        this.wrongInputSpeech = wrongInputSpeech;
    }

    @Override
    public String talk(String speech) {
        return name + ": " + speech;
    }

    @Override
    public String wrongInputReaction() {
        return talk(wrongInputSpeech);
    }
}
