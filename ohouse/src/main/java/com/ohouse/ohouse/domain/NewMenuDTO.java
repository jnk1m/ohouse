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

  private String imagePath;

  private String chitName;
}
