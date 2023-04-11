package com.ohouse.ohouse.repository;

import com.ohouse.ohouse.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

  @Query("select c from Category c where c.categoryId <= 11")
  List<Category> getMenuCategories();

}
