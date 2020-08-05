package com.example.ewallet.resource;

import com.example.ewallet.domain.User;
import com.example.ewallet.dto.UserDTO;
import com.example.ewallet.dto.UserReportDTO;
import com.example.ewallet.exceptions.UserNotFoundException;
import com.example.ewallet.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserResource {
  private final UserService userService;
  private final UserResourceAssembler userResourceAssembler;

  @GetMapping("/{id}")
  EntityModel<User> getUser(@PathVariable Long id) {
    User user = userService.getUserById(id).orElseThrow(() -> new UserNotFoundException(id));
    return userResourceAssembler.toModel(user);
  }

  @GetMapping("/exists/{cnp}")
  boolean existsByCnp(@PathVariable Long cnp) {
    return userService.userExistsByCnp(cnp);
  }

  @GetMapping("/all")
  CollectionModel<EntityModel<User>> getAllUsers() {
    List<User> users = userService.getAllUsers();
    return userResourceAssembler.toCollectionModel(users);
  }

  @GetMapping("/report/{cnp}")
  UserReportDTO getUserReport(@PathVariable Long cnp) {
    return userService.getUserReportByCnp(cnp);
  }

  @GetMapping("/report/all")
  List<UserReportDTO> getAllUserReports() {
    return userService.getAllUserReports();
  }

  @PostMapping
  public void createUser(@Valid @RequestBody UserDTO userDTO) {
    userService.createUser(userDTO);
  }
}
