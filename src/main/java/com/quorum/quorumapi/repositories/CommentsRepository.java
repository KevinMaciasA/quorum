package com.quorum.quorumapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.quorum.quorumapi.models.Comment;

import java.util.List;

public interface CommentsRepository extends JpaRepository<Comment, Integer> {
  List<Comment> findByPostId(Integer postId);

  Comment findByAuthorIdAndContentIgnoreCase(Integer authorId, String content);
}
