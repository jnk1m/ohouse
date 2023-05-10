package com.ohouseab.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "cart")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cart {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "cart_id")
  private Long cartId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "google_id")
  @NotNull
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "menu_id")
  @NotNull
  private Menu menu;

  @Column(name = "quantity")
  @NotNull
  private int quantity;

  /*nullable*/
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "option_id")
  private Options options;

  @Column(name = "order_complete")
  @NotNull
  private boolean orderComplete;

  @Builder
  public Cart(User user, Menu menu, int quantity, Options options, boolean orderComplete) {
    this.user = user;
    this.menu = menu;
    this.quantity = quantity;
    this.options = options;
    this.orderComplete = orderComplete;
  }
}
