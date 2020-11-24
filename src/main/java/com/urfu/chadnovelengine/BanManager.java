package com.urfu.chadnovelengine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class BanManager {
  private final Set<Integer> banned;
  private final ArrayList<Integer> admins;

  public BanManager(Set<Integer> banned, ArrayList<Integer> admins) {
    this.banned = banned;
    this.admins = admins;
  }

  public void addAdmin(Integer admin, Integer newAdmin){
    if (admins.contains(admin))
      admins.add(newAdmin);
  }

  public boolean filtered(long chatId, long startTime){
    if (banned.containsKey(chatId))
      return true;
    return false;
  }

  public boolean isAdmin(Integer chatId){
    return admins.contains(chatId);
  }

  public void put(Integer adminId, Integer chatId, long bannedTime){
    if (admins.contains(adminId))
      banned.put(chatId, bannedTime);
  }

  public void put(Integer adminId, Integer chatId){
    if (admins.contains(adminId))
      banned.put(chatId);
  }
}
