package com.ohouse.ohouse.domain;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class NewMenuDTO {
  private String menuNameEng;

  private String descriptionEng;

  private String menuNameKor;

  private String descriptionKor;

  private BigDecimal menuPrice;

  private int categoryId;

  private boolean isAvailable;

  private String chitName;


  public NewMenuDTO(String menuNameEng, String descriptionEng, String menuNameKor, String descriptionKor, BigDecimal menuPrice, int categoryId, boolean isAvailable, String chitName) {
    this.menuNameEng = menuNameEng;
    this.descriptionEng = descriptionEng;
    this.menuNameKor = menuNameKor;
    this.descriptionKor = descriptionKor;
    this.menuPrice = menuPrice;
    this.categoryId = categoryId;
    this.isAvailable = isAvailable;
    this.chitName = chitName;
  }
}
