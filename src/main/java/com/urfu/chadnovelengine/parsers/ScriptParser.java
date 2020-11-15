package com.urfu.chadnovelengine.parsers;

import com.urfu.chadnovelengine.DialogNode;
import com.urfu.chadnovelengine.Script;
import com.urfu.chadnovelengine.backendapi.Message;
import com.urfu.chadnovelengine.backendapi.MessageType;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.StringJoiner;

public class ScriptParser {
    public static Script parse(String scriptName) throws IOException {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        File file = new File(
                classLoader.getResource("Scripts/" + scriptName + ".sc").getFile());

        var text = Files.readAllLines(file.toPath());
        var nodes = new ArrayList<DialogNode>();

        for (int i = 0; i < text.size(); ++i) {
            var buffer = text.get(i).split(": ");
            if (buffer[0].equals("node")) {
                var messages = new ArrayList<Message>();
                do {
                    var messageRaw = text.get(++i);
                    var messageSplit = messageRaw.split("[: ]");

                    if (!messageSplit[0].equals("message")) {
                        break;
                    }

                    var content = messageRaw.substring(messageRaw.indexOf(": ") + 2);
                    var type = messageSplit[1];
                    messages.add(CreateMessage(content, type, scriptName, "Content"));
                } while (i < text.size() - 1);

                var newNode = new DialogNode(messages);

                var wrongInputReactionRaw = text.get(i);
                if (wrongInputReactionRaw.equals("")) {
                    nodes.add(newNode);
                    continue;
                }

                var wrongInputReaction = wrongInputReactionRaw.split(": ")[1];
                var answers = new ArrayList<String>();
                var responses = new ArrayList<Integer>();

                ++i;
                while (true) {
                    if (i == text.size() || text.get(++i).equals("")) {
                        newNode.setWrongInputReaction(new Message(wrongInputReaction, MessageType.TEXT));
                        newNode.setAnswers(answers.toArray(new String[0]));
                        newNode.setResponses(responses.stream().mapToInt(k->k).toArray());
                        nodes.add(newNode);
                        break;
                    }

                    var answerSplit = text.get(i).split(": ");
                    responses.add(Integer.parseInt(answerSplit[0]));
                    answers.add(answerSplit[1]);
                }
            }
        }

        return new Script(nodes.toArray(new DialogNode[0]));
    }

    private static Message CreateMessage(
            String message, String type, String scriptName, String contentDirectory) {
        MessageType messageType;
        switch (type) {
            case "image" -> messageType = MessageType.IMAGE;
            case "music" -> messageType = MessageType.MUSIC;
            case "video" -> messageType = MessageType.VIDEO;
            case "doc" -> messageType = MessageType.DOCUMENT;
            default -> messageType = MessageType.TEXT;
        }

        if (messageType != MessageType.TEXT) {
            message = contentDirectory + "/" + scriptName + "/" + message;
        }

        return new Message(message, messageType);
    }

}
