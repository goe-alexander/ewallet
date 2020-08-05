package com.example.ewallet.services;

import com.example.ewallet.domain.Transaction;
import com.example.ewallet.domain.User;
import com.example.ewallet.dto.TransactionDTO;
import com.example.ewallet.exceptions.UserNotFoundException;
import com.example.ewallet.interfaces.ITransactionService;
import com.example.ewallet.repositories.TransactionRepository;
import com.example.ewallet.utils.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TransactionService implements ITransactionService {
  private final Mapper mapper;
  private final UserService userService;
  private final TransactionRepository transactionRepository;

  @Override
  public void saveTransaction(Transaction transaction) {
    transactionRepository.save(transaction);
  }

  @Override
  public void saveTransaction(TransactionDTO transactionDTO) {
    Transaction transaction = mapper.map(transactionDTO, Transaction.class);
    Optional<User> existingUser = userService.getUserByCnp(transactionDTO.getCnp());
    if (!existingUser.isPresent()) {
      throw new UserNotFoundException(transactionDTO.getCnp(), "Transaction Discarded");
    }
    transaction.setUser(existingUser.get());
    transactionRepository.save(transaction);
  }

  public Iterable<Transaction> lookup() {
    return transactionRepository.findAll();
  }
}
