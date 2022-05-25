package com.example.study.model;

import javax.validation.constraints.Size;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class catalogForm {

  @Size(min = 2, max = 30)
  final String name;
}
