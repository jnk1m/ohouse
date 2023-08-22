package com.ohouse.ohouse.repository;

import com.ohouse.ohouse.domain.AdminMenuListDTO;
import com.ohouse.ohouse.domain.MenuOptionDTO;
import com.ohouse.ohouse.entity.Category;
import com.ohouse.ohouse.entity.Menu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface MenuRepository extends JpaRepository<Menu, Integer> {

  @Query("SELECT m FROM Menu m WHERE m.category = :category AND m.isAvailable = true")
  List<Menu> findByCategoryAndIsAvailableTrue(@Param("category") Category categoryId);

  @Query("SELECT m FROM Menu m WHERE m.menuId = :menuId AND m.isAvailable = true")
  Optional<Menu> findByMenuIdAndIsAvailableTrue(@Param("menuId") int menuId);

  @Query("SELECT new com.ohouse.ohouse.domain.MenuOptionDTO(c.categoryName, o.optionName, o.optionId) " +
          "FROM MenuOption mo " +
          "JOIN mo.category c " +
          "JOIN mo.options o " +
          "WHERE mo.menu = :menu")
  List<MenuOptionDTO> findOptionByMenuId(@Param("menu") Menu menu);

  @Query(value = "SELECT new com.ohouse.ohouse.domain.AdminMenuListDTO(" +
          "m.menuId, m.menuNameEng, m.menuPrice, c.categoryName, m.isAvailable) " +
          "FROM Menu m " +
          "JOIN Category c " +
          "on m.category.categoryId = c.categoryId",
          countQuery = "SELECT count(m) FROM Menu m")
  Page<AdminMenuListDTO> findAllAdminMenuListDTO(Pageable pageable);

  @Query("SELECT imagePath " +
          "FROM Menu " +
          "WHERE menuId = :menuId")
  String findImgPathByMenuId(int menuId);

  @Query("SELECT c.categoryName " +
          "FROM Menu m " +
          "JOIN Category  c on m.category.categoryId = c.categoryId " +
          "WHERE m.menuId = :menuId")
  String findCategoryNameByMenuId(int menuId);
}
