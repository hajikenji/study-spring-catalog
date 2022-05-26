package com.example.study.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Random;

import javax.transaction.Transactional;
import javax.validation.Validator;

import com.example.study.repository.CatalogRepository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import net.bytebuddy.utility.RandomString;

@SpringBootTest
@Transactional
public class modelTest {

  @Autowired
  CatalogRepository catalogRepository;

  @Autowired
  Validator validator;

  // @Autowired
  // Catalog catalog;
  Catalog catalog = new Catalog();

  // List<String> stringList = Arrays.asList("a", "",
  // "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "_");
  final String[] stringArray;
  // static final ArrayList<String> stringArrayList;

  static String a = "a";

  modelTest() {
    ArrayList<String> stringArrayList = new ArrayList<>();
    for (Integer i = 0; i < 10; i++) {
      stringArrayList.add(i.toString());
    }
    String[] stringArray = stringArrayList.toArray(new String[0]);
    this.stringArray = stringArray;
    // this.stringArrayList = stringArrayList;
    System.out.println(stringArray.toString());
  }

  // public static void testCase(String[] args) {
  // }

  // BindingResult bindingResult = new BindException(catalog, "catalog");
  // @Test
  // void testCase() {
  // var stringArrayList = new ArrayList<>();
  // for (Integer i = 0; i < 10; i++) {
  // stringArrayList.add(i.toString());
  // }
  // String[] stringArray = stringArrayList.toArray(new String[0]);
  // this.stringArray = stringArray;
  // System.out.println(stringArray.toString());
  // }

  @Test
  @DisplayName("登録ができるかテスト")
  void registerTest() {
    var catalog = new Catalog();
    // info.setId(1);
    catalog.setName("name");
    catalogRepository.save(catalog);
    var outPut = catalogRepository.getReferenceById(catalog.getId());
    assertEquals(catalog.getName(), outPut.getName());
  }

  @ParameterizedTest
  @ValueSource(strings = { "a", "", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "_" })
  @DisplayName("バリデーションテスト 条件:2~30字 通らない")
  void validationTest(String s) {
    // catalog.setId( // var catalog = new Catalog();1);
    // validator.validate(catalog, bindingResult);
    catalog.setName(s);

    var catalogList = new ArrayList<>();
    validator.validate(catalog).forEach(x -> catalogList.add(x));
    assertTrue(catalogList.size() == 1);
  }

  // @ParameterizedTest
  // @MethodSource(stringArray)
  // @DisplayName("バリデーションテスト 条件:2~30字 通る")
  // void validationTest2(String s) {
  // // catalog.setId( // var catalog = new Catalog();1);
  // // validator.validate(catalog, bindingResult);
  // catalog.setName(s);

  // var catalogList = new ArrayList<>();
  // validator.validate(catalog).forEach(x -> catalogList.add(x));
  // assertTrue(catalogList.size() == 1);
  // }

  @Test
  @DisplayName("バリデーションテスト 条件:2~30字 通る")
  void validationTestCicle() {
    Random random = new Random();
    // for で100回
    for (int i = 0; i < 100; i++) {
      // 2~30までの数字でランダムな数字を作成
      int randomNum = random.nextInt(2, 30);
      // ランダムな数字の長さの文字列作成
      String testCase = RandomString.make(randomNum);
      // modelにセット
      catalog.setName(testCase);
      var catalogList = new ArrayList<>();
      // バリデーションに引っかかるとエラーメッセージが出るので、リストに入れる
      validator.validate(catalog).forEach(x -> catalogList.add(x));
      // この後get(0)するので、リストの長さが0だとエラーのため"a"だけ入れとく
      catalogList.add("a");
      assertTrue(!catalogList.get(0).toString().contains("2 から 30 の間のサイズにしてください"));
    }

  }

  @Test
  @DisplayName("データの削除ができる")
  void registerDelete() {
    catalog.setName("name");
    catalogRepository.save(catalog);
    catalogRepository.deleteById(catalog.getId());
    assertTrue(catalogRepository.findAll().size() == 0);
  }

  @Test
  @DisplayName("データの更新ができる")
  void registerUpdate() {
    catalog.setName("name");
    catalogRepository.save(catalog);
    Catalog catalog2 = new Catalog();
    catalog2.setId(catalog.getId());
    catalog2.setName("name1");
    catalogRepository.save(catalog2);
    var outPut = catalogRepository.getReferenceById(catalog2.getId());
    assertEquals(catalog2.getName(), outPut.getName());
    assertTrue(catalogRepository.findAll().size() == 1);
  }

  @Test
  @DisplayName("データの更新がバリデーションによってちゃんとできない")
  void registerUpdateValidation() {
    catalog.setName("name");
    catalogRepository.save(catalog);
    Catalog catalog2 = new Catalog();
    catalog2.setId(catalog.getId());
    catalog2.setName("n");
    var catalogList = new ArrayList<>();
    validator.validate(catalog2).forEach(x -> catalogList.add(x));
    catalogList.add("a");
    assertTrue(catalogList.get(0).toString().contains("2 から 30 の間のサイズにしてください"));
  }

}
