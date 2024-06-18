package com.quorum.quorumapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.quorum.quorumapi.controllers.dataObjects.comments.CommentData;
import com.quorum.quorumapi.controllers.dataObjects.comments.CommentDetail;
import com.quorum.quorumapi.controllers.dataObjects.comments.CommentResult;
import com.quorum.quorumapi.controllers.validations.comments.ICommentValidation;
import com.quorum.quorumapi.errors.InvalidCredentials;
import com.quorum.quorumapi.errors.PostNotFoundError;
import com.quorum.quorumapi.models.Comment;
import com.quorum.quorumapi.models.User;
import com.quorum.quorumapi.repositories.CommentsRepository;
import com.quorum.quorumapi.repositories.PostsRepository;
import com.quorum.quorumapi.security.services.AuthUtilsService;

import java.util.List;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/posts/{postId}/comments")
public class CommentsController {
  @Autowired
  AuthUtilsService authService;
  @Autowired
  PostsRepository postsRepository;
  @Autowired
  CommentsRepository commentsRepository;
  @Autowired
  List<ICommentValidation> validations;

  @PostMapping
  public ResponseEntity<CommentResult> addComment(
      @PathVariable Integer postId,
      @Valid @RequestBody CommentData data,
      UriComponentsBuilder uri) {
    var detailedUser = authService.getCurrentUser();

    if (detailedUser == null)
      throw new InvalidCredentials();

    if (!postsRepository.existsById(postId))
      throw new PostNotFoundError(postId);

    var user = (User) detailedUser;
    validations.forEach(validation -> validation.test(data, user.getId()));

    var post = postsRepository.getReferenceById(postId);
    var comment = new Comment(post, user, data.content());
    var result = commentsRepository.save(comment);
    var path = uri.path("posts/{postId}/comments/{id}").buildAndExpand(postId, result.getId()).toUri();
    return ResponseEntity.created(path).body(
        new CommentResult(result.getId(), result.getAuthor().getUsername(), result.getCreatedAt()));
  }

  @GetMapping
  public ResponseEntity<List<CommentDetail>> getComments(@PathVariable Integer postId) {
    if (!postsRepository.existsById(postId))
      throw new PostNotFoundError();

    var comments = commentsRepository.findByPostId(postId);
    var detailedComments = comments.stream().map(c -> c.details()).toList();
    return ResponseEntity.ok(detailedComments);
  }
}
