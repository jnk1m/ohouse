package com.ohouse.ohouse.domain;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class CartOptionDTO {
  private final int parentCartId;
  private final int optionId;
  private final String optionName;
  private final BigDecimal optionPrice;

  public CartOptionDTO(int parentCartId, int optionId, String optionName, BigDecimal optionPrice) {
    this.parentCartId = parentCartId;
    this.optionId = optionId;
    this.optionName = optionName;
    this.optionPrice = optionPrice;
  }
}
