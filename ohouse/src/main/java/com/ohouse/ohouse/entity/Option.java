package com.ohouse.ohouse.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "options")
@Getter
@NoArgsConstructor
public class Option {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "option_id")
  private Long optionId;

  @Column(name = "option_name")
  private String name;

  @Column(name = "option_price")
  private BigDecimal optionPrice;

  public Option(String name, BigDecimal optionPrice) {
    this.name = name;
    this.optionPrice = optionPrice;
  }
}
