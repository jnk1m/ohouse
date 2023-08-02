package com.ohouse.ohouse.domain;

import com.ohouse.ohouse.enums.OrderStatus;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Getter
public class OrderListDTO {
  private final String orderNumber;
  private final ZonedDateTime orderDate;
  private final String orderTimezone;
  private final OrderStatus orderStatus;
  private final BigDecimal orderPrice;

  public OrderListDTO(String orderNumber, ZonedDateTime orderDate, String orderTimezone, OrderStatus orderStatus, BigDecimal orderPrice) {
    this.orderNumber = orderNumber;
    this.orderDate = orderDate;
    this.orderTimezone = orderTimezone;
    this.orderStatus = orderStatus;
    this.orderPrice = orderPrice;
  }
}
