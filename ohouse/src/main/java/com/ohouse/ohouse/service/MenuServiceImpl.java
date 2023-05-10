package com.ohouseab.service;

import com.ohouseab.domain.MenuDTO;
import com.ohouseab.domain.MenuOptionDTO;
import com.ohouseab.entity.Category;
import com.ohouseab.entity.Menu;
import com.ohouseab.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService{
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
  public Menu getMenu(Long menuId) {
    return menuRepository.findByMenuIdAndIsAvailableTrue(menuId);
  }

  @Override
  public MenuDTO getMenuDTO(Long menuId) {
    Menu menu = menuRepository.findByMenuIdAndIsAvailableTrue(menuId);

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
