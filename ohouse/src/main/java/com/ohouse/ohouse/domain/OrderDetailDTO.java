package com.ohouse.ohouse.domain;

import com.ohouse.ohouse.enums.OrderStatus;
import com.ohouse.ohouse.enums.PaymentMethod;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Getter
public class OrderDetailDTO {
  private final int orderId;
  private final BigDecimal price;
  private final PaymentMethod paymentMethod;
  private final String deliveryAddress;
  private final String deliveryContact;
  private final String specialInstruction;
  private final OrderStatus orderStatus;
  private final String name;
  private final ZonedDateTime orderDate;

  public OrderDetailDTO(int orderId, BigDecimal price, PaymentMethod paymentMethod, String deliveryAddress, String deliveryContact, String specialInstruction, OrderStatus orderStatus, String name, ZonedDateTime orderDate) {
    this.orderId = orderId;
    this.price = price;
    this.paymentMethod = paymentMethod;
    this.deliveryAddress = deliveryAddress;
    this.deliveryContact = deliveryContact;
    this.specialInstruction = specialInstruction;
    this.orderStatus = orderStatus;
    this.name = name;
    this.orderDate = orderDate;
  }
}
