package com.ohouse.ohouse.domain;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a single product ordered by the customer.
 * In cases where multiple products are ordered, they are managed and displayed in a list format.
 */

@Getter
public class OrderedItemDTO {
  private final int orderItemId;
  private final int orderId;
  private final int quantity;
  private final OrderedMenuItemDTO menu;
  private List<OrderedItemOptionDTO> options = new ArrayList<>();

  public OrderedItemDTO(int orderItemId, int orderId, int quantity, int menuId, String menuNameEng, BigDecimal menuPrice) {
    this.orderItemId = orderItemId;
    this.orderId = orderId;
    this.quantity = quantity;
    this.menu = new OrderedMenuItemDTO(menuId, menuNameEng, menuPrice);
  }
}
