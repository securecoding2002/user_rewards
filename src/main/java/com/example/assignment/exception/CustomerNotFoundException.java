package com.example.assignment.exception;

public class CustomerNotFoundException extends RuntimeException {
  public CustomerNotFoundException(String errorMessage) {
    super(errorMessage);
  }
}
