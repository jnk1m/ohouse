package com.ohouse.ohouse.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "order")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {
  @EmbeddedId
  private OrderId orderId;

  @MapsId("googleId")
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
  public Order(OrderId orderId, User user, BigDecimal orderPrice, String paymentMethod, String specialInstruction, String deliveryAddress, String deliveryPhone) {
    this.orderId = orderId;
    this.user = user;
    this.orderPrice = orderPrice;
    this.paymentMethod = paymentMethod;
    this.specialInstruction = specialInstruction;
    this.deliveryAddress = deliveryAddress;
    this.deliveryPhone = deliveryPhone;
  }
}

@Embeddable
class OrderId implements Serializable {
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "order_id")
  private Long orderId;

  @Column(name = "google_id")
  private String googleId;
}

/*Builder Example:
Order order = Order.builder()
        .orderId(new OrderId(1L, "googleId123"))
        .user(userInstance)
        .orderPrice(new BigDecimal("100.00"))
        .paymentMethod("credit")
        .specialInstruction("Leave it at the door")
        .deliveryAddress("123 Main St")
        .deliveryPhone("555-123-4567")
        .build();
 */
