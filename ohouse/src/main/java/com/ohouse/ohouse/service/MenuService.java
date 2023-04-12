package com.ohouse.ohouse.service;

import com.ohouse.ohouse.entity.Category;
import com.ohouse.ohouse.entity.Menu;

import java.util.List;

public interface MenuService {
  List<Menu> getMenusInCategory(Category category);
}
