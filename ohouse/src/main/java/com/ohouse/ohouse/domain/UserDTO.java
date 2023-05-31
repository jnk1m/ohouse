package com.ohouse.ohouse.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
  private String userName;

  private String phoneNumber;

  private String email;

  private boolean isPhoneVerified;

  public UserDTO(String userName, String phoneNumber, String email, boolean isPhoneVerified) {
    this.userName = userName;
    this.phoneNumber = phoneNumber;
    this.email = email;
    this.isPhoneVerified = isPhoneVerified;
  }
}
