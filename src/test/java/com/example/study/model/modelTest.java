package com.example.study.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.transaction.Transactional;

import com.example.study.repository.CatalogRepository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.RequiredArgsConstructor;

@SpringBootTest
@Transactional
// @RequiredArgsConstructor
public class modelTest {

  @Autowired
  CatalogRepository catalogRepository;

  @Test
  @DisplayName("登録テスト")
  void registerTest() {
    var info = new Catalog();
    // info.setId(1);
    info.setName("name");
    catalogRepository.save(info);
    var outPut = catalogRepository.getReferenceById(info.getId());
    assertEquals(info.getName(), outPut.getName());
  }

}
