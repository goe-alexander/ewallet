package com.example.ewallet.exceptions;

public class UserNotFoundException extends ResourceNotFoundException {

  public UserNotFoundException(Long cnp, String message) {
    super("Could not find user by cnp: " + cnp + " | " + message);
  }

  public UserNotFoundException(Long id) {
    super("Could not find user with id: " + id);
  }
}
