package com.ohouseab.service;

import com.ohouseab.entity.Category;
import com.ohouseab.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
  private final CategoryRepository categoryRepository;

  @Override
  public List<Category> getMenuCategories() {
    return categoryRepository.getMenuCategories();
  }

  @Override
  public Category findCategory(Long categoryId) {
    return categoryRepository.findById(categoryId).orElseThrow(()-> new RuntimeException("Category not found."));
  }
}
