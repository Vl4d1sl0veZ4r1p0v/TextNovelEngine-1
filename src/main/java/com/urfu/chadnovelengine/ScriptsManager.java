package com.urfu.chadnovelengine;

import com.urfu.chadnovelengine.parsers.ScriptParser;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;

public class ScriptsManager {
    private final HashMap<String, Script> scripts;
    private final String[] scriptsNames;

    public ScriptsManager() throws IOException {
        scriptsNames = getScriptsNames();
        scripts = parseAllScripts(scriptsNames);
    }

    private String[] getScriptsNames() throws IOException {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        File file = new File(classLoader.getResource("scripts.list").getFile());
        var text = Files.readAllLines(file.toPath());
        return text.toArray(new String[0]);
    }

    private HashMap<String, Script> parseAllScripts(String[] scriptsNames) throws IOException {
        var scripts = new HashMap<String, Script>();
        for (String name : scriptsNames) {
            scripts.put(name, ScriptParser.parse(name));
        }

        return scripts;
    }

    public Script getScript(String scriptName) {
        return scripts.get(scriptName);
    }

    public String[] getAllScriptsNames() {
        return scriptsNames;
    }

}
