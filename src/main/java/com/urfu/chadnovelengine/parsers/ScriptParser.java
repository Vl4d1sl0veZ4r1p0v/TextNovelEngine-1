package com.urfu.chadnovelengine.parsers;

import com.urfu.chadnovelengine.DialogNode;
import com.urfu.chadnovelengine.Script;
import com.urfu.chadnovelengine.SimpleTalker;
import com.urfu.chadnovelengine.backendapi.Talker;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;

public class ScriptParser {
    public static Script parse(String scriptName) throws IOException {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        File file = new File(classLoader.getResource(scriptName + ".sc").getFile());

        var text = Files.readAllLines(file.toPath());
        var nodesAmount = Integer.parseInt(text.get(0).split(": ")[1]);
        var nodes = new DialogNode[nodesAmount];
        var talkers = new HashMap<String, Talker>();

        for (int i = 2, nodeIndex = 0; i < text.size(); i++) {
            var buffer = text.get(i).split(": ");

            if (buffer[0].equals("talker")) {
                var talkerName = buffer[1];
                var talkerWrongInputSpeech = text.get(++i).split(": ")[1];
                talkers.put(talkerName, new SimpleTalker(talkerName, talkerWrongInputSpeech));
            }

            if (buffer[0].equals("node")) {
                var talker = talkers.get(text.get(++i).split(": ")[1]);
                var talkerMessage = text.get(++i).split(": ")[1];
                var newNode = new DialogNode(talker, talkerMessage);

                var answersAmount = Integer.parseInt(text.get(++i).split(": ")[1]);
                if (answersAmount != 0) {
                    var answers = new String[answersAmount];
                    var responses = new int[answersAmount];
                    for (var k = 0; k < answersAmount; k++) {
                        var bufferLine = text.get(++i).split(": ");
                        responses[k] = Integer.parseInt(bufferLine[0]);
                        answers[k] = bufferLine[1];
                    }

                    newNode.setAnswers(answers);
                    newNode.setResponses(responses);
                }

                nodes[nodeIndex++] = newNode;
            }

        }

        return new Script(nodes);
    }
}

