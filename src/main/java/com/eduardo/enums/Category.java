package com.eduardo.enums;


import lombok.Getter;

@Getter
public enum Category {
  BACK_END("Back-End"),
  FRONT_END("Front-End");

  private final String value;

  Category(String value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return value;
  }
}
