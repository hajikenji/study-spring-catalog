package com.example.study.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.CoreMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class rootControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Test
  @DisplayName("root.htmlが表示されるか")
  void rootDisplay() throws Exception {
    mockMvc
        .perform(
            get("/"))
        .andExpect(content().string(containsString("ようこそ")))
        .andExpect(content().string(containsString("ようこそ")))
        .andExpect(view().name("root"));
  }

}
