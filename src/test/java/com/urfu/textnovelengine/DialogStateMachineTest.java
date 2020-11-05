package com.urfu.textnovelengine;

import com.urfu.textnovelengine.backendapi.User;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

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
        String[] replies = new String[] { "1", "1"};

        var testIO = new TestIO(replies);
        var scriptsManager = new ScriptsManager();
        var dialogMachine = new DialogStateMachine(scriptsManager);

        var testUser = new User(1);
        testUser.setNewScript(testScript);

        assertTrue(dialogMachine.StartDialog(testUser, testIO));
        assertTrue(dialogMachine.Update(testUser, testIO));
        assertFalse(dialogMachine.Update(testUser, testIO));
        assertArrayEquals(expectedTalkerMessages, testIO.getTalkerMessages().toArray());
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
        var dialogMachine = new DialogStateMachine(scriptsManager);

        var firstUser = new User(1);
        var secondUser = new User(2);
        //
        firstUser.setNewScript(testScript);
        secondUser.setNewScript(testScript);
        firstUser.setCurrentNodeIndex(3);
        secondUser.setCurrentNodeIndex(3);
        //first

        assertTrue(dialogMachine.StartDialog(firstUser, testIOFirstUser));
        assertFalse(dialogMachine.Update(firstUser, testIOFirstUser));
        assertArrayEquals(expectedReturnedAnswersFirstUser, testIOFirstUser.getTalkerMessages().toArray());

        //second
        assertTrue(dialogMachine.StartDialog(secondUser, testIOSecondUser));
        assertTrue(dialogMachine.Update(secondUser, testIOSecondUser));
        assertFalse(dialogMachine.Update(secondUser, testIOSecondUser));
        assertArrayEquals(expectedReturnedAnswersSecondUser, testIOSecondUser.getTalkerMessages().toArray());
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
        var dialogMachine = new DialogStateMachine(scriptsManager);

        var firstUser = new User(1);
        var secondUser = new User(2);
        //
        firstUser.setNewScript(testScript);
        secondUser.setNewScript(testScript);
        firstUser.setCurrentNodeIndex(2);
        secondUser.setCurrentNodeIndex(2);
        //first

        assertTrue(dialogMachine.StartDialog(firstUser, testIOFirstUser));
        assertTrue(dialogMachine.Update(firstUser, testIOFirstUser));
        assertFalse(dialogMachine.Update(firstUser, testIOFirstUser));
        assertArrayEquals(expectedReturnedAnswersFirstUser, testIOFirstUser.getTalkerMessages().toArray());


        //second
        assertTrue(dialogMachine.StartDialog(secondUser, testIOSecondUser));
        assertTrue(dialogMachine.Update(secondUser, testIOSecondUser));
        assertFalse(dialogMachine.Update(secondUser, testIOSecondUser));
        assertArrayEquals(expectedReturnedAnswersSecondUser, testIOSecondUser.getTalkerMessages().toArray());
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
        var dialogMachine = new DialogStateMachine(scriptsManager);

        var firstUser = new User(1);
        var secondUser = new User(2);
        var thirdUser = new User(3);
        //
        firstUser.setNewScript(testScript);
        secondUser.setNewScript(testScript);
        thirdUser.setNewScript(testScript);
        firstUser.setCurrentNodeIndex(6);
        secondUser.setCurrentNodeIndex(6);
        thirdUser.setCurrentNodeIndex(6);
        //first

        assertTrue(dialogMachine.StartDialog(firstUser, testIOFirstUser));
        assertTrue(dialogMachine.Update(firstUser, testIOFirstUser));
        assertTrue(dialogMachine.Update(firstUser, testIOFirstUser));
        //System.out.println(testIOFirstUser.getTalkerMessages());
        assertArrayEquals(expectedReturnedAnswersFirstUser, testIOFirstUser.getTalkerMessages().toArray());


        //second
        assertTrue(dialogMachine.StartDialog(secondUser, testIOSecondUser));
        assertTrue(dialogMachine.Update(secondUser, testIOSecondUser));
        assertTrue(dialogMachine.Update(secondUser, testIOSecondUser));
        //System.out.println(testIOSecondUser.getTalkerMessages());
        assertArrayEquals(expectedReturnedAnswersSecondUser, testIOSecondUser.getTalkerMessages().toArray());

        //third
        assertTrue(dialogMachine.StartDialog(thirdUser, testIOThirdUser));
        assertTrue(dialogMachine.Update(thirdUser, testIOThirdUser));
        assertTrue(dialogMachine.Update(thirdUser, testIOThirdUser));
        System.out.println(testIOThirdUser.getTalkerMessages());
        assertArrayEquals(expectedReturnedAnswersThirdUser, testIOThirdUser.getTalkerMessages().toArray());
    }

}
