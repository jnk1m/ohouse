package com.ohouse.ohouse.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class InvalidCategoryIdException extends Exception {
  public InvalidCategoryIdException(String message) {
    super(message);
  }
}