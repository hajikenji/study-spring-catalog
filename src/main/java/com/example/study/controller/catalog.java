package com.example.study.controller;

import com.example.study.model.Catalog;
import com.example.study.repository.CatalogRepository;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class catalog {

  final CatalogRepository catalogRepository;

  // 一覧表示
  @GetMapping("/catalog")
  public String catalogForSeeing(@ModelAttribute Catalog catalog,
      Model model) {
    model.addAttribute("name", catalogRepository.findAll());
    // System.out.println(catalogRepository.findAll().get(0));
    return "catalog";
  }

  // 作成画面に遷移&作成処理
  @GetMapping("/catalog/catalog-create")
  public String catalogCreatePage(@ModelAttribute Catalog catalog) {
    return "catalog-create";
  }

  @PostMapping("/catalog/catalog-create")
  public String catalogCreating(@Validated @ModelAttribute Catalog catalog,
      BindingResult result) {
    if (result.hasErrors()) {
      return "catalog-create";
    }
    // System.out.println(catalog);
    // try {
    // catalog.setName("a");
    // } catch (Exception e) {
    // System.out.println(e);
    // }
    catalogRepository.save(catalog);
    return "redirect:/catalog";
  }

  // 更新処理
  @PostMapping("/catalog/catalog-update")
  public String catalogUpdate(@Validated @ModelAttribute Catalog catalog,
      BindingResult result,
      Model model) {
    if (result.hasErrors()) {
      model.addAttribute("name", catalogRepository.findAll());
      return "catalog";
    }
    catalogRepository.save(catalog);
    return "redirect:/catalog";
  }

  // 削除処理
  /// JUnit用に削除成功modelを作成し、redirectせず/catalogする
  @PostMapping("/catalog/catalog-delete")
  public String catalogDelete(@RequestParam("id") long id,
      Model model,
      @ModelAttribute Catalog catalog) {
    catalogRepository.deleteById(id);
    String stringId = Long.toString(id);
    model.addAttribute("forJunitTest", stringId);
    // model.addAttribute("", "deleteSuccess");
    catalogForSeeing(catalog, model);
    return "/catalog";
  }
}
