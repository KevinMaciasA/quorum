package com.quorum.quorumapi.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.quorum.quorumapi.controllers.dataObjects.posts.PostDetails;
import com.quorum.quorumapi.models.Post;

public interface PostsRepository extends JpaRepository<Post, Integer> {
  boolean existsByTitleAndContentIgnoreCase(String title, String content);

  // @Query("SELECT p FROM Post p JOIN p.status s WHERE s.code != 'HIDE' ORDER BY
  // p.createdAt DESC")
  @Query("SELECT p FROM Post p JOIN p.status s WHERE s.code != 'HIDE'")
  Page<PostDetails> getPosts(Pageable pageable);
}
