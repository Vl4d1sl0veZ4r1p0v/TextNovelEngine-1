package com.urfu.vkbot;

import static org.junit.jupiter.api.Assertions.*;

import com.urfu.chadnovelengine.BanManager;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.messages.Message;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MessageHandlerTest {
  TransportClient transportClient;
  VkApiClient vk;
  GroupActor actor;
  MessageHandler messageHandler;
  BanManager banManager;

  @BeforeEach
  public void init() throws IOException {
    transportClient = new HttpTransportClient();
    vk = new VkApiClient(transportClient);
    actor = new GroupActor(
        42,
        "9e9910a329bee4a9d77346ba8112445d3d6004b066edfec84d2dff1dc3eee76a43a35d261163b14a0b39e");
    //
    Set<Integer> admins = new HashSet<>();
    admins.add(207887738);
    //
    banManager = new BanManager(new HashSet<>(), admins);
    messageHandler = new MessageHandler(vk, actor, banManager);
  }

  @Test
  public void adminBanByUserId(){
    //
    List<Integer> userIds = new ArrayList<>();
    userIds.add(154175388);
    userIds.add(207887738);
    userIds.add(207887738);
    userIds.add(207887738);
    //
    List<String> textMessages = new ArrayList<>();
    textMessages.add("Hi");
    textMessages.add("CMS");
    textMessages.add("Ban panel");
    textMessages.add("Ban: 154175388");
    //
    for (var i = 0; i < 4; ++i){
      Message message = new Message();
      message.setText(textMessages.get(i));
      message.setFromId(userIds.get(i));
      try {
        messageHandler.handleMessages(message);
      } catch (ClientException | ApiException e) {
        e.printStackTrace();
      }
    }
    assertTrue(banManager.isBanned(154175388));
  }

  @Test
  public void adminUnbanByUserId() {
    //
    List<Integer> userIds = new ArrayList<>();
    userIds.add(154175388);
    userIds.add(207887738);
    userIds.add(207887738);
    userIds.add(207887738);
    userIds.add(207887738);
    //
    List<String> textMessages = new ArrayList<>();
    textMessages.add("Hi");
    textMessages.add("CMS");
    textMessages.add("Ban panel");
    textMessages.add("Ban: 154175388");
    textMessages.add("Unban: 154175388");
    //
    for (var i = 0; i < 5; ++i) {
      Message message = new Message();
      message.setText(textMessages.get(i));
      message.setFromId(userIds.get(i));
      try {
        messageHandler.handleMessages(message);
      } catch (ClientException | ApiException e) {
        e.printStackTrace();
      }

    }
    assertFalse(banManager.isBanned(154175388));
  }

  @Test
  public void userBanByUserId(){
    //
    List<Integer> userIds = new ArrayList<>();
    userIds.add(134386864);
    userIds.add(154175388);
    userIds.add(154175388);
    //
    List<String> textMessages = new ArrayList<>();
    textMessages.add("Hi");
    textMessages.add("Hi");
    textMessages.add("Ban: 134386864");
    //
    for (var i = 0; i < 3; ++i) {
      Message message = new Message();
      message.setText(textMessages.get(i));
      message.setFromId(userIds.get(i));
      try {
        messageHandler.handleMessages(message);
      } catch (ClientException | ApiException e) {
        e.printStackTrace();
      }

    }
    assertFalse(banManager.isBanned(134386864));
  }

  @Test
  public void userUnbanByUserId(){
    //
    List<Integer> userIds = new ArrayList<>();
    userIds.add(134386864);
    userIds.add(154175388);
    userIds.add(207887738);
    userIds.add(154175388);
    //
    List<String> textMessages = new ArrayList<>();
    textMessages.add("Hi");
    textMessages.add("Hi");
    textMessages.add("Ban: 134386864");
    textMessages.add("Unban: 134386864");
    //
    for (var i = 0; i < 4; ++i) {
      Message message = new Message();
      message.setText(textMessages.get(i));
      message.setFromId(userIds.get(i));
      try {
        messageHandler.handleMessages(message);
      } catch (ClientException | ApiException e) {
        e.printStackTrace();
      }

    }
    assertTrue(banManager.isBanned(134386864));
  }

  @Test
  public void userBanAdminById(){
    //
    List<Integer> userIds = new ArrayList<>();
    userIds.add(134386864);
    userIds.add(154175388);
    userIds.add(207887738);
    userIds.add(154175388);
    //
    List<String> textMessages = new ArrayList<>();
    textMessages.add("Hi");
    textMessages.add("Hi");
    textMessages.add("Ban: 134386864");
    textMessages.add("Unban: 134386864");
    //
    for (var i = 0; i < 4; ++i) {
      Message message = new Message();
      message.setText(textMessages.get(i));
      message.setFromId(userIds.get(i));
      try {
        messageHandler.handleMessages(message);
      } catch (ClientException | ApiException e) {
        e.printStackTrace();
      }

    }
    assertTrue(banManager.isBanned(134386864));
  }


}
