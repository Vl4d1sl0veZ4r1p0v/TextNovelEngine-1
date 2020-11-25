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

  public void addAdmin(Integer adminId, Integer newAdminId){
      admins.add(newAdminId);
  }

  public boolean isBanned(Integer userId){
    if (banned.contains(userId))
      return true;
    return false;
  }

  public void banUserById(Integer userId){
      banned.add(userId);
  }

  public void unbanUserById(Integer userId){
    banned.remove(userId);
  }
}
