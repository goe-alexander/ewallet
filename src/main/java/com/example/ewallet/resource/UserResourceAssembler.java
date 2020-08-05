package com.example.ewallet.resource;

import com.example.ewallet.domain.User;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class UserResourceAssembler
    implements RepresentationModelAssembler<User, EntityModel<User>> {

  @Override
  public EntityModel<User> toModel(User user) {
    return EntityModel.of(
        user,
        linkTo(methodOn(UserResource.class).getUser(user.getId())).withSelfRel(),
        linkTo(methodOn(UserResource.class).getAllUsers()).withRel("all"));
  }
}
