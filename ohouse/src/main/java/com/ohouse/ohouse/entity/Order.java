package com.ohouse.ohouse.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "order")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "order_id")
  private int orderId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "google_id")
  @NotNull
  private User user;

  /*The current time is set as the default value in the database (CURRENT_TIMESTAMP)*/
  @Column(name = "order_date")
  private LocalDateTime orderDate;

  @Column(name = "order_price")
  @NotNull
  private BigDecimal orderPrice;

  @Column(name = "order_state")
  @NotNull
  private String paymentMethod;

  /*nullable*/
  @Column(name = "special_instruction")
  private String specialInstruction;

  @Column(name = "delivery_address")
  @NotNull
  private String deliveryAddress;

  @Column(name = "delivery_phone")
  @NotNull
  private String deliveryPhone;

  @Builder
  public Order(User user, BigDecimal orderPrice, String paymentMethod,
               String specialInstruction, String deliveryAddress, String deliveryPhone) {
    this.user = user;
    this.orderPrice = orderPrice;
    this.paymentMethod = paymentMethod;
    this.specialInstruction = specialInstruction;
    this.deliveryAddress = deliveryAddress;
    this.deliveryPhone = deliveryPhone;
  }

}
