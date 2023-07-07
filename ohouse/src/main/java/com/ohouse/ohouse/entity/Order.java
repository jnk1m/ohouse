package com.ohouse.ohouse.entity;

import com.ohouse.ohouse.enums.OrderStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "order_id")
  private int orderId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  @NotNull
  private User user;

  /*The current time is set as the default value in the database (CURRENT_TIMESTAMP)*/
  @Column(name = "order_date")
  private LocalDateTime orderDate;

  @Column(name = "price")
  @NotNull
  private BigDecimal price;

  @Column(name = "payment_method")
  @NotNull
  private String paymentMethod;

  @Column(name = "delivery_address")
  @NotNull
  private String deliveryAddress;

  @Column(name = "delivery_contact")
  @NotNull
  private String deliveryContact;

  /*nullable*/
  @Column(name = "special_instruction")
  private String specialInstruction;

  @Column(name = "order_status")
  @Enumerated(EnumType.STRING)
  @NotNull
  private OrderStatus orderStatus;

  @Builder
  public Order(User user, BigDecimal price, String paymentMethod, String deliveryAddress, String deliveryContact, String specialInstruction, OrderStatus orderStatus) {
    this.user = user;
    this.price = price;
    this.paymentMethod = paymentMethod;
    this.deliveryAddress = deliveryAddress;
    this.deliveryContact = deliveryContact;
    this.specialInstruction = specialInstruction;
    this.orderStatus = orderStatus;
  }
}
