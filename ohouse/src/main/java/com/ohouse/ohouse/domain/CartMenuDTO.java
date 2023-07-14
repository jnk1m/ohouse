package com.ohouse.ohouse.domain;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class CartMenuDTO {
  private final int menuId;
  private final String menuNameEng;
  private final BigDecimal menuPrice;

  public CartMenuDTO(int menuId, String menuNameEng, BigDecimal menuPrice) {
    this.menuId = menuId;
    this.menuNameEng = menuNameEng;
    this.menuPrice = menuPrice;
  }
}
