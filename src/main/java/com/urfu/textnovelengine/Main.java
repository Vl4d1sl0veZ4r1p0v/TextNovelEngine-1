package com.urfu.textnovelengine;

import com.urfu.textnovelengine.parsers.ScriptParser;
import com.urfu.textnovelengine.backendapi.IO;
import com.urfu.textnovelengine.frontend.ConsoleFrontend;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        run(args[0] + ".sc", new ConsoleFrontend(System.in));
    }

    public static void run(String scriptName, IO io) throws IOException {
        var scriptStartNode = ScriptParser.parse(scriptName);
        var script = new Script(scriptStartNode);
        var doesContinue = script.StartDialog(io);

        while (doesContinue) {
            doesContinue = script.Update(io);
        }
    }

}
