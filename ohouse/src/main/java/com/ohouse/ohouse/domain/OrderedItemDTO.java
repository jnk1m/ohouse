package com.ohouse.ohouse.domain;

import lombok.Getter;

/**
 * Represents a single product ordered by the customer.
 * In cases where multiple products are ordered, they are managed and displayed in a list format.
 */

@Getter
public class OrderedItemDTO {
  private final int orderItemId;
  private final int orderId;
  private final int menuId;
  private final int quantity;

  public OrderedItemDTO(int orderItemId, int orderId, int menuId, int quantity) {
    this.orderItemId = orderItemId;
    this.orderId = orderId;
    this.menuId = menuId;
    this.quantity = quantity;
  }
}
