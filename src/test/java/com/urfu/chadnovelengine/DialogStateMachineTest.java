package com.urfu.chadnovelengine;

import com.urfu.chadnovelengine.backendapi.User;

import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DialogStateMachineTest {

    @Test
    void testSimpleTest() throws IOException {
        var testScript = "test_dialog";
        var expectedTalkerMessages = new String[]{
                "A: HELLO",
                "Что ответишь?",
                "YES",
                "NO",
                "цифра ответа: ",
                "A: GO",
                "Что ответишь?",
                "OKAY",
                "цифра ответа: ",
                "A: THE END",
                "Сюжет окончен"
        };
        String[] replies = {"1", "1"};

        var testIO = new TestIO(replies);
        var scriptsManager = new ScriptsManager();
        var dialogMachine = new DialogStateMachine();

        var testUser = new User(1);
        testUser.setNewScript(testScript);

        Assertions.assertTrue(dialogMachine.startDialog(testUser, scriptsManager, testIO));
        Assertions.assertTrue(dialogMachine.update(testUser, scriptsManager, testIO));
        Assertions.assertFalse(dialogMachine.update(testUser, scriptsManager, testIO));
        Assertions.assertArrayEquals(expectedTalkerMessages, testIO.getTalkerMessages().toArray());
    }


    @Test
    void testPathsToDifferentTerminalNodes() throws IOException {
//      FirstUser represents path to terminate node throught 3-4.
//      SecondUser represents path to terminate node throught 3-5-11.
//      This test represents the ability to move to different terminal node from one node.
        var testScript = "matan_love";

        var expectedReturnedAnswersFirstUser = new String[]{
                "Tian: Что? Я не смогу без тебя его сделать!",
                "Что ответишь?",
                "A",
                "B",
                "цифра ответа: ",
                "Tian: Tian совершила СУЕЦЫД!!!",
                "Сюжет окончен"
        };
        var answersFirstUser = new String[]{"1"};

        var expectedReturnedAnswersSecondUser = new String[]{
                "Tian: Что? Я не смогу без тебя его сделать!",
                "Что ответишь?",
                "A",
                "B",
                "цифра ответа: ",
                "Tian: A",
                "Что ответишь?",
                "A",
                "цифра ответа: ",
                "Tian: И они затащили МАТАН.",
                "Сюжет окончен"
        };
        var answersSecondUser = new String[]{"2", "1"};


        var testIOFirstUser = new TestIO(answersFirstUser);
        var testIOSecondUser = new TestIO(answersSecondUser);


        var scriptsManager = new ScriptsManager();
        var dialogMachine = new DialogStateMachine();

        var firstUser = new User(1);
        var secondUser = new User(2);

        firstUser.setNewScript(testScript);
        secondUser.setNewScript(testScript);
        firstUser.setCurrentNodeIndex(3);
        secondUser.setCurrentNodeIndex(3);

        //first
        Assertions.assertTrue(dialogMachine.startDialog(firstUser, scriptsManager, testIOFirstUser));
        Assertions.assertFalse(dialogMachine.update(firstUser, scriptsManager, testIOFirstUser));
        Assertions.assertArrayEquals(expectedReturnedAnswersFirstUser, testIOFirstUser.getTalkerMessages().toArray());

        //second
        Assertions.assertTrue(dialogMachine.startDialog(secondUser, scriptsManager, testIOSecondUser));
        Assertions.assertTrue(dialogMachine.update(secondUser, scriptsManager, testIOSecondUser));
        Assertions.assertFalse(dialogMachine.update(secondUser, scriptsManager, testIOSecondUser));
        Assertions.assertArrayEquals(expectedReturnedAnswersSecondUser, testIOSecondUser.getTalkerMessages().toArray());
    }

    @Test
    void testDifferentPathToOneNode() throws IOException {
        //This test represents that user can move to one node by different ways.
        //In this test showed paths: 2-5-11, 2-9-11.
        var testScript = "matan_love";

        var expectedReturnedAnswersFirstUser = new String[]{
                "Tian: Да у тебя никогда нет времени! Чем ты вообще там занимаешься?",
                "Что ответишь?",
                "A",
                "B",
                "цифра ответа: ",
                "Tian: A",
                "Что ответишь?",
                "A",
                "цифра ответа: ",
                "Tian: И они затащили МАТАН.",
                "Сюжет окончен"
        };
        var answersFirstUser = new String[]{"1", "1"};

        var expectedReturnedAnswersSecondUser = new String[]{
                "Tian: Да у тебя никогда нет времени! Чем ты вообще там занимаешься?",
                "Что ответишь?",
                "A",
                "B",
                "цифра ответа: ",
                "Tian: M",
                "Что ответишь?",
                "A",
                "B",
                "цифра ответа: ",
                "Tian: И они затащили МАТАН.",
                "Сюжет окончен"
        };
        var answersSecondUser = new String[]{"2", "2"};


        var testIOFirstUser = new TestIO(answersFirstUser);
        var testIOSecondUser = new TestIO(answersSecondUser);


        var scriptsManager = new ScriptsManager();
        var dialogMachine = new DialogStateMachine();

        var firstUser = new User(1);
        var secondUser = new User(2);

        firstUser.setNewScript(testScript);
        secondUser.setNewScript(testScript);
        firstUser.setCurrentNodeIndex(2);
        secondUser.setCurrentNodeIndex(2);

        //first
        Assertions.assertTrue(dialogMachine.startDialog(firstUser, scriptsManager, testIOFirstUser));
        Assertions.assertTrue(dialogMachine.update(firstUser, scriptsManager, testIOFirstUser));
        Assertions.assertFalse(dialogMachine.update(firstUser, scriptsManager, testIOFirstUser));
        Assertions.assertArrayEquals(expectedReturnedAnswersFirstUser, testIOFirstUser.getTalkerMessages().toArray());


        //second
        Assertions.assertTrue(dialogMachine.startDialog(secondUser, scriptsManager, testIOSecondUser));
        Assertions.assertTrue(dialogMachine.update(secondUser, scriptsManager, testIOSecondUser));
        Assertions.assertFalse(dialogMachine.update(secondUser, scriptsManager, testIOSecondUser));
        Assertions.assertArrayEquals(expectedReturnedAnswersSecondUser, testIOSecondUser.getTalkerMessages().toArray());
    }

    @Test
    void testMapAndReduce() throws IOException {
        var testScript = "matan_love";

        var expectedReturnedAnswersFirstUser = new String[]{
                "Tian: Дашь прикурить?",
                "Что ответишь?",
                "A",
                "B",
                "C",
                "цифра ответа: ",
                "Tian: Ты чего наделал?",
                "Что ответишь?",
                "A",
                "B",
                "цифра ответа: ",
                "Tian: Но, я любила своего Зорича “плак, плак”",
                "Что ответишь?",
                "A",
                "цифра ответа: "
        };
        var answersFirstUser = new String[]{"1", "1"};

        var expectedReturnedAnswersSecondUser = new String[]{
                "Tian: Дашь прикурить?",
                "Что ответишь?",
                "A",
                "B",
                "C",
                "цифра ответа: ",
                "Tian: E",
                "Что ответишь?",
                "A",
                "B",
                "цифра ответа: ",
                "Tian: Но, я любила своего Зорича “плак, плак”",
                "Что ответишь?",
                "A",
                "цифра ответа: "
        };
        var answersSecondUser = new String[]{"2", "1"};

        var expectedReturnedAnswersThirdUser = new String[]{
                "Tian: Дашь прикурить?",
                "Что ответишь?",
                "A",
                "B",
                "C",
                "цифра ответа: ",
                "Tian: M",
                "Что ответишь?",
                "A",
                "B",
                "цифра ответа: ",
                "Tian: Но, я любила своего Зорича “плак, плак”",
                "Что ответишь?",
                "A",
                "цифра ответа: "
        };
        var answersThirdUser = new String[]{"3", "1"};

        var testIOFirstUser = new TestIO(answersFirstUser);
        var testIOSecondUser = new TestIO(answersSecondUser);
        var testIOThirdUser = new TestIO(answersThirdUser);


        var scriptsManager = new ScriptsManager();
        var dialogMachine = new DialogStateMachine();

        var firstUser = new User(1);
        var secondUser = new User(2);
        var thirdUser = new User(3);

        firstUser.setNewScript(testScript);
        secondUser.setNewScript(testScript);
        thirdUser.setNewScript(testScript);
        firstUser.setCurrentNodeIndex(6);
        secondUser.setCurrentNodeIndex(6);
        thirdUser.setCurrentNodeIndex(6);

        //first
        Assertions.assertTrue(dialogMachine.startDialog(firstUser, scriptsManager, testIOFirstUser));
        Assertions.assertTrue(dialogMachine.update(firstUser, scriptsManager, testIOFirstUser));
        Assertions.assertTrue(dialogMachine.update(firstUser, scriptsManager, testIOFirstUser));
        //System.out.println(testIOFirstUser.getTalkerMessages());
        Assertions.assertArrayEquals(expectedReturnedAnswersFirstUser, testIOFirstUser.getTalkerMessages().toArray());


        //second
        Assertions.assertTrue(dialogMachine.startDialog(secondUser, scriptsManager, testIOSecondUser));
        Assertions.assertTrue(dialogMachine.update(secondUser, scriptsManager, testIOSecondUser));
        Assertions.assertTrue(dialogMachine.update(secondUser, scriptsManager, testIOSecondUser));
        //System.out.println(testIOSecondUser.getTalkerMessages());
        Assertions.assertArrayEquals(expectedReturnedAnswersSecondUser, testIOSecondUser.getTalkerMessages().toArray());

        //third
        Assertions.assertTrue(dialogMachine.startDialog(thirdUser, scriptsManager, testIOThirdUser));
        Assertions.assertTrue(dialogMachine.update(thirdUser, scriptsManager, testIOThirdUser));
        Assertions.assertTrue(dialogMachine.update(thirdUser, scriptsManager, testIOThirdUser));
        System.out.println(testIOThirdUser.getTalkerMessages());
        Assertions.assertArrayEquals(expectedReturnedAnswersThirdUser, testIOThirdUser.getTalkerMessages().toArray());
    }

}
