package com.quorum.quorumapi.controllers.validations.comments;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.quorum.quorumapi.controllers.dataObjects.comments.CommentData;
import com.quorum.quorumapi.errors.ValidationError;
import com.quorum.quorumapi.repositories.CommentsRepository;

@Component
public class DuplicateCommentValidation implements ICommentValidation {
  @Autowired
  CommentsRepository repository;
  private String errorMessage = "Error: Duplicate Comment Detected. Please avoid posting the same content multiple times.";

  @Override
  public void test(CommentData data, Integer id) {
    var comment = repository.findByAuthorIdAndContentIgnoreCase(id, data.content());

    if (comment != null)
      throw new ValidationError(errorMessage);
  }
}
