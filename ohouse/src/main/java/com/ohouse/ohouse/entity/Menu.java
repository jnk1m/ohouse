package com.ohouse.ohouse.entity;

import lombok.Getter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "menu")
@Getter
public class Menu {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "menu_id")
  private Long id;

  @Column(name = "menu_name_kor")
  private String menuNameKor;

  /*nullable*/
  @Column(name = "description_kor")
  private String descriptionKor;

  @Column(name = "menu_name_eng")
  private String menuNameEng;

  /*nullable*/
  @Column(name = "description_eng")
  private String descriptionEng;

  @Column(name = "menu_price")
  private BigDecimal menuPrice;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "category_id")
  private Category category;

  @Column(name = "is_available")
  private boolean isAvailable;

  /*nullable*/
  @Column(name = "image_path")
  private String imagePath;

  @Column(name = "chit_name")
  private String chitName;

  protected Menu() {
  }

  private Menu(MenuBuilder builder) {
    this.id = builder.id;
    this.menuNameKor = builder.menuNameKor;
    this.descriptionKor = builder.descriptionKor;
    this.menuNameEng = builder.menuNameEng;
    this.descriptionEng = builder.descriptionEng;
    this.menuPrice = builder.menuPrice;
    this.category = builder.category;
    this.isAvailable = builder.isAvailable;
    this.imagePath = builder.imagePath;
    this.chitName = builder.chitName;
  }

  public static class MenuBuilder {
    private Long id;
    private String menuNameKor;
    private String descriptionKor;
    private String menuNameEng;
    private String descriptionEng;
    private BigDecimal menuPrice;
    private Category category;
    private boolean isAvailable;
    private String imagePath;
    private String chitName;

    public MenuBuilder id(Long id) {
      this.id = id;
      return this;
    }

    public MenuBuilder menuNameKor(String menuNameKor) {
      this.menuNameKor = menuNameKor;
      return this;
    }

    public MenuBuilder descriptionKor(String descriptionKor) {
      this.descriptionKor = descriptionKor;
      return this;
    }

    public MenuBuilder menuNameEng(String menuNameEng) {
      this.menuNameEng = menuNameEng;
      return this;
    }

    public MenuBuilder descriptionEng(String descriptionEng) {
      this.descriptionEng = descriptionEng;
      return this;
    }

    public MenuBuilder menuPrice(BigDecimal menuPrice) {
      this.menuPrice = menuPrice;
      return this;
    }

    public MenuBuilder category(Category category) {
      this.category = category;
      return this;
    }

    public MenuBuilder isAvailable(boolean isAvailable) {
      this.isAvailable = isAvailable;
      return this;
    }

    public MenuBuilder imagePath(String imagePath) {
      this.imagePath = imagePath;
      return this;
    }

    public MenuBuilder chitName(String chitName) {
      this.chitName = chitName;
      return this;
    }

    public Menu build() {
      return new Menu(this);
    }
  }
}
