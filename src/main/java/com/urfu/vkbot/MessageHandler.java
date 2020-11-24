package com.urfu.vkbot;

import com.urfu.chadnovelengine.Backend;
import com.urfu.chadnovelengine.BanManager;
import com.urfu.chadnovelengine.backendapi.MessageType;
import com.urfu.telegrambot.botapi.VkIO;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.messages.Message;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
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
  private final HashMap<Integer, Long> lastMessageTimeUNIX;
  private final HashMap<MessageType, String> messageTypeFileTypeMap;
  private Integer ownerId;
  private Integer id;

  private static HashMap<Integer, Long> test(){
    var tmp = new HashMap<Integer, Long>();
    tmp.put(154175388, 1000 * 5 * 60L);
    return tmp;
  }

  public MessageHandler(VkApiClient vk, GroupActor actor) throws IOException {
    this.vk = vk;
    this.actor = actor;
    random = new Random();
    allUsers = new HashSet<String>();
    //
    List<Integer> admins = new ArrayList<Integer>();
    admins.add(207887738);
    //
    HashMap<Integer, Long> banned = new HashMap<Integer, Long>();
    chadNovelEngineBackend = new Backend();
    banManager = new BanManager(banned, admins);
    bannedMessage = "You're banned!";
    lastMessageTimeUNIX = new HashMap<>();
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

  public void handleMessage(Message message) throws ClientException, ApiException {
    var messageText = message.getText();
    var userId = message.getFromId();

    //Logging
    System.out.println("Vk message from User: " + userId + ", data: " + message.getText());
    //

    allUsers.add(userId.toString());

    if (lastMessageTimeUNIX.containsKey(userId)
        && banManager.filtered(userId, lastMessageTimeUNIX.get(userId))
    ) {

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
          sendAllUsers(Arrays.copyOf(
              allUsers.toArray(),
              allUsers.size(),
              String[].class), io, userId);
        }
        default -> {
          handleMessages(io, messageText, userId);
        }
      }
    }
  }

  private void sendAllUsers(String[] allUsers, VkIO io, int userId)
      throws ClientException, ApiException {
    io.printPossibleAnswers(allUsers);
    vk.messages()
      .send(actor)
      .message("All users")
      .userId(userId)
      .randomId(random.nextInt(10000))
      .keyboard(io.getButtons())
      .execute();

  }
  private void handleMessages(VkIO io, String messageText, int userId)
      throws ClientException, ApiException {

//    if (messageText.startsWith("ban: ")){
//      var bannedUserId = Integer.parseInt(messageText.substring(5, messageText.length()));
////        if (!banManager.isAdmin(bannedUserId))
//      banManager.put(userId, bannedUserId, 1000 * 60 * 5L);
//    }

    io.setUserAnswer(messageText);
    lastMessageTimeUNIX.put(userId, System.currentTimeMillis());
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
              messageTypeFileTypeMap.get(m.messageType) + String.format("-%d_%d", ownerId, id))
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
