package com.ohouse.ohouse.domain;

import com.ohouse.ohouse.enums.OrderStatus;
import lombok.Getter;

import java.math.BigDecimal;

/**
 * Represents the order summary for display purposes on the MyPage.
 * Contains formatted order date for a more readable presentation.
 */

@Getter
public class OrderSummaryDisplayDTO {
  private final String orderNumber;
  private final String orderDate;
  private final OrderStatus orderStatus;
  private final BigDecimal orderPrice;

  public OrderSummaryDisplayDTO(String orderNumber, String orderDate, OrderStatus orderStatus, BigDecimal orderPrice) {
    this.orderNumber = orderNumber;
    this.orderDate = orderDate;
    this.orderStatus = orderStatus;
    this.orderPrice = orderPrice;
  }
}