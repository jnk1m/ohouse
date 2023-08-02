package com.ohouse.ohouse.domain;

import com.ohouse.ohouse.enums.OrderStatus;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class FormattedOrderListDTO {
  private final String orderNumber;
  private final String orderDate;
  private final OrderStatus orderStatus;
  private final BigDecimal orderPrice;

  public FormattedOrderListDTO(String orderNumber, String orderDate, OrderStatus orderStatus, BigDecimal orderPrice) {
    this.orderNumber = orderNumber;
    this.orderDate = orderDate;
    this.orderStatus = orderStatus;
    this.orderPrice = orderPrice;
  }
}