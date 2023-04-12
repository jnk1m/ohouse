package com.ohouse.ohouse.repository;

import com.ohouse.ohouse.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ohouse.ohouse.entity.Menu;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface MenuRepository extends JpaRepository<Menu, Long> {

  @Query("SELECT m FROM Menu m WHERE m.category = :category AND m.isAvailable = true")
  List<Menu> findByCategoryAndIsAvailableTrue(@Param("category") Category categoryId);

}
