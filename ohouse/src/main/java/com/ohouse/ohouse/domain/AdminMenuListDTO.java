package com.ohouse.ohouse.domain;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class AdminMenuListDTO {
  private final int menuId;
  private final String menuNameEng;
  private final BigDecimal menuPrice;
  private final String categoryName;
  private final boolean isAvailable;

  public AdminMenuListDTO(int menuId, String menuNameEng, BigDecimal menuPrice, String categoryName, boolean isAvailable) {
    this.menuId = menuId;
    this.menuNameEng = menuNameEng;
    this.menuPrice = menuPrice;
    this.categoryName = categoryName;
    this.isAvailable = isAvailable;
  }
}
