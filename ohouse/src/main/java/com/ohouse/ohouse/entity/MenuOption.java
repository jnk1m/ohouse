package com.ohouseab.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "menu_option")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MenuOption {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "menu_option_id")
  private Long menuOptionId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "category_id")
  @NotNull
  private Category category;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "menu_id")
  @NotNull
  private Menu menu;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "option_id")
  @NotNull
  private Options options;

  @Builder
  public MenuOption(Category category, Menu menu, Options options) {
    this.category = category;
    this.menu = menu;
    this.options = options;
  }
}
