package com.ohouse.ohouse.entity;

import com.ohouse.ohouse.enums.Role;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
  @Id
  @Column(name = "user_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long userId;

  @Column(name = "user_name")
  @NotNull
  private String userName;

  @Column(name = "phone_number")
  private String phoneNumber;

  @Column(name = "email")
  @NotNull
  private String email;

  @Column(name = "is_phone_verified")
  @NotNull
  private boolean isPhoneVerified;

  @Column(name = "user_role")
  @Enumerated(EnumType.STRING)
  @NotNull
  private Role role;

  @Column(name = "order_count")
  private int orderCount;

  @Column(name = "free_side")
  private boolean freeSide;

  @Builder
  private User(String userName, String phoneNumber, String email, boolean isPhoneVerified, Role role) {
    this.userName = userName;
    this.phoneNumber = phoneNumber;
    this.email = email;
    this.isPhoneVerified = isPhoneVerified;
    this.role = role;
  }

  /*Builder Example
  User user = User.builder()
          .userName("exampleUserName")
          .phoneNumber("01012345678")
          .email("example@example.com")
          .isPhoneVerified(true)
          .userRole(ROLE.USER)
          .build();

   */

  public String getRoleKey() {
    return this.role.getKey();
  }

  public User update(String userName) {
    this.userName = userName;
    return this;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public void setPhoneVerified(boolean phoneVerified) {
    isPhoneVerified = phoneVerified;
  }
}


