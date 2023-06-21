package com.ohouse.ohouse.controller;

import com.ohouse.ohouse.domain.UserDTO;
import com.ohouse.ohouse.security.auth.SessionUser;
import com.ohouse.ohouse.service.PhoneValidationService;
import com.ohouse.ohouse.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/accounts")
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

  @PostMapping("/verification-codes")
  public String sendVerificationCode(@RequestBody Map<String, String> payload) {
    phoneValidationService.sendVerification(payload.get("phoneNumber"));
    return "redirect:/accounts";
  }

  @PostMapping("/verification-codes/verification")
  public void checkVerificationCode(@RequestBody Map<String, String> payload){
    String verificationCheckResult = phoneValidationService.checkVerificationCode(payload.get("phoneNumber"), payload.get("verificationCode"));

    if(verificationCheckResult.equals("approved")){
      System.out.println("approved");
    }
  }


}
