package com.ohouse.ohouse.domain;

import com.ohouse.ohouse.enums.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
  private Long userID;

  private String userName;

  private String phoneNumber;

  private String email;

  private boolean isPhoneVerified;

  private Role role;

  private int orderCount;

  private boolean freeSide;

  public UserDTO(Long userID, String userName, String phoneNumber, String email, boolean isPhoneVerified, Role role, int orderCount, boolean freeSide) {
    this.userID = userID;
    this.userName = userName;
    this.phoneNumber = phoneNumber;
    this.email = email;
    this.isPhoneVerified = isPhoneVerified;
    this.role = role;
    this.orderCount = orderCount;
    this.freeSide = freeSide;
  }
}
