package com.ohouse.ohouse.service;

import com.ohouse.ohouse.entity.Category;

import java.util.List;

public interface CategoryService {
  List<Category> getMenuCategories();

  Category findCategory(Long categoryId);
}
