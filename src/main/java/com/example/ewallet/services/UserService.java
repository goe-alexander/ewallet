package com.example.ewallet.services;

import com.example.ewallet.domain.User;
import com.example.ewallet.dto.TransactionTypeReportDTO;
import com.example.ewallet.dto.UserDTO;
import com.example.ewallet.dto.UserReportDTO;
import com.example.ewallet.exceptions.ResourceNotFoundException;
import com.example.ewallet.exceptions.UserNotFoundException;
import com.example.ewallet.interfaces.IUserServices;
import com.example.ewallet.interfaces.TransactionReportProj;
import com.example.ewallet.repositories.TransactionRepository;
import com.example.ewallet.repositories.UserRepository;
import com.example.ewallet.utils.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService implements IUserServices {
  private final UserRepository userRepository;
  private final TransactionRepository transactionRepository;
  private final Mapper mapper;

  @Override
  public List<User> getAllUsers() {
    List<User> allUsers = new ArrayList<User>();
    userRepository.findAll().forEach(allUsers::add);
    return allUsers;
  };

  @Override
  public Optional<User> getUserById(Long id) {
    return userRepository.findById(id);
  }

  @Override
  public Optional<User> getUserByCnp(Long cnp) {
    return Optional.ofNullable(userRepository.findByCnp(cnp));
  }

  @Override
  @Transactional
  public List<UserReportDTO> getAllUserReports() {
    List<TransactionReportProj> allUserTransactions =
        transactionRepository.getAllTransactionProjections();
    if (allUserTransactions.isEmpty()) {
      throw new ResourceNotFoundException("No general report found!");
    }

    return getAllTransactionTypeReports(allUserTransactions);
  }

  @Override
  @Transactional(readOnly = true)
  public UserReportDTO getUserReportByCnp(Long cnp) {
    List<TransactionReportProj> userTransactions =
        transactionRepository.getReportProjectionByCnp(cnp);
    if (userTransactions.isEmpty()) {
      throw new ResourceNotFoundException("Report Does not exits for cnp: " + cnp);
    }
    return getUserReportDto(cnp, userTransactions);
  }

  @Override
  public boolean userExistsByCnp(Long cnp) {
    return userRepository.existsByCnp(cnp);
  }

  @Override
  public void createUser(UserDTO userDTO) {
    if (userExistsByCnp(userDTO.getCnp())) {
      return;
    }

    User newUser = mapper.map(userDTO, User.class);
    userRepository.save(newUser);
  }

  @Override
  public void deleteUserByCnp(Long cnp) {
    User existingUser = getExistingUserByCnp(cnp);
    userRepository.delete(existingUser);
  }

  @Override
  public void updateUser(UserDTO newUserValues) {
    User existingUser = getExistingUserByCnp(newUserValues.getCnp());
    User updatedUser = mapper.map(newUserValues, User.class);
    mapper.map(updatedUser, existingUser);
    userRepository.save(existingUser);
  }

  public Iterable<User> lookup() {
    return userRepository.findAll();
  }

  private List<UserReportDTO> getAllTransactionTypeReports(
      List<TransactionReportProj> allTransactions) {
    List<UserReportDTO> userReportDTOs = new ArrayList<>();
    Map<Long, List<TransactionReportProj>> allUsersReportMap =
        allTransactions.stream().collect(groupingBy(TransactionReportProj::getCnp));
    allUsersReportMap
        .entrySet()
        .forEach(entry -> userReportDTOs.add(getUserReportDto(entry.getKey(), entry.getValue())));
    return userReportDTOs;
  }

  private UserReportDTO getUserReportDto(Long cnp, List<TransactionReportProj> userTransactions) {
    return UserReportDTO.builder()
        .cnp(cnp)
        .iban(userTransactions.stream().map(TransactionReportProj::getIban).findFirst().get())
        .typeDetails(getTransactionTypeReportsForUser(userTransactions))
        .build();
  }

  private List<TransactionTypeReportDTO> getTransactionTypeReportsForUser(
      List<TransactionReportProj> transactions) {
    Map<String, List<TransactionReportProj>> transactionsByType =
        transactions.stream().collect(groupingBy(TransactionReportProj::getType));

    List<TransactionTypeReportDTO> typeReportDTOS =
        transactionsByType.entrySet().stream()
            .map(
                entry ->
                    TransactionTypeReportDTO.builder()
                        .type(entry.getKey())
                        .count(entry.getValue().size())
                        .sum(
                            entry.getValue().stream()
                                .mapToDouble(TransactionReportProj::getSum)
                                .sum())
                        .build())
            .collect(Collectors.toList());
    return typeReportDTOS;
  }

  private User getExistingUserByCnp(Long cnp) {
    User existingUser = userRepository.findByCnp(cnp);
    if (existingUser == null) {
      throw new UserNotFoundException(cnp, null);
    }
    return existingUser;
  }
}
