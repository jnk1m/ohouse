package com.ohouse.ohouse.service;

import com.ohouse.ohouse.entity.Category;
import com.ohouse.ohouse.entity.MenuCategoryView;

import java.util.List;

public interface CategoryService {
  List<Category> getMenuCategories();

  Category findCategory(int categoryId);

  List<MenuCategoryView> getAllMenuCategoryView();
}
