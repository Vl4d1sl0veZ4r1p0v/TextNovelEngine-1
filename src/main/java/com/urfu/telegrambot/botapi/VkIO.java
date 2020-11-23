package com.urfu.telegrambot.botapi;

import com.urfu.chadnovelengine.backendapi.IO;
import com.urfu.chadnovelengine.backendapi.Message;
import com.vk.api.sdk.objects.messages.Keyboard;
import com.vk.api.sdk.objects.messages.KeyboardButton;
import com.vk.api.sdk.objects.messages.KeyboardButtonAction;
import com.vk.api.sdk.objects.messages.KeyboardButtonActionType;
import java.util.ArrayList;
import java.util.List;

public class VkIO implements IO {
  private Keyboard buttons;
  private ArrayList<Message> messages;
  private String currentUserAnswer;

  public VkIO() {
    messages = new ArrayList<>();
  }

  @Override
  public void printPossibleAnswers(String[] answers) {
    setPossibleReplies(answers);
  }

  private void setPossibleReplies(String[] answers) {
    buttons = new Keyboard();
    List<List<KeyboardButton>> allButtons = new ArrayList<>();
    for (String answer : answers){
      List<KeyboardButton> row = new ArrayList<>();
      row.add(new KeyboardButton().setAction(new KeyboardButtonAction().setLabel(answer).
          setType(KeyboardButtonActionType.TEXT)));
      allButtons.add(row);
    }
    buttons.setButtons(allButtons);
  }

  @Override
  public void printExistingScriptsNames(String[] scriptsNames) {
    setPossibleReplies(scriptsNames);
  }

  @Override
  public String getUserAnswer() {
    return currentUserAnswer;
  }

  @Override
  public int getAnswerIndex(String answer, String[] answers) {
    for (var i = 0; i < answers.length; ++i) {
      if (answer.equals(answers[i])) {
        return i;
      }
    }
    return -1;
  }

  @Override
  public void sendMessage(Message message) {
    messages.add(message);
  }

  @Override
  public void sendMessages(ArrayList<Message> messages) {
    this.messages.addAll(messages);
  }

  public void setUserAnswer(String answer) {
    currentUserAnswer = answer;
  }

  public ArrayList<Message> getMessages() {
    return messages;
  }

  public Keyboard getButtons() {
    return buttons;
  }
}
