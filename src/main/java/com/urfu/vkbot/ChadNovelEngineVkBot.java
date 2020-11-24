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
import com.vk.api.sdk.objects.messages.Message;
import com.vk.api.sdk.queries.messages.MessagesGetLongPollHistoryQuery;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
public class ChadNovelEngineVkBot {
  private Backend backend;

  private static HashMap<Integer, Long> test(){
    var tmp = new HashMap<Integer, Long>();
    tmp.put(207887738, 1000 * 5 * 60L);
    return tmp;
  }



  public static void main(String[] args)
      throws ClientException, ApiException, InterruptedException, IOException {
    TransportClient transportClient = new HttpTransportClient();
    VkApiClient vk = new VkApiClient(transportClient);
    Random random = new Random();
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
    BanManager banManager = new BanManager(test());

    while (true) {
      MessagesGetLongPollHistoryQuery historyQuery = vk.messages().getLongPollHistory(actor).ts(ts);
      List<Message> messagesHistory = historyQuery.execute().getMessages().getItems();

      if (!messagesHistory.isEmpty()) {
        messagesHistory.forEach(message -> {

          var messageText = message.getText();
          var userId = message.getFromId();
          System.out.println("Vk message from User: " + userId + ", data: " + message.getText());


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
            switch (messageText) {
              default -> {
                handleMessages(userId, messageText, lastMessageTimeUNIX,
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

  private static void handleMessages(int userId, String messageText,
      HashMap<Integer, Long> lastMessageTimeUNIX,
      Backend chadNovelEngineBackend,
      GroupActor actor, Random random,
      int ownerId, int Id, VkApiClient vk){
      var io = new VkIO();
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
