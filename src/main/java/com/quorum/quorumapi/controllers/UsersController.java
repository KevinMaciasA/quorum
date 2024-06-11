package com.quorum.quorumapi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quorum.quorumapi.controllers.dataObjects.user.UserData;
import com.quorum.quorumapi.controllers.dataObjects.user.UserDetails;
import com.quorum.quorumapi.controllers.dataObjects.user.UserUpdateData;
import com.quorum.quorumapi.errors.InvalidCredentials;
import com.quorum.quorumapi.errors.UserNotFoundError;
import com.quorum.quorumapi.repositories.UsersRepository;
import com.quorum.quorumapi.security.services.AuthUtilsService;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/users")
public class UsersController {
  @Autowired
  UsersRepository usersRepository;
  @Autowired
  AuthUtilsService authService;
  @Autowired
  PasswordEncoder passwordEncoder;

  @GetMapping("/{id}")
  public ResponseEntity<?> getUser(@PathVariable Integer id) {
    if (!usersRepository.existsById(id))
      throw new UserNotFoundError(id);

    var user = usersRepository.getReferenceById(id);

    if (authService.isCurrentUser(id))
      return ResponseEntity.ok(user.userData());

    return ResponseEntity.ok(new UserDetails(user.getName()));
  }

  @GetMapping
  public ResponseEntity<List<UserData>> getAllUsers() {
    List<UserData> allUsers = usersRepository
        .findAll()
        .stream()
        .map(u -> u.userData())
        .toList();
    return ResponseEntity.ok(allUsers);
  }

  @PutMapping("/{id}")
  @Transactional
  public ResponseEntity<?> updateYourUser(@PathVariable Integer id, @RequestBody UserUpdateData data) {
    if (!usersRepository.existsById(id))
      throw new UserNotFoundError(id);

    if (data.username() == null && data.password() == null)
      return ResponseEntity.badRequest().build();

    if (!authService.isCurrentUser(id))
      throw new InvalidCredentials();

    var user = usersRepository.getReferenceById(id);
    if (data.password() == null)
      user.update(data.username(), data.password());
    else {
      var encodedPassword = passwordEncoder.encode(data.password());
      user.update(data.username(), encodedPassword);
    }

    return ResponseEntity.ok(user.userData());
  }

  @DeleteMapping("/{id}")
  @Transactional
  public ResponseEntity<?> deleteYourUser(@PathVariable Integer id) {
    if (!usersRepository.existsById(id))
      throw new UserNotFoundError(id);

    if (!authService.isCurrentUser(id) && !authService.isMod(id))
      throw new InvalidCredentials();

    usersRepository.deleteById(id);

    return ResponseEntity.noContent().build();
  }
}
