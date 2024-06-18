package com.quorum.quorumapi.controllers.dataObjects.comments;

import java.time.LocalDateTime;

import com.quorum.quorumapi.models.Comment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommentDetail {
  private Integer id;
  private String content;
  private String authorName;
  private String authorEmail;
  private LocalDateTime createdAt;

  public CommentDetail(Comment comment) {
    this.id = comment.getId();
    this.content = comment.getContent();
    this.authorName = comment.getAuthor().getName();
    this.authorEmail = comment.getAuthor().getUsername();
    this.createdAt = comment.getCreatedAt();
  }
}
