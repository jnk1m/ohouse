package com.ohouse.ohouse.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class UserDTO implements Serializable {
  private static final long serialVersionUID = 1L;

  private int userId;

  private String userName;

  private String email;


  public UserDTO(int userId, String userName, String email) {
    this.userId = userId;
    this.userName = userName;
    this.email = email;
  }
}
