package com.quorum.quorumapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.quorum.quorumapi.models.User;

public interface UsersRepository extends JpaRepository<User, Integer> {
  UserDetails findByEmail(String email);

  boolean existsByEmail(String email);
}
