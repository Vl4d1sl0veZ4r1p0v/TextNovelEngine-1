package com.urfu.chadnovelengine;

import java.util.Map;

public class UsersInformationStorage {
  private Backend backend;

  public UsersInformationStorage(Backend backend) {
    this.backend = backend;
  }

  public String getLastUserInformation(int userId, long chatId){
    var user = backend.users.getUser(userId);
    var currentNode = user.getCurrentNodeIndex();
    var currentScript = user.getCurrentScript();
    var result = new StringBuilder();
    result.append("User ID: "+userId);
    result.append("Current node: "+Integer.toString(currentNode)+"\n");
    result.append("Current script: "+currentScript+"\n");
    return result.toString();
  }

}
