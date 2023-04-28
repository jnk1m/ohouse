package com.ohouse.ohouse.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {
  public static final String PATH = "/error";

  @RequestMapping(value = PATH)
  public String error(){
    return "404-simple";
  }

}
