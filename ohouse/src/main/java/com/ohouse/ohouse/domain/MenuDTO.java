package com.ohouse.ohouse.domain;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class MenuDTO {
  private int menuId;

  private String menuNameEng;

  private String descriptionEng;

  private String menuNameKor;

  private String descriptionKor;

  private BigDecimal menuPrice;

  private String imagePath;

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
