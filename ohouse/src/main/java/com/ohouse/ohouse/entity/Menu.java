package com.ohouse.ohouse.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "menu")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Menu {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "menu_id")
  private Long id;

  @Column(name = "menu_name_eng")
  private String menuNameEng;

  /*nullable*/
  @Column(name = "description_eng")
  private String descriptionEng;

  @Column(name = "menu_name_kor")
  private String menuNameKor;

  /*nullable*/
  @Column(name = "description_kor")
  private String descriptionKor;

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

  @Builder
  public Menu(String menuNameEng, String descriptionEng, String menuNameKor, String descriptionKor,
              BigDecimal menuPrice, Category category, boolean isAvailable, String imagePath, String chitName) {
    this.menuNameEng = menuNameEng;
    this.descriptionEng = descriptionEng;
    this.menuNameKor = menuNameKor;
    this.descriptionKor = descriptionKor;
    this.menuPrice = menuPrice;
    this.category = category;
    this.isAvailable = isAvailable;
    this.imagePath = imagePath;
    this.chitName = chitName;
  }


}
