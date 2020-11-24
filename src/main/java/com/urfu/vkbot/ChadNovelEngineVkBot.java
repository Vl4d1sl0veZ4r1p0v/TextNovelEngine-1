package com.urfu.vkbot;

import com.urfu.chadnovelengine.Backend;
import com.urfu.chadnovelengine.BanManager;
import com.urfu.telegrambot.botapi.VkIO;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.messages.Keyboard;
import com.vk.api.sdk.objects.messages.Message;
import com.vk.api.sdk.queries.messages.MessagesGetLongPollHistoryQuery;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
public class ChadNovelEngineVkBot {
  private Backend backend;

  private static HashMap<Integer, Long> test(){
    var tmp = new HashMap<Integer, Long>();
    tmp.put(154175388, 1000 * 5 * 60L);
    return tmp;
  }



  public static void main(String[] args)
      throws ClientException, ApiException, InterruptedException, IOException {
    TransportClient transportClient = new HttpTransportClient();
    VkApiClient vk = new VkApiClient(transportClient);
    Random random = new Random();
    Set<String> allUsers = new HashSet<String>();

    var admins = new ArrayList<Integer>();
    admins.add(207887738);

    var banned = new HashMap<Integer, Long>();
    BanManager banManager = new BanManager(banned, admins);
    GroupActor actor = new GroupActor(
        200523079,
        "9e9910a329bee4a9d77346ba8112445d3d6004b066edfec84d2dff1dc3eee76a43a35d261163b14a0b39e"
    );
    Integer ts = vk.messages().getLongPollServer(actor).execute().getTs();
    Backend chadNovelEngineBackend = new Backend();
    var ownerId = 0;
    var Id = 0;

    String bannedMessage = "You're banned!";
    HashMap<Integer, Long> lastMessageTimeUNIX = new HashMap<>();

    while (true) {
      MessagesGetLongPollHistoryQuery historyQuery = vk.messages().getLongPollHistory(actor).ts(ts);
      List<Message> messagesHistory = historyQuery.execute().getMessages().getItems();

      if (!messagesHistory.isEmpty()) {
        messagesHistory.forEach(message -> {

          var messageText = message.getText();
          var userId = message.getFromId();
          System.out.println("Vk message from User: " + userId + ", data: " + message.getText());
          allUsers.add("ban: "+userId.toString());

          if (lastMessageTimeUNIX.containsKey(userId) && banManager.filtered(userId,
              lastMessageTimeUNIX.get(userId))) {
            try {

              vk.messages().send(actor).message(bannedMessage).userId(userId).randomId(random.nextInt(10000)).execute();

            } catch (ApiException e) {
              e.printStackTrace();
            } catch (ClientException e) {
              e.printStackTrace();
            }

          }
          else {
            var io = new VkIO();
            switch (messageText) {
              case "CMS" -> {
                try {
                  sendAllUsers(vk, Arrays.copyOf(
                          allUsers.toArray(),
                          allUsers.size(),
                          String[].class) , io, actor, userId, random);
                } catch (ClientException e) {
                  e.printStackTrace();
                } catch (ApiException e) {
                  e.printStackTrace();
                }
              }
              default -> {
                handleMessages(banManager, io, userId, messageText, lastMessageTimeUNIX,
                    chadNovelEngineBackend, actor, random, ownerId, Id, vk);
              }
              }
          }
        });
        }
      ts = vk.messages().getLongPollServer(actor).execute().getTs();
      Thread.sleep(500);
    }

  }

  private static void sendAllUsers(VkApiClient vk, String[] allUsers, VkIO io,
      GroupActor actor, int userId, Random random) throws ClientException, ApiException {
    io.printPossibleAnswers(allUsers);
    vk.messages().send(actor).message("All users").userId(userId)
        .randomId(random.nextInt(10000)).keyboard(io.getButtons()).execute();

  }

