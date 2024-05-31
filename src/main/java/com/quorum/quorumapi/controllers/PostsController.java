package com.quorum.quorumapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.quorum.quorumapi.controllers.dataObjects.posts.PostData;
import com.quorum.quorumapi.controllers.dataObjects.posts.PostDetails;
import com.quorum.quorumapi.controllers.dataObjects.posts.UpdatePostData;
import com.quorum.quorumapi.errors.ErrorInfo;
import com.quorum.quorumapi.errors.PostDuplicationError;
import com.quorum.quorumapi.errors.PostNotFoundError;
import com.quorum.quorumapi.models.Post;
import com.quorum.quorumapi.models.User;
import com.quorum.quorumapi.repositories.PostsRepository;
import com.quorum.quorumapi.security.services.AuthUtilsService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/posts")
public class PostsController {
  @Autowired
  AuthUtilsService authService;
  @Autowired
  PostsRepository postsRepository;

  @PostMapping
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
        .body(result.details());
  }

  @GetMapping
  public ResponseEntity<Page<PostDetails>> getPosts(
      @PageableDefault(size = 10, sort = "createdAt", direction = Direction.DESC) Pageable pageable) {
    var posts = postsRepository.getPosts(pageable);
    return ResponseEntity.ok().body(posts);
  }

  @GetMapping("/{id}")
  public ResponseEntity<PostDetails> getPostById(@PathVariable Integer id) {
    if (!postsRepository.existsById(id))
      throw new PostNotFoundError(id);

    var post = postsRepository.getReferenceById(id);
    return ResponseEntity.ok(post.details());
  }

  @PutMapping("/{id}")
  @Transactional
  public ResponseEntity<PostDetails> updatePostById(@PathVariable Integer id, @RequestBody UpdatePostData data) {
    if (!postsRepository.existsById(id))
      throw new PostNotFoundError(id);

    var post = postsRepository.getReferenceById(id);
    post.update(data);
    return ResponseEntity.ok(post.details());
  }

  @DeleteMapping("/{id}")
  @Transactional
  public ResponseEntity<Void> deletePostById(@PathVariable Integer id) {
    postsRepository.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
