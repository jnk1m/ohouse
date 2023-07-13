package com.ohouse.ohouse.domain;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class CartOptionDTO {
  private int parentCartId;
  private int optionId;
  private String optionName;
  private BigDecimal optionPrice;

  public CartOptionDTO(int parentCartId, int optionId, String optionName, BigDecimal optionPrice) {
    this.parentCartId = parentCartId;
    this.optionId = optionId;
    this.optionName = optionName;
    this.optionPrice = optionPrice;
  }
}