  private static void handleMessages(BanManager banManager, VkIO io, int userId, String messageText,
      HashMap<Integer, Long> lastMessageTimeUNIX,
      Backend chadNovelEngineBackend,
      GroupActor actor, Random random,
      int ownerId, int Id, VkApiClient vk){

      if (messageText.startsWith("ban: ")){
        var bannedUserId = Integer.parseInt(messageText.substring(5, messageText.length()));
        if (!banManager.isAdmin(bannedUserId))
          banManager.put(userId, bannedUserId);
      }

      io.setUserAnswer(messageText);
      lastMessageTimeUNIX.put(userId, System.currentTimeMillis());
      chadNovelEngineBackend.updateUser(userId, io);
      var messages = io.getMessages();
      for (var i = 0; i < messages.size() - 1; ++i) {
        try {
          executeMessage(vk,
              actor,
              userId,
              random,
              messages.get(i),
              ownerId,
              Id);
        } catch (FileNotFoundException e) {
          e.printStackTrace();
        } catch (TelegramApiException e) {
          e.printStackTrace();
        } catch (InterruptedException e) {
          e.printStackTrace();
        } catch (ClientException e) {
          e.printStackTrace();
        } catch (ApiException e) {
          e.printStackTrace();
        }
      }

      var buttons = io.getButtons();
      var m = messages.get(messages.size() - 1);
      switch (m.messageType) {
        case IMAGE -> {
          try {
            vk.messages().send(actor).attachment(String.format("photo-200523079_457239018", ownerId, Id))
                .userId(userId)
                .randomId(random.nextInt(10000)).keyboard(buttons).execute();
          } catch (ApiException e) {
            e.printStackTrace();
          } catch (ClientException e) {
            e.printStackTrace();
          }
        }
        case MUSIC -> {
          try {
            vk.messages().send(actor).attachment(String.format("audio-200575158_456239019", ownerId, Id)).userId(userId)
                .randomId(random.nextInt(10000)).keyboard(buttons).execute();
          } catch (ApiException e) {
            e.printStackTrace();
          } catch (ClientException e) {
            e.printStackTrace();
          }
        }
        case VIDEO -> {
          try {
            vk.messages().send(actor).attachment(String.format("video-%d_%d", ownerId, Id)).userId(userId)
                .randomId(random.nextInt(10000)).keyboard(buttons).execute();
          } catch (ApiException e) {
            e.printStackTrace();
          } catch (ClientException e) {
            e.printStackTrace();
          }
        }
        case DOCUMENT -> {
          try {
            vk.messages().send(actor).attachment(String.format("doc-%d_%d", ownerId, Id)).userId(userId)
                .randomId(random.nextInt(10000)).keyboard(buttons).execute();
          } catch (ApiException e) {
            e.printStackTrace();
          } catch (ClientException e) {
            e.printStackTrace();
          }
        }
        default -> {
          try {
            vk.messages().send(actor).message(m.content).userId(userId)
                .randomId(random.nextInt(10000)).keyboard(buttons).execute();
          } catch (ApiException e) {
            e.printStackTrace();
          } catch (ClientException e) {
            e.printStackTrace();
          }
        }
      }  // It's a copy-paste, but it's the best you can do
  }

  private static void executeMessage(VkApiClient vk, GroupActor actor, int userId,
      Random random, com.urfu.chadnovelengine.backendapi.Message m,
      int ownerId, int Id)
      throws FileNotFoundException, TelegramApiException,
      InterruptedException, ClientException, ApiException {
    switch (m.messageType) {
      case IMAGE -> {
        try {
          vk.messages().send(actor).attachment(String.format("photo-200523079_457239018", ownerId, Id))
              .userId(userId)
              .randomId(random.nextInt(10000)).execute();
        } catch (ApiException e) {
          e.printStackTrace();
        } catch (ClientException e) {
          e.printStackTrace();
        }
      }
      case MUSIC -> {
        try {
          vk.messages().send(actor).attachment(String.format("audio-200575158_456239019", ownerId, Id)).userId(userId)
              .randomId(random.nextInt(10000)).execute();
        } catch (ApiException e) {
          e.printStackTrace();
        } catch (ClientException e) {
          e.printStackTrace();
        }
      }
      case VIDEO -> {
        try {
          vk.messages().send(actor).attachment(String.format("video-%d_%d", ownerId, Id)).userId(userId)
              .randomId(random.nextInt(10000)).execute();
        } catch (ApiException e) {
          e.printStackTrace();
        } catch (ClientException e) {
          e.printStackTrace();
        }
      }
      case DOCUMENT -> {
        try {
          vk.messages().send(actor).attachment(String.format("doc-%d_%d", ownerId, Id)).userId(userId)
              .randomId(random.nextInt(10000)).execute();
        } catch (ApiException e) {
          e.printStackTrace();
        } catch (ClientException e) {
          e.printStackTrace();
        }
      }
      default -> {
        try {
          vk.messages().send(actor).message(m.content).userId(userId)
              .randomId(random.nextInt(10000)).execute();
        } catch (ApiException e) {
          e.printStackTrace();
        } catch (ClientException e) {
          e.printStackTrace();
        }
      }
    }
  }
}
