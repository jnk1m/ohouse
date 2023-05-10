package com.ohouseab.service;

import com.ohouseab.entity.Category;

import java.util.List;

public interface CategoryService {
  List<Category> getMenuCategories();

  Category findCategory(Long categoryId);
}
