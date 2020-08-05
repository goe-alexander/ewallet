package com.example.ewallet;

import com.example.ewallet.domain.Transaction;
import com.example.ewallet.domain.User;
import com.example.ewallet.dto.TransactionDTO;
import com.example.ewallet.dto.UserDTO;
import com.example.ewallet.services.TransactionService;
import com.example.ewallet.services.UserService;
import com.example.ewallet.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;

import static com.example.ewallet.domain.TransactionType.IBAN_TO_IBAN;
import static com.example.ewallet.domain.TransactionType.IBAN_TO_WALLET;
import static com.example.ewallet.domain.TransactionType.WALLET_TO_IBAN;
import static com.example.ewallet.domain.TransactionType.WALLET_TO_WALLET;

@SpringBootApplication()
public class EwalletApplication implements CommandLineRunner {

  @Autowired private UserService userService;
  @Autowired private TransactionService transactionService;
  @Autowired private Mapper mapper;

  public static void main(String[] args) {
    SpringApplication.run(EwalletApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    addSomeDummyData();
  }

  private void addSomeDummyData() {
    UserDTO userDTO =
        UserDTO.builder()
            .firstName("Ionel")
            .lastName("1")
            .email("i1@email.com")
            .cnp(190101534998821L)
            .build();
    TransactionDTO transactionDTO =
        TransactionDTO.builder()
            .iban("RO2103123NSV")
            .cnp(189034534123L)
            .dateTime(LocalDateTime.now())
            .description("desc1")
            .name("I1")
            .type(IBAN_TO_IBAN.name())
            .sum(123.0)
            .build();

    userService.createUser(userDTO);
    User us1 = userService.getUserByCnp(userDTO.getCnp()).get();

    userDTO.setCnp(190111534998822L);
    userService.createUser(userDTO);
    User us2 = userService.getUserByCnp(userDTO.getCnp()).get();

    userDTO.setCnp(190111534998823L);
    userService.createUser(userDTO);
    User us3 = userService.getUserByCnp(userDTO.getCnp()).get();

    // Save transactions for User 1
    Transaction t1 = mapper.map(transactionDTO, Transaction.class);
    t1.setUser(us1);

    transactionDTO.setType(WALLET_TO_IBAN.name());
    transactionDTO.setSum(20.0);
    Transaction t2 = mapper.map(transactionDTO, Transaction.class);
    t2.setUser(us1);

    transactionService.saveTransaction(t1);
    transactionService.saveTransaction(t2);

    // save transactions for user 2
    transactionDTO.setType(WALLET_TO_WALLET.name());
    Transaction t2_1 = mapper.map(transactionDTO, Transaction.class);
    t2_1.setUser(us2);

    transactionDTO.setType(IBAN_TO_WALLET.name());
    transactionDTO.setSum(20.0);
    Transaction t2_2 = mapper.map(transactionDTO, Transaction.class);
    t2_2.setUser(us2);

    transactionService.saveTransaction(t2_1);
    transactionService.saveTransaction(t2_2);

    transactionService.lookup().forEach(System.out::println);
    userService.lookup().forEach(System.out::println);
  }
}
