package com.ohouse.ohouse.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCartDTO {
  /*Cart*/
  private int cartId;

  private int menuId;

  private String menuName;

  private int quantity;

  /*Cart Option*/
  /*
   This field can either receive options or be left empty, depending on the menu.
   It is declared as an Integer type since it is a nullable field.
   */

  private Integer optionId;

  private String optionName;

  private Double optionPrice;

  public UserCartDTO(int cartId, int menuId, String menuName, int quantity, Integer optionId, String optionName, Double optionPrice) {
    this.cartId = cartId;
    this.menuId = menuId;
    this.menuName = menuName;
    this.quantity = quantity;
    this.optionId = optionId;
    this.optionName = optionName;
    this.optionPrice = optionPrice;
  }
}
