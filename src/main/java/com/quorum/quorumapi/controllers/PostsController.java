package com.quorum.quorumapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.quorum.quorumapi.errors.ErrorInfo;
import com.quorum.quorumapi.errors.PostDuplicationError;
import com.quorum.quorumapi.models.Post;
import com.quorum.quorumapi.models.User;
import com.quorum.quorumapi.repositories.PostsRepository;
import com.quorum.quorumapi.security.services.AuthUtilsService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/posts")
public class PostsController {
  @Autowired
  AuthUtilsService authService;
  @Autowired
  PostsRepository postsRepository;

  @PostMapping()
  public ResponseEntity<?> post(@RequestBody @Valid PostData data, UriComponentsBuilder uri) {
    var detailedUser = authService.getCurrentUser();

    if (detailedUser == null)
      return ResponseEntity
          .status(HttpStatus.UNAUTHORIZED)
          .body(new ErrorInfo(HttpStatus.UNAUTHORIZED, "User not authenticated"));

    if (postsRepository.existsByTitleAndContentIgnoreCase(data.title(), data.content()))
      throw new PostDuplicationError();

    var newPost = new Post(data, (User) detailedUser);
    var result = postsRepository.save(newPost);
    var path = uri.path("posts/{id}").buildAndExpand(result.getId()).toUri();
    return ResponseEntity
        .created(path)
        .body(new PostResult(result.getTitle(), result.getCreatedAt()));
  }
}
