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

import com.example.study.model.Catalog;

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
                .andExpect(content().string(containsString("一覧")))
                // .andExpect(content().string(containsString("ようこそ")))
                .andExpect(view().name("root"));
    }

    @Test
    @DisplayName("catalog.htmlが表示されるか")
    void catalogListDisplay() throws Exception {
        mockMvc
                .perform(get("/catalog"))
                .andExpect(content().string(containsString("一覧画面")))
                .andExpect(content().string(containsString("作成")))
                .andExpect(view().name("catalog"));
    }

    @Test
    @DisplayName("catalog/catalog-create.htmlが表示されるか")
    void catalogCreateDisplay() throws Exception {
        mockMvc
                .perform(get("/catalog/catalog-create"))
                .andExpect(content().string(containsString("新規作成画面")))
                .andExpect(content().string(containsString("登録")))
                .andExpect(view().name("catalog-create"));
    }

    @Test
    @DisplayName("catalog/catalog-create.htmlでcreateできるか、またバリデーション")
    void catalogCreateCatalog() throws Exception {

        Catalog catalog = new Catalog();
        catalog.setName("name");
        Catalog catalog2 = new Catalog();
        catalog2.setName("n");

        mockMvc.perform(
                post("/catalog/catalog-create")
                        .flashAttr("catalog", catalog))
                // .andExpect(content().string(containsString("name")))
                .andExpect(redirectedUrl("/catalog"));
        mockMvc.perform(
                get("/catalog"))
                .andExpect(content().string(containsString("name")));

        mockMvc.perform(
                post("/catalog/catalog-create")
                        .flashAttr("catalog", catalog2))
                // .andExpect(content().string(containsString("name")))
                .andExpect(model().hasErrors())
                .andExpect(content().string(containsString("新規作成画面")))
                // html側に事前にth:errorのタグにname="error"など仕込んでおくと、エラッた時にしたのが反応する
                .andExpect(content().string(containsString("error")))
                .andExpect(content().string(containsString("nameErrorForJUnitTest")));

        // mockMvc.perform(
        // get("/catalog"))
        // .andExpect(view().name("catalog-create"))
        // .andExpect(content().string(containsString("2 から 30 の間のサイズにしてください")));
        // .flashAttr("a", catalog);
        // .andExpect(view().name("catalog-create"));
    }

    @Test
    @DisplayName("create後にdeleteできるか")
    void catalogDelete() throws Exception {

        Catalog catalog = new Catalog();
        catalog.setName("name");

        mockMvc.perform(
                post("/catalog/catalog-create")
                        .flashAttr("catalog", catalog))
                // .andExpect(content().string(containsString("name")))
                .andExpect(redirectedUrl("/catalog"));
        mockMvc.perform(
                get("/catalog"))
                .andExpect(content().string(containsString("name")));

        // createの処理を行った後でgetIdをしないと取れない。
        /// ↑のsetNameの所でgetIdしちゃうと保存処理の前なのでidが無いから
        String forParam = Long.toString(catalog.getId());
        String testString = "name=" + "\"" + Long.toString(catalog.getId()) + "\"";

        mockMvc.perform(
                post("/catalog/catalog-delete").param("id", forParam))
                // post("/catalog/catalog-delete"))
                .andExpect(content().string(containsString(testString)));
        // mockMvc.perform(
        // get("/catalog")
        // )
        // .andExpect(matcher)

    }

    @Test
    @DisplayName("updateできるか")
    void catalogUpdate() throws Exception {

        Catalog catalog = new Catalog();
        catalog.setName("name");

        mockMvc.perform(
                post("/catalog/catalog-create")
                        .flashAttr("catalog", catalog))
                .andExpect(redirectedUrl("/catalog"));

        mockMvc.perform(
                get("/catalog"))
                .andExpect(content().string(containsString("一覧画面")));

        // Long id = catalog.getId();
        catalog.setName("name1");

        mockMvc.perform(
                post("/catalog/catalog-update").flashAttr("catalog", catalog))
                .andExpect(redirectedUrl("/catalog"));

        mockMvc.perform(
                get("/catalog"))
                .andExpect(content().string(containsString("一覧画面")))
                .andExpect(content().string(containsString("name1")));
    }

}
