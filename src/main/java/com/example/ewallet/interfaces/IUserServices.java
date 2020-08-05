package com.example.ewallet.interfaces;

import com.example.ewallet.domain.User;
import com.example.ewallet.dto.UserDTO;
import com.example.ewallet.dto.UserReportDTO;

import java.util.List;
import java.util.Optional;

public interface IUserServices {
  List<User> getAllUsers();

  Optional<User> getUserById(Long id);

  Optional<User> getUserByCnp(Long cnp);

  List<UserReportDTO> getAllUserReports();

  UserReportDTO getUserReportByCnp(Long cnp);

  boolean userExistsByCnp(Long cnp);

  void createUser(UserDTO userDTO);

  void deleteUserByCnp(Long CNP);

  void updateUser(UserDTO newUserValues);
}
