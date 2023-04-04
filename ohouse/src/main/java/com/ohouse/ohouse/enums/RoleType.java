package com.ohouse.ohouse.enums;

public enum RoleType {
  USER(0),
  ADMIN(1);

  private final int roleNum;

  RoleType(int roleNum) {
    this.roleNum = roleNum;
  }

  public int getRoleNum() {
    return roleNum;
  }
}
