package com.quorum.quorumapi.controllers.validations.comments;

import com.quorum.quorumapi.controllers.dataObjects.comments.CommentData;

public interface ICommentValidation {
  void test(CommentData data, Integer id);
}
