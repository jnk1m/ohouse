package com.ohouse.ohouse.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "order_item_option")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItemOption {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "order_item_option_id")
  private int orderItemOptionId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_item_id")
  @NotNull
  private OrderItem orderItem;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "option_id")
  @NotNull
  private Options option;
}
