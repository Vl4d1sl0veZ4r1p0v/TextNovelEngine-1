package com.urfu.chadnovelengine;

import java.util.Set;

public class BanManager {
  private final Set<Integer> banned;
  private final Set<Integer> admins;

  public BanManager(Set<Integer> banned, Set<Integer> admins) {
    this.banned = banned;
    this.admins = admins;
  }

  public boolean isAdmin(Integer userId){
    return admins.contains(userId);
  }

  public boolean isBanned(Integer userId){
    return banned.contains(userId);
  }

  public void banUserById(Integer userId){
      banned.add(userId);
  }

  public void unbanUserById(Integer userId){
    banned.remove(userId);
  }
}
