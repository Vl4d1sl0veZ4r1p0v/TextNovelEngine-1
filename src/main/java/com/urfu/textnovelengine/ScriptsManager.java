package com.urfu.textnovelengine;

import com.urfu.textnovelengine.parsers.ScriptParser;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;

public class ScriptsManager {
    private final HashMap<String, Script> scripts;

    public ScriptsManager() throws IOException {
        scripts = new HashMap<>();
        parseAllScripts(getScriptsNames());
    }

    private String[] getScriptsNames() throws IOException {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        File file = new File(classLoader.getResource("scripts.list").getFile());
        var text = Files.readAllLines(file.toPath());
        return text.toArray(new String[0]);
    }

    private void parseAllScripts(String[] scriptsNames) throws IOException {
        for (String name : scriptsNames) {
            scripts.put(name, ScriptParser.parse(name));
        }
    }

    public Script getScript(String scriptName) {
        return scripts.get(scriptName);
    }

}
