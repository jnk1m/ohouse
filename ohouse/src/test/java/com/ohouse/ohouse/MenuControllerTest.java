package com.ohouse.ohouse;

import com.ohouse.ohouse.config.SecurityConfig;
import com.ohouse.ohouse.controller.MenuController;
import com.ohouse.ohouse.domain.MenuDTO;
import com.ohouse.ohouse.domain.MenuOptionDTO;
import com.ohouse.ohouse.entity.Category;
import com.ohouse.ohouse.entity.Menu;
import com.ohouse.ohouse.exception.InvalidCategoryIdException;
import com.ohouse.ohouse.exception.MenuNotFoundException;
import com.ohouse.ohouse.service.CategoryService;
import com.ohouse.ohouse.service.MenuService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = MenuController.class)
@ExtendWith(SpringExtension.class)
@Import(SecurityConfig.class)
class MenuControllerTest {
  @Autowired
  private MockMvc mvc;

  @MockBean
  private CategoryService categoryService;

  @MockBean
  private MenuService menuService;

  private Category category;
  private MenuDTO menuDTO;
  private List<MenuDTO> menuDTOList;
  private Map<String, List<MenuOptionDTO>> menuOptions;
  private Menu menu;

  @BeforeEach
  void setUp() throws MenuNotFoundException {
    category = new Category("Breakfast");
    menu = new Menu("Menu 1", "Description 1", "메뉴 1", "설명 1", new BigDecimal(10), category, true, null, "menu");
    menuDTO = new MenuDTO(1L, "Menu 1", "Description 1", "메뉴 1", "설명 1", new BigDecimal(10), null);
    menuDTOList = Arrays.asList(
            menuDTO, new MenuDTO(2L, "Menu 2", "Description 2", "메뉴 2", "설명 2", new BigDecimal(20), null)
    );

    given(categoryService.findCategory(anyLong())).willReturn(category);
    given(menuService.getMenusInCategory(category)).willReturn(menuDTOList);

    menuOptions = new HashMap<>();

    List<MenuOptionDTO> option1 = Arrays.asList(
            new MenuOptionDTO("Egg Options", "scramble"),
            new MenuOptionDTO("Egg Options", "over easy")
    );

    List<MenuOptionDTO> option2 = Arrays.asList(
            new MenuOptionDTO("Steak Options", "Rare"),
            new MenuOptionDTO("Steak Options", "Medium Rare")
    );

    menuOptions.put("option1", option1);
    menuOptions.put("option2", option2);

    given(menuService.getMenuDTO(anyLong())).willReturn(menuDTO);
    given(menuService.getMenu(anyLong())).willReturn(menu);
    given(menuService.getMenuOptions(menu)).willReturn(menuOptions);
  }


  @Test
  @WithMockUser
  void testGetMenus_validCategoryId() throws Exception {
    mvc.perform(get("/menus/{categoryId}", 1L))
            .andExpect(status().isOk())
            .andExpect(model().attribute("category", category))
            .andExpect(model().attribute("menus", menuDTOList))
            .andExpect(view().name("menu-list"));
  }

  @Test
  @WithMockUser
  void testGetMenus_invalidCategoryId() throws Exception {
    given(categoryService.findCategory(12L))
            .willThrow(new InvalidCategoryIdException("Invalid Category"));

    mvc.perform(get("/menus/{categoryId}", 12L))
            .andExpect(status().isNotFound())
            .andExpect(result -> assertTrue(result.getResolvedException() instanceof InvalidCategoryIdException))
            .andExpect(result -> assertEquals("Invalid Category", result.getResolvedException().getMessage()));
  }


  @Test
  @WithMockUser
  void getMenuWithOptions() throws Exception {
    mvc.perform(get("/menus/detail/{menuId}", 1L))
            .andExpect(status().isOk())
            .andExpect(model().attribute("menuOptions", menuOptions.entrySet()))
            .andExpect(view().name("menu-detail"))
            .andExpect(result -> {

            });
  }
}
