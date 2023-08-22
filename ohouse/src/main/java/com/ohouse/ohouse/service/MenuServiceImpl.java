package com.ohouse.ohouse.service;

import com.ohouse.ohouse.domain.AdminMenuListDTO;
import com.ohouse.ohouse.domain.MenuDTO;
import com.ohouse.ohouse.domain.MenuOptionDTO;
import com.ohouse.ohouse.domain.NewMenuDTO;
import com.ohouse.ohouse.entity.Category;
import com.ohouse.ohouse.entity.Menu;
import com.ohouse.ohouse.exception.MenuNotFoundException;
import com.ohouse.ohouse.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {
  private final MenuRepository menuRepository;
  private final CategoryService categoryService;

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
  public Menu getAvailableMenuById(int menuId) throws MenuNotFoundException {
    return menuRepository.findByMenuIdAndIsAvailableTrue(menuId).orElseThrow(() -> new MenuNotFoundException("Menu Not Found"));
  }

  @Override
  public MenuDTO getMenuDTO(int menuId) throws MenuNotFoundException {
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

  @Override
  public Page<AdminMenuListDTO> getAllMenus(Pageable pageable) {
    return menuRepository.findAllAdminMenuListDTO(pageable);
  }

  @Override
  public Menu getMenuById(int menuId) {
    return menuRepository.findById(menuId).orElseThrow();
  }

  @Transactional
  @Override
  public void addMenu(NewMenuDTO newMenuDTO) {
    Category category = categoryService.findCategory(newMenuDTO.getCategoryId());
    Menu menu = Menu.builder().menuNameEng(newMenuDTO.getMenuNameEng())
            .descriptionEng(newMenuDTO.getDescriptionEng())
            .menuNameKor(newMenuDTO.getMenuNameKor())
            .descriptionKor(newMenuDTO.getDescriptionKor())
            .menuPrice(newMenuDTO.getMenuPrice())
            .category(category)
            .isAvailable(newMenuDTO.isAvailable())
            .chitName(newMenuDTO.getChitName())
            .build();

    menuRepository.save(menu);
  }

  @Override
  public String getMenuImgPath(int menuId) {
    return menuRepository.findImgPathByMenuId(menuId);
  }

  @Override
  public String getMenuCategoryName(int menuId) {
    return menuRepository.findCategoryNameByMenuId(menuId);
  }

  @Transactional
  @Override
  public void setMenuImage(int menuId, String imgUrl) {
    Menu menu = menuRepository.findById(menuId).orElseThrow();
    menu.setImgPath(imgUrl);
  }

}
