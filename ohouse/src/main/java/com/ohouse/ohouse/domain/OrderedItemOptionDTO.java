package com.ohouse.ohouse.domain;

import lombok.Getter;

import java.math.BigDecimal;

/**
 * Represents the selected options for an ordered product.
 * An ordered product can have multiple options. It's embedded within the OrderedItemDTO.
 */

@Getter
public class OrderedItemOptionDTO {
  private final int parentOrderId;
  private final int optionId;
  private final String optionName;
  private final BigDecimal optionPrice;

  public OrderedItemOptionDTO(int parentOrderId, int optionId, String optionName, BigDecimal optionPrice) {
    this.parentOrderId = parentOrderId;
    this.optionId = optionId;
    this.optionName = optionName;
    this.optionPrice = optionPrice;
  }
}
