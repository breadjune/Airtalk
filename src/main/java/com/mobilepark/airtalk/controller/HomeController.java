package com.mobilepark.airtalk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

  @RequestMapping("/home")
  public @ResponseBody String home() {
    return "Hello, Spring Boot!";
  }


  @RequestMapping("/api")
  public @ResponseBody String api() {
    return "api";
  }

}