package com.ohouse.ohouse.entity;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "user")
@Getter
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

}


