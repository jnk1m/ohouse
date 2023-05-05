package com.ohouse.ohouse.service;

import com.ohouse.ohouse.domain.MenuDTO;
import com.ohouse.ohouse.domain.MenuOptionDTO;
import com.ohouse.ohouse.entity.Category;
import com.ohouse.ohouse.entity.Menu;

import java.util.List;
import java.util.Map;

public interface MenuService {
  List<MenuDTO> getMenusInCategory(Category category);

  MenuDTO getMenuDTO(Long menuId);

  Menu getMenu(Long menuId);

  Map<String, List<MenuOptionDTO>> getMenuOptions(Menu menu);

}
