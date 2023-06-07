package com.ohouse.ohouse.controller;

import com.ohouse.ohouse.domain.UserDTO;
import com.ohouse.ohouse.security.auth.SessionUser;
import com.ohouse.ohouse.service.PhoneValidationService;
import com.ohouse.ohouse.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@RequestMapping("/account")
public class UserController {

  private final UserService userService;
  private final PhoneValidationService phoneValidationService;

  @GetMapping()
  public String getAccountPage(Model model, HttpSession session) {
    SessionUser user = (SessionUser) session.getAttribute("user");

    UserDTO userDTO = userService.getUserByEmail(user.getEmail());

    model.addAttribute("user", userDTO);

    return "mypage";
  }

  @PostMapping("/sendVerificationCode")
  public String sendVerificationCode(@RequestParam String phoneNumber) {
    phoneValidationService.sendVerificationCode("+" + phoneNumber);
    return "redirect:/account";
  }


}
