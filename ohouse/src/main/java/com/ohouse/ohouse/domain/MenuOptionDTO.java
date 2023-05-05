package com.ohouse.ohouse.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuOptionDTO {
  private String categoryName;

  private String optionName;

  public MenuOptionDTO(String categoryName, String optionName) {
    this.categoryName = categoryName;
    this.optionName = optionName;
  }
}
