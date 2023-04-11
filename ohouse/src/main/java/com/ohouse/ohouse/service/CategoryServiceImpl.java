package com.ohouse.ohouse.service;

import com.ohouse.ohouse.entity.Category;
import com.ohouse.ohouse.repository.CategoryRepository;
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
}
