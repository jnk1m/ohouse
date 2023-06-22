package com.ohouse.ohouse.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
  private Long userId;

  private String userName;

  private String phoneNumber;

  private String email;

  private boolean isPhoneVerified;

  public UserDTO(Long userId, String userName, String phoneNumber, String email, boolean isPhoneVerified) {
    this.userId = userId;
    this.userName = userName;
    this.phoneNumber = phoneNumber;
    this.email = email;
    this.isPhoneVerified = isPhoneVerified;
  }
}
