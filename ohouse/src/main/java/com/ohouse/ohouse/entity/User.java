package com.ohouse.ohouse.entity;

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
  @Column(name = "google_id")
  @NotNull
  private String googleId;

  @Column(name = "phone_number")
  @NotNull
  private String phoneNumber;

  @Column(name = "email")
  @NotNull
  private String email;

  @Column(name = "is_verified")
  @NotNull
  private boolean isVerified;

  @Column(name = "auth")
  @NotNull
  private boolean auth;

  @Column(name = "order_count")
  private int orderCount;

  @Column(name = "free_side")
  private boolean freeSide;

  @Builder
  private User(String googleId, String phoneNumber, String email, boolean isVerified, boolean auth) {
    this.googleId = googleId;
    this.phoneNumber = phoneNumber;
    this.email = email;
    this.isVerified = isVerified;
    this.auth = auth;
  }

}


