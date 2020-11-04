package com.urfu.textnovelengine;

import com.urfu.textnovelengine.parsers.ScriptParser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;

public class ScriptsManager {
    private final HashMap<String, Script> Scripts;
    private final String[] ScriptsNames;

    public ScriptsManager() throws IOException {
        ScriptsNames = getScriptsNames();
        Scripts = ParseAllScripts(ScriptsNames);
    }

    private String[] getScriptsNames() throws IOException {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        File file = new File(classLoader.getResource("scripts.list").getFile());
        var text = Files.readAllLines(file.toPath());
        return text.toArray(new String[0]);
    }

    private HashMap<String, Script> ParseAllScripts(String[] scriptsNames) throws IOException {
        var scripts = new HashMap<String, Script>();
        for (String name : scriptsNames) {
            scripts.put(name, ScriptParser.parse(name));
        }

        return scripts;
    }

    public Script getScript(String scriptName) {
        return Scripts.get(scriptName);
    }

    public String[] getAllScriptsNames() {
        return ScriptsNames;
    }

}
