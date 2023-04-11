package com.ohouse.ohouse.controller;

import com.ohouse.ohouse.entity.Category;
import com.ohouse.ohouse.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MainController {

  private final CategoryService categoryService;

  @GetMapping("/")
  public String index(Model model) {
    List<Category> menuCategories = categoryService.getMenuCategories();

    model.addAttribute("categories", menuCategories);
    return "index";
  }
}
