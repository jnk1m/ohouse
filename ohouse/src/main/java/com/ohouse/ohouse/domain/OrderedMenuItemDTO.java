package com.ohouse.ohouse.domain;

import lombok.Getter;

import java.math.BigDecimal;

/* Represents specific information about an ordered menu item.*/

@Getter
public class OrderedMenuItemDTO {
  private final int menuId;
  private final String menuNameEng;
  private final BigDecimal menuPrice;

  public OrderedMenuItemDTO(int menuId, String menuNameEng, BigDecimal menuPrice) {
    this.menuId = menuId;
    this.menuNameEng = menuNameEng;
    this.menuPrice = menuPrice;
  }
}
