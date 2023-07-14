package com.ohouse.ohouse.domain;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class MenuDTO {
  private final int menuId;

  private final String menuNameEng;

  private final String descriptionEng;

  private final String menuNameKor;

  private final String descriptionKor;

  private final BigDecimal menuPrice;

  private final String imagePath;

  public MenuDTO(int menuId, String menuNameEng, String descriptionEng, String menuNameKor, String descriptionKor, BigDecimal menuPrice, String imagePath) {
    this.menuId = menuId;
    this.menuNameEng = menuNameEng;
    this.descriptionEng = descriptionEng;
    this.menuNameKor = menuNameKor;
    this.descriptionKor = descriptionKor;
    this.menuPrice = menuPrice;
    this.imagePath = imagePath;
  }
}
