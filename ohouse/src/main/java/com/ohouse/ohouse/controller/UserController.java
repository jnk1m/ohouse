package com.ohouse.ohouse.controller;

import com.ohouse.ohouse.domain.UserDTO;
import com.ohouse.ohouse.service.PhoneValidationService;
import com.ohouse.ohouse.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

  @ModelAttribute("userDTO")
  @CachePut(value = "userDTO", key = "#session.getAttribute('user').email")
  public UserDTO getUserDTO(HttpSession session) {
    return (UserDTO) session.getAttribute("user");
  }

  @GetMapping()
  public String getAccountPage(Model model, @ModelAttribute("userDTO") UserDTO userDTO) {
    boolean phoneVerificationStatus = userService.checkPhoneVerificationStatus(userDTO.getUserId());

    if (phoneVerificationStatus) {
      model.addAttribute("phoneNumber", userService.getPhoneById(userDTO.getUserId()));
    }

    model.addAttribute("phoneVerificationStatus", phoneVerificationStatus);
    model.addAttribute("user", userDTO);

    return "mypage";
  }

  @PostMapping("/verification-codes")
  public ResponseEntity<String> sendVerificationCode(@RequestBody Map<String, String> payload) {
    try {
      phoneValidationService.sendVerification(payload.get("phoneNumber"));
      return new ResponseEntity<>("{\"message\": \"Code sent\"}", HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>("{\"message\": \"Error while sending verification code\"}", HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PostMapping("/verification-codes/verification")
  public ResponseEntity<String> checkVerificationCode(@RequestBody Map<String, String> payload, @ModelAttribute("userDTO") UserDTO userDTO) {
    String phoneNumber = payload.get("phoneNumber");
    String verificationCheckResult = phoneValidationService.checkVerificationCode(phoneNumber, payload.get("verificationCode"));

    try {
      if (verificationCheckResult.equals("approved")) {
        userService.savePhoneNumberAndMarkVerified(userDTO.getUserId(), phoneNumber);
        return new ResponseEntity<>("{\"message\": \"Verification successful\"}", HttpStatus.OK);
      } else {
        return new ResponseEntity<>("{\"message\": \"Verification failed\"}", HttpStatus.OK);
      }
    } catch (Exception e) {
      //TODO
      return new ResponseEntity<>("{\"message\": \"Error while checking verification code\"}", HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

}
