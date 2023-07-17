package com.ohouse.ohouse.domain;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class UserDTO implements Serializable {
  private static final long serialVersionUID = 1L;

  private final int userId;

  private final String userName;

  private final String email;

  public UserDTO(int userId, String userName, String email) {
    this.userId = userId;
    this.userName = userName;
    this.email = email;
  }
}
