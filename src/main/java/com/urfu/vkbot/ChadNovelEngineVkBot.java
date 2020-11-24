package com.urfu.vkbot;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.messages.Message;
import com.vk.api.sdk.queries.messages.MessagesGetLongPollHistoryQuery;
import java.io.IOException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ChadNovelEngineVkBot {

  public static void main(String[] args)
      throws ClientException, ApiException, InterruptedException, IOException {
    TransportClient transportClient = new HttpTransportClient();
    VkApiClient vk = new VkApiClient(transportClient);
    GroupActor actor = new GroupActor(
        200523079,
        "9e9910a329bee4a9d77346ba8112445d3d6004b066edfec84d2dff1dc3eee76a43a35d261163b14a0b39e"
    );
    Integer ts = vk.messages().getLongPollServer(actor).execute().getTs();
    var messageHandler = new MessageHandler(vk, actor);
    while (true) {
      MessagesGetLongPollHistoryQuery historyQuery = vk.messages()
          .getLongPollHistory(actor)
          .ts(ts);
      List<Message> messagesHistory = historyQuery
          .execute()
          .getMessages()
          .getItems();
      if (!messagesHistory.isEmpty()) {
        messagesHistory.forEach(message -> {
          try {
            messageHandler.handleMessage(message);
          } catch (ClientException e) {
            e.printStackTrace();
          } catch (ApiException e) {
            e.printStackTrace();
          }
        });
      }
      ts = vk.messages()
          .getLongPollServer(actor)
          .execute()
          .getTs();
      Thread.sleep(500);
    }
  }
}
