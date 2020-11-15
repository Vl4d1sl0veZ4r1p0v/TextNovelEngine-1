package com.urfu.chadnovelengine;

import com.urfu.chadnovelengine.parsers.ScriptParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ScriptsManager {
    private final HashMap<String, Script> scripts;
    private final String[] scriptsNames;

    public ScriptsManager() throws IOException {
        scriptsNames = getScriptsNames();
        scripts = parseAllScripts(scriptsNames);
    }

    private String[] getScriptsNames() throws IOException {
        return getResourceFiles("Scripts");
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

    private String[] getResourceFiles(String path) throws IOException {
        List<String> filenames = new ArrayList<>();

        InputStream in = getResourceAsStream(path);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String resource;

        while ((resource = br.readLine()) != null) {
            filenames.add(resource.split("\\.")[0]);
        }

        return filenames.toArray(new String[0]);
    }

    private InputStream getResourceAsStream(String resource) {
        final InputStream in = getContextClassLoader().getResourceAsStream(resource);
        return in == null ? getClass().getResourceAsStream(resource) : in;
    }

    private static ClassLoader getContextClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

}
