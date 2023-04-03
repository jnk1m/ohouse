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
public class Option {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "option_id")
  private Long optionId;

  @Column(name = "option_name")
  @NotNull
  private String name;

  @Column(name = "option_price")
  @NotNull
  private BigDecimal optionPrice;

  public Option(String name, BigDecimal optionPrice) {
    this.name = name;
    this.optionPrice = optionPrice;
  }
}
