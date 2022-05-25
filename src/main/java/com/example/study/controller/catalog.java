package com.example.study.controller;

import com.example.study.model.catalogForm;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class catalog {

  @GetMapping("/catalog")
  public String catalogForSeeing() {
    return "catalog";
  }

  @GetMapping("/catalog/catalog-create")
  public String catalogCreatePage(@ModelAttribute catalogForm catalog) {
    return "catalog-create";
  }

  @PostMapping("/catalog/catalog-create")
  public String catalogCreating(@Validated @ModelAttribute catalogForm catalog,
      BindingResult result) {
    if (result.hasErrors()) {
      return "catalog-create";
    }
    System.out.println(catalog.getName());
    return "redirect:/catalog";
  }
}
