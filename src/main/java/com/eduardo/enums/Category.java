package com.eduardo.enums;


import lombok.Getter;

@Getter
public enum Category {
  BACK_END("Back-End"),
  FRONT_END("Front-End");

  private String value;

  Category(String value) {
  }

  @Override
  public String toString() {
    return value;
  }
}
