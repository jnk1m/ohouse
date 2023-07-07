package com.ohouse.ohouse.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuOptionDTO {
  private String categoryName;

  private String optionName;

  private int optionId;

  public MenuOptionDTO(String categoryName, String optionName, int optionId) {
    this.categoryName = categoryName;
    this.optionName = optionName;
    this.optionId = optionId;
  }
}
