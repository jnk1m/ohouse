package com.ohouse.ohouse.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MainController {

  @GetMapping("/")
  public String index() {
    return "index";
  }

  @GetMapping("/policies/user-agreement")
  public String userAgreement(){
    return "footer/user-agreement";
  }

  @GetMapping("/policies/privacy-policy")
  public String privacyPolicy(){
    return "footer/privacy-policy";
  }

  @GetMapping("/contacts")
  public String contacts(){
    return "contacts";
  }

  @GetMapping("/about")
  public String about(){
    return "about";
  }



}
