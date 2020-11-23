package com.urfu.vkbot;

import com.urfu.chadnovelengine.Backend;
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
import java.util.List;
import java.util.Random;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
public class ChadNovelEngineVkBot {
  private Backend backend;

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

    while (true) {
      MessagesGetLongPollHistoryQuery historyQuery = vk.messages().getLongPollHistory(actor).ts(ts);
      List<Message> messagesHistory = historyQuery.execute().getMessages().getItems();

      if (!messagesHistory.isEmpty()) {
        messagesHistory.forEach(message -> {

          var messageText = message.getText();
          var userId = message.getFromId();
          log.info("Vk message from User: {}, data {}", userId, message.getText());

          var io = new VkIO();
          io.setUserAnswer(messageText);
          chadNovelEngineBackend.updateUser(userId, io);

          var messages = io.getMessages();

          for (var i = 0; i < messages.size() - 1; ++i) {
            try {
              executeMessage(vk,
                  actor,
                  userId,
                  random,
                  messages.get(i));
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
                vk.messages().send(actor).attachment("photo-200523079_457239018").userId(userId).randomId(random.nextInt(10000)).keyboard(buttons).execute();
              } catch (ApiException e) {
                e.printStackTrace();
              } catch (ClientException e) {
                e.printStackTrace();
              }
              try {
                vk.messages().send(actor).attachment("photo-200523079_457239018").userId(userId).randomId(random.nextInt(10000)).keyboard(buttons).execute();
              } catch (ApiException e) {
                e.printStackTrace();
              } catch (ClientException e) {
                e.printStackTrace();
              }
            }
            case MUSIC, VIDEO, DOCUMENT -> {
              vk.photos();
              try {
                vk.messages().send(actor).attachment("photo-200523079_457239018").userId(userId).randomId(random.nextInt(10000)).keyboard(buttons).execute();
              } catch (ApiException e) {
                e.printStackTrace();
              } catch (ClientException e) {
                e.printStackTrace();
              }
            }
            default -> {
              try {
                vk.messages().send(actor).message(m.content).userId(userId).randomId(random.nextInt(10000)).keyboard(buttons).execute();
              } catch (ApiException e) {
                e.printStackTrace();
              } catch (ClientException e) {
                e.printStackTrace();
              }
            }
          }  // It's a copy-paste, but it's the best you can do


        });
      }
      ts = vk.messages().getLongPollServer(actor).execute().getTs();
      Thread.sleep(500);
    }
  }

  private static void executeMessage(VkApiClient vk, GroupActor actor, int userId, Random random, com.urfu.chadnovelengine.backendapi.Message m)
      throws FileNotFoundException, TelegramApiException, InterruptedException, ClientException, ApiException {
    switch (m.messageType) {
      case IMAGE,MUSIC, VIDEO, DOCUMENT -> {
        try {
          vk.messages().send(actor).attachment("photo-200523079_457239018").userId(userId).randomId(random.nextInt(10000)).execute();
        } catch (ApiException e) {
          e.printStackTrace();
        } catch (ClientException e) {
          e.printStackTrace();
        }
      }
      default -> {
        try {
          vk.messages().send(actor).message(m.content).userId(userId).randomId(random.nextInt(10000)).execute();
        } catch (ApiException e) {
          e.printStackTrace();
        } catch (ClientException e) {
          e.printStackTrace();
        }
      }
    }
  }
}
