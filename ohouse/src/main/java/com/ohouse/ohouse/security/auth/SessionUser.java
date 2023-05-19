package com.ohouse.ohouse.security.auth;

import com.ohouse.ohouse.entity.User;
import lombok.Getter;

@Getter
public class SessionUser {
  private String name;
  private String email;

  public SessionUser(User user) {
    this.name = user.getUserName();
    this.email = user.getEmail();
  }
}
