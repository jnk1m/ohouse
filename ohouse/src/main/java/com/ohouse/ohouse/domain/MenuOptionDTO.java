package com.ohouse.ohouse.domain;

import lombok.Getter;

@Getter
public class MenuOptionDTO {
  private final String categoryName;

  private final String optionName;

  private final int optionId;

  public MenuOptionDTO(String categoryName, String optionName, int optionId) {
    this.categoryName = categoryName;
    this.optionName = optionName;
    this.optionId = optionId;
  }
}
