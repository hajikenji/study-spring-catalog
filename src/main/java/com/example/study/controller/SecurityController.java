package com.example.study.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecurityController {

  @GetMapping("/ls")
  public String test() {
    return "/root";
  }

}
