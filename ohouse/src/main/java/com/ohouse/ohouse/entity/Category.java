package com.ohouse.ohouse.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "category")
@Getter
@NoArgsConstructor
public class Category {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "category_id")
  private int categoryId;

  @Column(name = "category_name")
  private String categoryName;

  public Category(String categoryName) {
    this.categoryName = categoryName;
  }
}
