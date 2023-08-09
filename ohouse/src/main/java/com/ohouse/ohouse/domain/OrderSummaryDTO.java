package com.ohouse.ohouse.domain;

import com.ohouse.ohouse.enums.OrderStatus;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

/**
 * Represents the summary of customer orders for the MyPage.
 * Primarily used for data retrieval and not directly displayed on the screen.
 */

@Getter
public class OrderSummaryDTO {
  private final String orderNumber;
  private final ZonedDateTime orderDate;
  private final String orderTimezone;
  private final OrderStatus orderStatus;
  private final BigDecimal orderPrice;

  public OrderSummaryDTO(String orderNumber, ZonedDateTime orderDate, String orderTimezone, OrderStatus orderStatus, BigDecimal orderPrice) {
    this.orderNumber = orderNumber;
    this.orderDate = orderDate;
    this.orderTimezone = orderTimezone;
    this.orderStatus = orderStatus;
    this.orderPrice = orderPrice;
  }
}
