package com.example.ewallet.exceptions;

public class TransactionNotFoundException extends ResourceNotFoundException {
  public TransactionNotFoundException(String IBAN, Long userId) {
    super("Transaction not found for user: " + userId + "; with IBAN: " + IBAN);
  }
}
