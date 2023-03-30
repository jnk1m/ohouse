package com.ohouse.ohouse.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user")
@Getter
@NoArgsConstructor
public class User {
  @Id
  @Column(name = "google_id")
  private String googleId;

  @Column(name = "phone_number")
  private String phoneNumber;

  @Column(name = "email")
  private String email;

  @Column(name = "is_verified")
  private boolean isVerified;

  @Column(name = "auth")
  private boolean auth;

  @Column(name = "order_count")
  private int orderCount;

  @Column(name = "free_side")
  private boolean freeSide;

  public User(String googleId, String phoneNumber, String email, boolean isVerified, boolean auth, int orderCount, boolean freeSide) {
    this.googleId = googleId;
    this.phoneNumber = phoneNumber;
    this.email = email;
    this.isVerified = isVerified;
    this.auth = auth;
    this.orderCount = orderCount;
    this.freeSide = freeSide;
  }
}


