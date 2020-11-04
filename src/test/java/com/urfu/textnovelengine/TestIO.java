//package com.urfu.textnovelengine;
//
//import com.urfu.textnovelengine.backendapi.IO;
//
//public class TestIO implements IO {
//    private String[] testAnswers;
//    private String[] returnedAnswers;
//    private int currentAnswer;
//
//    public TestIO(String[] testAnswers) {
//        this.testAnswers = testAnswers;
//        currentAnswer = 0;
//    }
//
//    @Override
//    public void printMessage(String message) {
//    }
//
//    @Override
//    public void printPossibleAnswers(String[] answers) {
//        returnedAnswers = answers;
//    }
//
//    public String[] getReturnedAnswers() {
//        return returnedAnswers;
//    }
//
//    @Override
//    public String getUserAnswer() {
//        return testAnswers[currentAnswer++];
//    }
//}
