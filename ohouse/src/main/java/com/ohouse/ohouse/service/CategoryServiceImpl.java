package com.ohouse.ohouse.service;

import com.ohouse.ohouse.entity.Category;
import com.ohouse.ohouse.entity.MenuCategoryView;
import com.ohouse.ohouse.repository.CategoryRepository;
import com.ohouse.ohouse.repository.MenuCategoryViewRepository;
import com.ohouse.ohouse.repository.OptionCategoryViewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
  private final CategoryRepository categoryRepository;
  private final MenuCategoryViewRepository menuCategoryViewRepository;
  private final OptionCategoryViewRepository optionCategoryViewRepository;

  @Override
  public List<Category> getMenuCategories() {
    return categoryRepository.getMenuCategories();
  }

  @Override
  public Category findCategory(int categoryId) {
    return categoryRepository.findById(categoryId).orElseThrow(()-> new RuntimeException("Category not found."));
  }

  @Override
  public List<MenuCategoryView> getAllMenuCategoryView() {
    return menuCategoryViewRepository.findAll();
  }
}
