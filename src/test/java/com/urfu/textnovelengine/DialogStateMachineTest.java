package com.urfu.textnovelengine;

import com.urfu.textnovelengine.backendapi.User;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class DialogStateMachineTest {

    @Test
    void testSimpleTest() throws IOException {
        var testScript = "test_dialog";
        var expectedReturnedAnswers = new String[]{"YES", "NO"};
        var answers = new String[3];
        answers[0] = "1";
        answers[1] = "1";
        answers[2] = "1";

        var testIO = new TestIO(answers);
        var scriptsManager = new ScriptsManager();
        var dialogMachine = new DialogStateMachine(scriptsManager);

        var testUser = new User(1);
        testUser.setNewScript(testScript);

        assertTrue(dialogMachine.StartDialog(testUser, testIO));

        assertTrue(dialogMachine.Update(testUser, testIO));
        assertArrayEquals(expectedReturnedAnswers, testIO.getReturnedAnswers());

        assertTrue(dialogMachine.Update(testUser, testIO));

        assertTrue(dialogMachine.Update(testUser, testIO));

        assertFalse(dialogMachine.Update(testUser, testIO));
    }


    @Test
    void testPathsToDifferentTerminalNodes() throws IOException {
        var testScript = "matan_love";
        var expectedReturnedAnswers = new String[]{"YES", "NO"};
        var answers = new String[3];
        answers[0] = "1";
        answers[1] = "1";
        answers[2] = "1";

        var testIO = new TestIO(answers);
        var scriptsManager = new ScriptsManager();
        var dialogMachine = new DialogStateMachine(scriptsManager);

        var testUser = new User(1);
        testUser.setNewScript(testScript);
        assertTrue(false);
    }

    @Test
    void testDifferentPathToOneNode() throws IOException {
        var testScript = "test_dialog";
        var expectedReturnedAnswers = new String[]{"YES", "NO"};
        var answers = new String[3];
        answers[0] = "1";
        answers[1] = "1";
        answers[2] = "1";

        var testIO = new TestIO(answers);
        var scriptsManager = new ScriptsManager();
        var dialogMachine = new DialogStateMachine(scriptsManager);

        var testUser = new User(1);
        testUser.setNewScript(testScript);

        assertTrue(false);
    }

    @Test
    void testMapAndReduce() throws IOException {
        var testScript = "test_dialog";
        var expectedReturnedAnswers = new String[]{"YES", "NO"};
        var answers = new String[3];
        answers[0] = "1";
        answers[1] = "1";
        answers[2] = "1";

        var testIO = new TestIO(answers);
        var scriptsManager = new ScriptsManager();
        var dialogMachine = new DialogStateMachine(scriptsManager);

        var testUser = new User(1);
        testUser.setNewScript(testScript);
        assertTrue(false);
    }

}
