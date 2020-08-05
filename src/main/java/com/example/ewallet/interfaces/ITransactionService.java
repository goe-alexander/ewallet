package com.example.ewallet.interfaces;

import com.example.ewallet.domain.Transaction;
import com.example.ewallet.dto.TransactionDTO;

public interface ITransactionService {
  void saveTransaction(Transaction transaction);

  void saveTransaction(TransactionDTO transactionDTO);

  Iterable<Transaction> lookup();
}
