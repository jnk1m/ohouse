package com.ohouse.ohouse.entity;

import com.ohouse.ohouse.enums.OrderStatus;
import lombok.*;

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
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "order_id")
  private Long orderId;

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

  @Column(name = "order_status")
  @Enumerated(EnumType.STRING)
  @NotNull
  private OrderStatus orderStatus;

  @Column(name = "payment_method")
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
  public Order(User user, BigDecimal orderPrice, OrderStatus orderStatus, String paymentMethod, String specialInstruction, String deliveryAddress, String deliveryPhone) {
    this.user = user;
    this.orderPrice = orderPrice;
    this.orderStatus = orderStatus;
    this.paymentMethod = paymentMethod;
    this.specialInstruction = specialInstruction;
    this.deliveryAddress = deliveryAddress;
    this.deliveryPhone = deliveryPhone;
  }


}


/*Builder Example:
Order order = Order.builder()
        .user(userInstance)
        .orderPrice(new BigDecimal("100.00"))
        .orderStatus(OrderStatus.CREATED)
        .paymentMethod("credit")
        .specialInstruction("Leave it at the door")
        .deliveryAddress("123 Main St")
        .deliveryPhone("555-123-4567")
        .build();
 */
