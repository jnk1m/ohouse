package com.ohouse.ohouse.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "cart")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cart {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "cart_id")
  private int cartId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "google_id")
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "menu_id")
  private Menu menu;

  @Column(name = "quantity")
  private int quantity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "option_id")
  private Option option;

  @Column(name = "order_complete")
  private boolean orderComplete;

  @Builder
  public Cart(User user, Menu menu, int quantity, Option option, boolean orderComplete) {
    this.user = user;
    this.menu = menu;
    this.quantity = quantity;
    this.option = option;
    this.orderComplete = orderComplete;
  }
}
