package com.urfu.textnovelengine;

import com.urfu.textnovelengine.parsers.ScriptParser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;

public class ScriptsManager {
    private final HashMap<String, Script> Scripts;

    public ScriptsManager() throws IOException {
        Scripts = new HashMap<>();
        ParseAllScripts(getScriptsNames());
    }

    private String[] getScriptsNames() throws IOException {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        File file = new File(classLoader.getResource("scripts.list").getFile());
        var text = Files.readAllLines(file.toPath());
        return text.toArray(new String[0]);
    }

    private void ParseAllScripts(String[] scriptsNames) throws IOException {
        for (String name : scriptsNames) {
            System.out.println(name);
            Scripts.put(name, new Script(ScriptParser.parse(name)));
        }
    }

    public Script getScript(String scriptName) {
        return Scripts.get(scriptName);
    }

}
