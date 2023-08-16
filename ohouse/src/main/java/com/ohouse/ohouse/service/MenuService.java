package com.ohouse.ohouse.service;

import com.ohouse.ohouse.domain.AdminMenuListDTO;
import com.ohouse.ohouse.domain.MenuDTO;
import com.ohouse.ohouse.domain.MenuOptionDTO;
import com.ohouse.ohouse.entity.Category;
import com.ohouse.ohouse.entity.Menu;
import com.ohouse.ohouse.exception.MenuNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface MenuService {
  List<MenuDTO> getMenusInCategory(Category category);

  MenuDTO getMenuDTO(int menuId) throws MenuNotFoundException;

  Menu getAvailableMenuById(int menuId) throws MenuNotFoundException;

  Map<String, List<MenuOptionDTO>> getMenuOptions(Menu menu);

  Page<AdminMenuListDTO> getAllMenus(Pageable pageable);

}
