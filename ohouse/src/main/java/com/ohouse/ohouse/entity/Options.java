package com.ohouse.ohouse.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "options")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Options {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "option_id")
  private int optionId;

  @Column(name = "option_name")
  @NotNull
  private String optionName;

  @Column(name = "option_price")
  @NotNull
  private BigDecimal optionPrice;

  public Options(String optionName, BigDecimal optionPrice) {
    this.optionName = optionName;
    this.optionPrice = optionPrice;
  }
}
