package com.urfu.vkbot;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.messages.Keyboard;
import com.vk.api.sdk.objects.messages.KeyboardButton;
import com.vk.api.sdk.objects.messages.KeyboardButtonAction;
import com.vk.api.sdk.objects.messages.KeyboardButtonActionType;
import com.vk.api.sdk.objects.messages.KeyboardButtonColor;
import com.vk.api.sdk.objects.messages.Message;
import com.vk.api.sdk.queries.messages.MessagesGetLongPollHistoryQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class VkBot {

  public VkBot() throws ClientException, ApiException, InterruptedException {
    TransportClient transportClient = new HttpTransportClient();
    VkApiClient vk = new VkApiClient(transportClient);

    Random random = new Random();

    Keyboard keyboard = new Keyboard();

    List<KeyboardButton> line1 = new ArrayList<>();
    line1.add(new KeyboardButton().setAction(new KeyboardButtonAction().setLabel("Button1").
        setType(KeyboardButtonActionType.TEXT)).
        setColor(KeyboardButtonColor.POSITIVE));
    line1.add(new KeyboardButton().setAction(new KeyboardButtonAction().setLabel("Button2").
        setType(KeyboardButtonActionType.TEXT)).
        setColor(KeyboardButtonColor.NEGATIVE));

    List<List<KeyboardButton>> allButtons = new ArrayList<>();
    allButtons.add(line1);
    keyboard.setButtons(allButtons);

    GroupActor actor = new GroupActor(200523079, "9e9910a329bee4a9d77346ba8112445d3d6004b066edfec84d2dff1dc3eee76a43a35d261163b14a0b39e");
    Integer ts = vk.messages().getLongPollServer(actor).execute().getTs();
    while (true){
      MessagesGetLongPollHistoryQuery historyQuery = vk.messages().getLongPollHistory(actor).ts(ts);
      List<Message> messages = historyQuery.execute().getMessages().getItems();
      if (!messages.isEmpty()) {
        messages.forEach(message -> {
          System.out.println(message.getText());
          try {
            vk.messages().send(actor).message("Test").userId(message.getFromId()).
                randomId((random.nextInt(10000))).
                keyboard(keyboard).execute();
          } catch (ApiException | ClientException e) {
            e.printStackTrace();
          }
        });
      }
      ts = vk.messages().getLongPollServer(actor).execute().getTs();
      Thread.sleep(500);
    }
  }
}
