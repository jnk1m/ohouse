package com.ohouse.ohouse.service;

import com.ohouse.ohouse.domain.MenuDTO;
import com.ohouse.ohouse.domain.MenuOptionDTO;
import com.ohouse.ohouse.entity.Category;
import com.ohouse.ohouse.entity.Menu;
import com.ohouse.ohouse.exception.MenuNotFoundException;
import com.ohouse.ohouse.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {
  private final MenuRepository menuRepository;

  @Override
  public List<MenuDTO> getMenusInCategory(Category category) {
    List<Menu> menuList = menuRepository.findByCategoryAndIsAvailableTrue(category);
    List<MenuDTO> menuDTOList = new ArrayList<>();

    for (Menu menu : menuList) {
      MenuDTO menuDTO = new MenuDTO(menu.getMenuId(), menu.getMenuNameEng(), menu.getDescriptionEng(),
              menu.getMenuNameKor(), menu.getDescriptionKor(), menu.getMenuPrice(), menu.getImagePath());
      menuDTOList.add(menuDTO);
    }

    return menuDTOList;
  }

  @Override
  public Menu getMenu(Long menuId) throws MenuNotFoundException {
    return menuRepository.findByMenuIdAndIsAvailableTrue(menuId).orElseThrow(() -> new MenuNotFoundException("Menu Not Found"));
  }

  @Override
  public MenuDTO getMenuDTO(Long menuId) throws MenuNotFoundException {
    Menu menu = menuRepository.findByMenuIdAndIsAvailableTrue(menuId).orElseThrow(() -> new MenuNotFoundException("Menu Not Found"));

    return new MenuDTO(menu.getMenuId(), menu.getMenuNameEng(), menu.getDescriptionEng(),
            menu.getMenuNameKor(), menu.getDescriptionKor(), menu.getMenuPrice(), menu.getImagePath());
  }

  @Override
  public Map<String, List<MenuOptionDTO>> getMenuOptions(Menu menu) {

    List<MenuOptionDTO> menuOptionDTOList = menuRepository.findOptionByMenuId(menu);

    return menuOptionDTOList.stream()
            .collect(Collectors.groupingBy(MenuOptionDTO::getCategoryName));

  }

}
