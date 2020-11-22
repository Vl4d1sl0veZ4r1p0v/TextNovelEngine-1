package com.urfu.chadnovelengine;

import java.util.HashSet;

public class ContentManagementSystem {
  private HashSet<Long> set;
  private UsersInformationStorage usersInformationStorage;

  public ContentManagementSystem(Backend backend) {
    this.set = new HashSet<>();
    this.usersInformationStorage = new UsersInformationStorage(backend);
  }

  public String getLastInformation(int userId, long chatId){
    return this.usersInformationStorage.getLastUserInformation(userId, chatId);
  }


}
