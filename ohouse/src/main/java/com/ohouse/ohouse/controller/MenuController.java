package com.ohouse.ohouse.controller;

import com.ohouse.ohouse.domain.MenuDTO;
import com.ohouse.ohouse.domain.MenuOptionDTO;
import com.ohouse.ohouse.entity.Category;
import com.ohouse.ohouse.exception.InvalidCategoryIdException;
import com.ohouse.ohouse.exception.MenuNotFoundException;
import com.ohouse.ohouse.service.CategoryService;
import com.ohouse.ohouse.service.MenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/menus")
public class MenuController {
  private final MenuService menuService;
  private final CategoryService categoryService;

  @GetMapping("/{categoryId}")
  public String getMenusInCategory(@PathVariable int categoryId, Model model) throws InvalidCategoryIdException {
    Category category = categoryService.findCategory(categoryId);

    // Categories 8, 10, and 11 are not in use due to menu consolidation.
    // Starting from ID 12, they are not used for menu retrieval.
    // Exception is thrown if the category ID is 8 or greater than or equal to 10.
    if (categoryId >= 10 || categoryId == 8) {
      throw new InvalidCategoryIdException("Invalid Category");
    }

    List<MenuDTO> menuDTOList = menuService.getMenusInCategory(category);

    model.addAttribute("category", category);
    model.addAttribute("menus", menuDTOList);
    return "menu-list";
  }

  @GetMapping("/detail/{menuId}")
  public String getMenuWithOptions(@PathVariable int menuId, Model model) throws MenuNotFoundException {
    MenuDTO menuDTO = menuService.getMenuDTO(menuId);

    Map<String, List<MenuOptionDTO>> menuOptions = menuService.getMenuOptions(menuService.getAvailableMenuById(menuId));

    Set<Map.Entry<String, List<MenuOptionDTO>>> menuOptionSet = menuOptions.entrySet();

    model.addAttribute("menu", menuDTO);
    model.addAttribute("menuOptions", menuOptionSet);

    return "menu-detail";
  }
}


