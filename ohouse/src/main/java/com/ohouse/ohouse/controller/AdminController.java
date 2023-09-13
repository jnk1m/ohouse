package com.ohouse.ohouse.controller;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.ohouse.ohouse.domain.AdminMenuListDTO;
import com.ohouse.ohouse.domain.NewMenuDTO;
import com.ohouse.ohouse.domain.OrderSummaryDTO;
import com.ohouse.ohouse.domain.OrderSummaryDisplayDTO;
import com.ohouse.ohouse.entity.Menu;
import com.ohouse.ohouse.entity.MenuCategoryView;
import com.ohouse.ohouse.enums.OrderStatus;
import com.ohouse.ohouse.service.CategoryService;
import com.ohouse.ohouse.service.MenuService;
import com.ohouse.ohouse.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
  private final OrderService orderService;
  private final MenuService menuService;
  private final CategoryService categoryService;
  private final AmazonS3Client s3;

  @Value("${aws.s3.bucket}")
  private String bucket;

  @GetMapping
  public String getAdminPage() {
    return "admin/admin";
  }

  /*Order*/

  @GetMapping("/orders")
  public String getOrdersPage(Model model, Pageable pageable) {
    Page<OrderSummaryDTO> orders = orderService.getAllOrders(pageable);

    List<OrderSummaryDisplayDTO> formattedOrders = orders.stream()
            .map(order -> {
              //TODO: Modify to fetch the timezone ID from the client.
              ZonedDateTime customerDateTime = order.getOrderDate().withZoneSameInstant(ZoneId.of("Asia/Seoul"));
              String formattedDateTime = customerDateTime.format(DateTimeFormatter.ofPattern("MMMM dd, yyyy HH:mm"));
              return new OrderSummaryDisplayDTO(order.getOrderNumber(), formattedDateTime, order.getOrderStatus(), order.getOrderPrice());
            })
            .collect(Collectors.toList());

    Page<OrderSummaryDisplayDTO> formattedOrdersWithPage = new PageImpl<>(formattedOrders, PageRequest.of(0, formattedOrders.size()), formattedOrders.size());

    model.addAttribute("orders", formattedOrdersWithPage);

    return "admin/admin-orders";
  }

  @PostMapping("/confirmation/{orderNumber}")
  public ResponseEntity<String> confirmOrder(@PathVariable("orderNumber") String orderNumber) {
    try {
      orderService.updateOrderStatus(orderNumber, OrderStatus.PROCESSING);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (Exception e) {
      return null;
    }
  }

  @PostMapping("/cancellation/{orderNumber}")
  public ResponseEntity<String> cancelOrder(@PathVariable("orderNumber") String orderNumber) {
    try {
      orderService.updateOrderStatus(orderNumber, OrderStatus.CANCELLED);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (Exception e) {
      return null;
    }
  }

  @PostMapping("/completion/{orderNumber}")
  public ResponseEntity<String> completeOrder(@PathVariable("orderNumber") String orderNumber) {
    try {
      orderService.updateOrderStatus(orderNumber, OrderStatus.COMPLETED);
      //TODO: UPDATE USER ORDER COUNT +1
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (Exception e) {
      return null;
    }
  }

  /*Menu*/

  @GetMapping("/menus")
  public String getMenuListPage(Model model, Pageable pageable) {
    Page<AdminMenuListDTO> allMenus = menuService.getAllMenus(pageable);

    model.addAttribute("menus", allMenus);

    return "admin/menu-list";
  }

  @GetMapping("/menu/{menuId}")
  public String getMenuDetail(Model model, @PathVariable("menuId") int menuId) {
    Menu menu = menuService.getMenuById(menuId);
    model.addAttribute("menu", menu);
    return "admin/menu-detail";
  }

  @GetMapping("/menus/new")
  public String getMenuRegistrationPage(Model model) {
    List<MenuCategoryView> menuCategory = categoryService.getAllMenuCategoryView();

    model.addAttribute("menuCategory", menuCategory);

    return "admin/menu-registration";
  }

  @PostMapping("/menu/new")
  public String addNewMenu(@ModelAttribute NewMenuDTO newMenuDTO) {
    menuService.addMenu(newMenuDTO);
    return "redirect:admin/menus";
  }

  @GetMapping("/menu/{menuId}/image")
  public String getImageSettingPage(Model model, @PathVariable("menuId") int menuId) {
    String menuImgPath = menuService.getMenuImgPath(menuId);
    model.addAttribute("menuImgPath", menuImgPath);
    model.addAttribute("menuId", menuId);
    return "admin/image-upload";

  }

  @PostMapping("/menu/{menuId}/image")
  public String setMenuImage(@PathVariable("menuId") int menuId,
                             @RequestParam("imageFile") MultipartFile file) throws IOException {
    long maxFileSize = 100 * 1024;
    if (file.getSize() > maxFileSize) {
      throw new FileSizeLimitExceededException("File size must be less than 100KB.", file.getSize(), maxFileSize);
    }
    String menuCategoryName = menuService.getMenuCategoryName(menuId).toLowerCase();
    String folderPath = menuCategoryName + "/";

    String fileName = folderPath + Objects.requireNonNull(file.getOriginalFilename()).trim().toLowerCase().replaceAll("\\s", "-");

    File tempFile = convertMultiPartToFile(file);


    PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, fileName, tempFile);
    s3.putObject(putObjectRequest);

    tempFile.delete();

    String imgUrl = s3.getUrl(bucket, fileName).toString();

    menuService.setMenuImage(menuId, imgUrl);

    return "redirect:/admin/menu/" + menuId;
  }

  private File convertMultiPartToFile(MultipartFile file) throws IOException {
    File convFile = File.createTempFile("temp", null);
    try (FileOutputStream fos = new FileOutputStream(convFile)) {
      fos.write(file.getBytes());
    }
    return convFile;
  }

  @GetMapping("/menu/edit/{menuId}")
  public String getMenuUpdateForm(Model model, @PathVariable("menuId") int menuId) {
    List<MenuCategoryView> menuCategory = categoryService.getAllMenuCategoryView();
    NewMenuDTO newMenuDTO = menuService.getNewMenuDTO(menuId);

    model.addAttribute(("menu"), newMenuDTO);
    model.addAttribute("menuCategory", menuCategory);
    model.addAttribute("menuId", menuId);
    return "admin/modify-menu";
  }

  @PostMapping("/menu/edit/{menuId}")
  public String updateMenuDetail(@ModelAttribute NewMenuDTO newMenuDTO, @PathVariable("menuId") int menuId, RedirectAttributes redirectAttributes) {
    menuService.updateMenuDetail(newMenuDTO, menuId);
    redirectAttributes.addFlashAttribute("message", "변경완료!");
    return "redirect:/admin/menu/" + menuId;
  }
}
