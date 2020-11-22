package com.urfu.chadnovelengine;

import java.util.HashMap;

public class BanManager {
  private final HashMap<Long, Long> banned;


  public BanManager() {
    banned = new HashMap<>();
  }

  public BanManager(HashMap<Long, Long> banned) {
    this.banned = banned;
  }

  public boolean filtered(long chatId, long startTime){
    var currentTime = System.currentTimeMillis();
    var elapsedTime = currentTime - startTime;
    if (banned.containsKey(chatId) && banned.get(chatId) > elapsedTime)
      return true;
    return false;
  }

  public void put(long chatId, long bannedTime){
    banned.put(chatId, bannedTime);
  }
}
