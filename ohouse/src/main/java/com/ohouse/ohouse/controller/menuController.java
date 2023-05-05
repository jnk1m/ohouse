package com.ohouse.ohouse.controller;

import com.ohouse.ohouse.domain.MenuDTO;
import com.ohouse.ohouse.domain.MenuOptionDTO;
import com.ohouse.ohouse.entity.Category;
import com.ohouse.ohouse.service.CategoryService;
import com.ohouse.ohouse.service.MenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

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
  public String getMenus(@PathVariable Long categoryId, Model model) {
    Category category = categoryService.findCategory(categoryId);

    /*Category ID 1 to 11 are valid menu IDs, but 12 and above are reserved for options.
    Therefore, when customers request the list of available menus, only the ones with IDs between 1 and 11 should be returned.
    Any ID higher than 11 should result in an error message.*/
    if (categoryId > 11) {
      throw new InvalidCategoryIdException("Invalid Category");
    }

    List<MenuDTO> menuDTOList = menuService.getMenusInCategory(category);

    model.addAttribute("category", category);
    model.addAttribute("menus", menuDTOList);
    return "menu-list";
  }

  @GetMapping("/detail/{menuId}")
  public String getMenu(@PathVariable Long menuId, Model model) {
    MenuDTO menuDTO = menuService.getMenuDTO(menuId);

    Map<String, List<MenuOptionDTO>> menuOptions = menuService.getMenuOptions(menuService.getMenu(menuId));

    Set<Map.Entry<String, List<MenuOptionDTO>>> menuOptionSet = menuOptions.entrySet();

    model.addAttribute("menu", menuDTO);
    model.addAttribute("menuOptions", menuOptionSet);

    return "menu-detail";
  }
}

@ResponseStatus(HttpStatus.NOT_FOUND)
class InvalidCategoryIdException extends RuntimeException {
  public InvalidCategoryIdException(String message) {
    super(message);
  }
}


