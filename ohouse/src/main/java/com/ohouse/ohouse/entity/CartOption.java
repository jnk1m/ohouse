package com.ohouse.ohouse.entity;

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
public class CartOption {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "cart_option_id")
  private int cartOptionId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "cart_id")
  @NotNull
  private Cart cart;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "option_id")
  @NotNull
  private Options option;

  @Builder
  public CartOption(Cart cart, Options option) {
    this.cart = cart;
    this.option = option;
  }
}
