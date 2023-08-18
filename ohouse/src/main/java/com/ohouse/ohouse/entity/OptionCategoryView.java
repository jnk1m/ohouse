package com.ohouse.ohouse.entity;

import com.ohouse.ohouse.enums.CategoryType;
import lombok.Getter;
import net.jcip.annotations.Immutable;

import javax.persistence.*;

@Entity
@Immutable
@Table(name = "option_category")
@Getter
public class OptionCategoryView {
  @Id
  @Column(name = "category_id")
  private int categoryId;

  @Column(name = "category_name")
  private String categoryName;

  @Column(name = "type")
  @Enumerated(EnumType.STRING)
  private CategoryType categoryType;
}
