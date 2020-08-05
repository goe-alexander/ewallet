package com.example.ewallet.repositories;

import com.example.ewallet.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends CrudRepository<User, Long> {
  User findByCnp(Long cnp);

  boolean existsByCnp(Long cnp);
}
