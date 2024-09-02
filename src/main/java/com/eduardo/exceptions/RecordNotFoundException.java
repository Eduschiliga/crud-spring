package com.eduardo.exceptions;

import java.io.Serial;

public class RecordNotFoundException extends RuntimeException {
  @Serial
  private static final long serialVersionUID = 1L;

  public RecordNotFoundException(Long id) {
    super("Record not found! > > > With id: " + id);
  }
}