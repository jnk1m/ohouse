package com.ohouse.ohouse.controller;

import com.ohouse.ohouse.domain.UserDTO;
import com.ohouse.ohouse.security.auth.SessionUser;
import com.ohouse.ohouse.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @GetMapping("/account")
  public String getAccountPage(Model model, HttpSession session) {
    SessionUser user = (SessionUser) session.getAttribute("user");

    UserDTO userDTO = userService.getUserByEmail(user.getEmail());

    model.addAttribute("user", userDTO);

    return "/mypage";
  }
}
