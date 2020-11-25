package com.urfu.vkbot;

import com.urfu.chadnovelengine.Backend;
import com.urfu.chadnovelengine.BanManager;
import com.urfu.chadnovelengine.backendapi.MessageType;
import com.urfu.telegrambot.botapi.VkIO;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.messages.Keyboard;
import com.vk.api.sdk.objects.messages.Message;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class MessageHandler {
  private final Random random;
  private final Set<String> allUsers;
  private final VkApiClient vk;
  private final GroupActor actor;
  private final Backend chadNovelEngineBackend;
  private final BanManager banManager;
  private final String bannedMessage;
  private final String notAdminMessage;
  private final HashMap<MessageType, String> messageTypeFileTypeMap;
  private Integer ownerId;
  private Integer id;

  public MessageHandler(VkApiClient vk, GroupActor actor) throws IOException {
    this.vk = vk;
    this.actor = actor;
    random = new Random();
    //
    allUsers = new HashSet<String>();
    allUsers.add("154175388");
    allUsers.add("154175308");
    allUsers.add("154175088");
    allUsers.add("150175088");
    allUsers.add("154170088");
    allUsers.add("154075088");
    //
    Set<Integer> admins = new HashSet<>();
    admins.add(207887738);
    //
    HashSet<Integer> banned = new HashSet<Integer>();
    banned.add(154175388);
    //
    chadNovelEngineBackend = new Backend();
    banManager = new BanManager(banned, admins);
    bannedMessage = "You're banned!";
    notAdminMessage = "You aren't admin!";
    ownerId = 0;
    id = 0;
    //
    messageTypeFileTypeMap = new HashMap<MessageType, String>();
    messageTypeFileTypeMap.put(MessageType.IMAGE, "photo");
    messageTypeFileTypeMap.put(MessageType.MUSIC, "audio");
    messageTypeFileTypeMap.put(MessageType.VIDEO, "video");
    messageTypeFileTypeMap.put(MessageType.DOCUMENT, "doc");
  }

  private void filesParameters(String name){
    switch (name){
      case "ＣＹＢＥＲ　ＤＲＥＡＭ [Chillwave-SynthwaveMix]" -> {
        ownerId = 200575158;
        id = 456239018;
      }
      case "DOOM - Original Game Soundtrack" -> {
        ownerId = 200575158;
        id = 456239017;
      }
      case "日文、動漫音樂電台 | Anime Music" -> {
        ownerId = 200575158;
        id = 456239019;
      }
      default -> {
        ownerId = 200523079;
        id = 457239018;
      }
    }
  }

  private Keyboard getCMSOptions(VkIO io){
    String[] options = new String[]{"Ban panel"};
    //
    io.printPossibleAnswers(options);
    return io.getButtons();
  }

  private void sendMessage(String message, Integer userId, Keyboard keyboard)
      throws ClientException, ApiException {
    vk.messages()
        .send(actor)
        .message(message)
        .userId(userId)
        .randomId(random.nextInt(10000))
        .keyboard(keyboard)
        .execute();
  }

  private void sendMessage(String message, Integer userId)
      throws ClientException, ApiException {
    vk.messages()
        .send(actor)
        .message(message)
        .userId(userId)
        .randomId(random.nextInt(10000))
        .execute();
  }

  public void handleMessages(Message message)
      throws ClientException, ApiException {
    var messageText = message.getText();
    var userId = message.getFromId();

    //Logging
    System.out.println("Vk message from User: " + userId + ", data: " + message.getText());
    //

    allUsers.add(userId.toString());

    if (banManager.isBanned(userId)) {
      vk.messages()
        .send(actor)
        .message(bannedMessage)
        .userId(userId)
        .randomId(random.nextInt(10000))
        .execute();
    }
    else {
      var io = new VkIO();
      switch (messageText) {
        case "CMS" -> {
          if (banManager.isAdmin(userId))
            vk.messages()
              .send(actor)
              .message("CMS options: ")
              .keyboard(getCMSOptions(io))
              .userId(userId)
              .randomId(random.nextInt(10000))
              .execute();
          else
            vk.messages()
                .send(actor)
                .message(notAdminMessage)
                .userId(userId)
                .randomId(random.nextInt(10000))
                .execute();
        }
        case "Ban panel" -> {
          if (banManager.isAdmin(userId))
            sendAllUsers(Arrays.copyOf(
                allUsers.toArray(),
                allUsers.size(),
                String[].class), io, userId);
          else
            vk.messages()
                .send(actor)
                .message(notAdminMessage)
                .userId(userId)
                .randomId(random.nextInt(10000))
                .execute();
        }
        default -> {
          handleMessage(io, messageText, userId);
        }
      }
    }
  }

  private void sendAllUsers(String[] allUsers, VkIO io, int userId)
      throws ClientException, ApiException {
    for (var i = 0; i < allUsers.length; ++i) {
      var currentUser = Integer.parseInt(allUsers[i]);
      allUsers[i] = (banManager.isBanned(currentUser) ? "Unban: " : "Ban: ") + allUsers[i];
    }
    io.printPossibleAnswers(allUsers);

    vk.messages()
      .send(actor)
      .message("All users")
      .userId(userId)
      .randomId(random.nextInt(10000))
      .keyboard(io.getButtons())
      .execute();
  }

  private void handleMessage(VkIO io, String messageText, int userId)
      throws ClientException, ApiException {

    if (banManager.isAdmin(userId)
        && (messageText.startsWith("Ban: ") || messageText.startsWith("Unban: "))){
      StringBuilder message = new StringBuilder();
      message.append("User : ");
      if (messageText.startsWith("Ban: ")){
        var bannedUserId = Integer.parseInt(messageText.substring(5, messageText.length()));
        message.append(bannedUserId);
          if (!banManager.isAdmin(bannedUserId)) {
            banManager.banUserById(bannedUserId);
            message.append(" banned.");
          }
          else
            message.append(" can't be banned.");
        } else if (messageText.startsWith("Unban: ")) {
        var unbannedUserId = Integer.parseInt(messageText.substring(7, messageText.length()));
        banManager.unbanUserById(unbannedUserId);
        message.append(unbannedUserId);
        message.append(" unbanned.");
      }
      vk.messages()
          .send(actor)
          .message(message.toString())
          .userId(userId)
          .randomId(random.nextInt(10000))
          .keyboard(new Keyboard())
          .execute();
      sendAllUsers(Arrays.copyOf(
          allUsers.toArray(),
          allUsers.size(),
          String[].class), io, userId);
    } else {
      io.setUserAnswer(messageText);
      chadNovelEngineBackend.updateUser(userId, io);
      ownerId = 0;
      id = 0;
      filesParameters(messageText);
      var messages = io.getMessages();
      for (var i = 0; i < messages.size() - 1; ++i) {
        try {
          executeMessage(
              messageTypeFileTypeMap
                  .get(messages.get(i).messageType),
              userId,
              messages.get(i)
          );
        } catch (ClientException e) {
          e.printStackTrace();
        } catch (ApiException e) {
          e.printStackTrace();
        }
      }
      ownerId = 0;
      id = 0;
      filesParameters(messageText);
      var buttons = io.getButtons();
      var m = messages.get(messages.size() - 1);
      switch (m.messageType) {
        case IMAGE, MUSIC, VIDEO, DOCUMENT -> {
          vk.messages()
              .send(actor)
              .attachment(
                  messageTypeFileTypeMap
                      .get(m.messageType) + String.format("-%d_%d", ownerId, id))
              .userId(userId)
              .randomId(random.nextInt(10000))
              .keyboard(buttons)
              .execute();
        }
        default -> {
          vk.messages()
              .send(actor)
              .message(m.content)
              .userId(userId)
              .randomId(random.nextInt(10000))
              .keyboard(buttons)
              .execute();
        }
      }
    }
  }

  private void executeMessage(String fileType,
      int userId,
      com.urfu.chadnovelengine.backendapi.Message m)
      throws ClientException, ApiException {
    switch (m.messageType) {
      case IMAGE, MUSIC, VIDEO, DOCUMENT -> {
        vk.messages()
          .send(actor)
          .attachment(fileType + String.format("-%d_%d", ownerId, id))
          .userId(userId)
          .randomId(random.nextInt(10000))
          .execute();
      }
      default -> {
        vk.messages()
          .send(actor)
          .message(m.content)
          .userId(userId)
          .randomId(random.nextInt(10000))
          .execute();
      }
    }
  }
}
