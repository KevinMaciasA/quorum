package com.quorum.quorumapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.quorum.quorumapi.models.Post;

public interface PostsRepository extends JpaRepository<Post, Integer> {
  boolean existsByTitleAndContentIgnoreCase(String title, String content);
}
