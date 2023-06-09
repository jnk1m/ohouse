package com.ohouse.ohouse;

import com.ohouse.ohouse.config.RedisConfig;
import com.ohouse.ohouse.controller.MenuController;
import com.ohouse.ohouse.domain.MenuDTO;
import com.ohouse.ohouse.domain.MenuOptionDTO;
import com.ohouse.ohouse.entity.Category;
import com.ohouse.ohouse.entity.Menu;
import com.ohouse.ohouse.exception.InvalidCategoryIdException;
import com.ohouse.ohouse.exception.MenuNotFoundException;
import com.ohouse.ohouse.security.auth.CustomOAuth2UserService;
import com.ohouse.ohouse.service.CategoryService;
import com.ohouse.ohouse.service.MenuService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = MenuController.class)
@Import(RedisConfig.class)
@ActiveProfiles("dev")
class MenuControllerTest {
  @Autowired
  private MockMvc mvc;

  @MockBean
  CustomOAuth2UserService customOAuth2UserService;

  @MockBean
  private CategoryService categoryService;

  @MockBean
  private MenuService menuService;

  private Category category;
  private List<MenuDTO> menuDTOList;
  private Map<String, List<MenuOptionDTO>> menuOptions;

  @BeforeEach
  void setUp() throws MenuNotFoundException {
    category = new Category("Breakfast");
    Menu menu = new Menu("Menu 1", "Description 1", "메뉴 1", "설명 1", new BigDecimal(10), category, true, null, "menu");
    MenuDTO menuDTO = new MenuDTO(1L, "Menu 1", "Description 1", "메뉴 1", "설명 1", new BigDecimal(10), null);
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
  void testGetMenusInCategory_validCategoryId() throws Exception {
    mvc.perform(get("/menus/{categoryId}", 1L))
            .andExpect(status().isOk())
            .andExpect(model().attribute("category", category))
            .andExpect(model().attribute("menus", menuDTOList))
            .andExpect(view().name("menu-list"));
  }

  @Test
  void testGetMenusInCategory_invalidCategoryId() throws Exception {
    given(categoryService.findCategory(12L)).willAnswer(invocation -> {
      throw new InvalidCategoryIdException("Invalid Category");
    });

    mvc.perform(get("/menus/{categoryId}", 12L))
            .andExpect(status().isNotFound())
            .andExpect(result -> assertTrue(result.getResolvedException() instanceof InvalidCategoryIdException))
            .andExpect(result -> assertEquals("Invalid Category", result.getResolvedException().getMessage()));
  }

  @Test
  void getMenuWithOptions_validMenuId() throws Exception {
    mvc.perform(get("/menus/detail/{menuId}", 1L))
            .andExpect(status().isOk())
            .andExpect(model().attribute("menuOptions", menuOptions.entrySet()))
            .andExpect(view().name("menu-detail"))
            .andExpect(result -> {
              Map<String, Object> modelMap = Objects.requireNonNull(result.getModelAndView()).getModel();
              Set<Map.Entry<String, List<MenuOptionDTO>>> menuOptionSet = (Set<Map.Entry<String, List<MenuOptionDTO>>>) modelMap.get("menuOptions");
              for (Map.Entry<String, List<MenuOptionDTO>> entry : menuOptionSet) {
                int expectedListSize = 2;
                assertEquals(expectedListSize, entry.getValue().size());
              }
            });
  }

  @Test
  void getMenuWithOptions_invalidMenuId() throws Exception {
    given(menuService.getMenu(anyLong())).willAnswer(invocation -> {
      throw new MenuNotFoundException("Menu Not Found");
    });

    mvc.perform(get("/menus/detail/{menuId}", anyLong()))
            .andExpect(status().isNotFound())
            .andExpect(result -> assertTrue(result.getResolvedException() instanceof MenuNotFoundException))
            .andExpect(result -> assertEquals("Menu Not Found", result.getResolvedException().getMessage()));
  }
}

