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
    for (int i = 0; i < 100; i++) {
      int randomNum = random.nextInt(2, 30);
      String testCase = RandomString.make(randomNum);
      catalog.setName(testCase);
      var catalogList = new ArrayList<>();
      validator.validate(catalog).forEach(x -> catalogList.add(x));
      catalogList.add("a");
      assertTrue(!catalogList.get(0).toString().contains("2 から 30 の間のサイズにしてください"));
      System.out.println(i);
    }

  }

  // public static Stream<String> source() {
  // ArrayList<String> stringArrayList = new ArrayList<>();
  // for (Integer i = 0; i < 10; i++) {
  // stringArrayList.add(i.toString());
  // }
  // String[] stringArray = stringArrayList.toArray(new String[0]);
  // // this.stringArrayList = stringArrayList;
  // System.out.println(stringArray.toString());
  // return Stream.of(stringArrayList);
  // }

}
