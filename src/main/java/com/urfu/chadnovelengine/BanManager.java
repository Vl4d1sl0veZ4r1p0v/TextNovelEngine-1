package com.urfu.chadnovelengine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BanManager {
  private final HashMap<Integer, Long> banned;
  private final List<Integer> admins;

  public BanManager(HashMap<Integer, Long> banned, List<Integer> admins) {
    this.banned = banned;
    this.admins = admins;
  }

  public void addAdmin(Integer admin, Integer newAdmin){
    if (admins.contains(admin))
      admins.add(newAdmin);
  }

  public boolean filtered(long chatId, long startTime){
    var currentTime = System.currentTimeMillis();
    var elapsedTime = currentTime - startTime;
    if (banned.containsKey(chatId) && banned.get(chatId) > elapsedTime)
      return true;
    return false;
  }

  public void put(Integer adminId, Integer chatId, long bannedTime){
    if (admins.contains(adminId))
      banned.put(chatId, bannedTime);
  }
}
