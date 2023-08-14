package com.ohouse.ohouse.entity;

import com.ohouse.ohouse.enums.OrderStatus;
import com.ohouse.ohouse.enums.PaymentMethod;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;

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

  @Column(name = "price")
  @NotNull
  private BigDecimal price;

  @Column(name = "payment_method")
  @Enumerated(EnumType.STRING)
  @NotNull
  private PaymentMethod paymentMethod;

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

  @Column(name = "name")
  @NotNull
  private String name;

  @Column(name = "order_number")
  @NotNull
  private String orderNumber;

  @Column(name = "order_date")
  @NotNull
  private ZonedDateTime orderDate;

  @Column(name = "order_timezone")
  @NotNull
  private String orderTimezone;

  @Builder
  public Order(User user, BigDecimal price, PaymentMethod paymentMethod, String deliveryAddress, String deliveryContact, String specialInstruction, OrderStatus orderStatus, String name, String orderNumber) {
    this.user = user;
    this.price = price;
    this.paymentMethod = paymentMethod;
    this.deliveryAddress = deliveryAddress;
    this.deliveryContact = deliveryContact;
    this.specialInstruction = specialInstruction;
    this.orderStatus = orderStatus;
    this.name = name;
    this.orderNumber = orderNumber;
    this.orderDate = ZonedDateTime.now(ZoneId.of("UTC"));
    this.orderTimezone = ZonedDateTime.now().getZone().toString();
  }

  public void updateOrderStatus(OrderStatus newOrderStatus) {
    this.orderStatus = newOrderStatus;
  }
}
