package com.ohouse.ohouse.domain;

import lombok.Getter;

import java.math.BigDecimal;

/**
 * Represents the selected options for an ordered product.
 * An ordered product can have multiple options. It's embedded within the OrderedItemDTO.
 */

@Getter
public class OrderedItemOptionDTO {
  private final int parentOrderItemId;
  private final String optionName;
  private final BigDecimal optionPrice;

  public OrderedItemOptionDTO(int parentOrderItemId, String optionName, BigDecimal optionPrice) {
    this.parentOrderItemId = parentOrderItemId;
    this.optionName = optionName;
    this.optionPrice = optionPrice;
  }
}
