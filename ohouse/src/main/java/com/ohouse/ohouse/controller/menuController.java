package com.ohouse.ohouse.controller;

import com.ohouse.ohouse.domain.MenuDTO;
import com.ohouse.ohouse.entity.Category;
import com.ohouse.ohouse.entity.Menu;
import com.ohouse.ohouse.service.CategoryService;
import com.ohouse.ohouse.service.MenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/menus")
public class MenuController {
  private final MenuService menuService;
  private final CategoryService categoryService;

  @PostMapping()
  @ResponseBody
  public List<MenuDTO> getMenuList(@RequestParam Long categoryId) {

    Category category = categoryService.findCategory(categoryId);

    List<Menu> menuList = menuService.getMenusInCategory(category);
    List<MenuDTO> menuDTOList = new ArrayList<>();

    for (Menu menu : menuList) {
      MenuDTO menuDTO = new MenuDTO(menu.getMenuId(), menu.getMenuNameEng(), menu.getDescriptionEng(),
              menu.getMenuNameKor(), menu.getDescriptionKor(), menu.getMenuPrice(), menu.getImagePath());
      menuDTOList.add(menuDTO);
    }

    return menuDTOList;
  }


}
