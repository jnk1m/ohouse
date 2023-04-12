package com.ohouse.ohouse.service;

import com.ohouse.ohouse.entity.Category;
import com.ohouse.ohouse.entity.Menu;
import com.ohouse.ohouse.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService{
  private final MenuRepository menuRepository;

  @Override
  public List<Menu> getMenusInCategory(Category category) {
    return menuRepository.findByCategoryAndIsAvailableTrue(category);
  }
}
