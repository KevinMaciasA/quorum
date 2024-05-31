package com.quorum.quorumapi.controllers.dataObjects.posts;

import java.time.LocalDateTime;

import com.quorum.quorumapi.models.Post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostDetails {
    private Integer id;
    private String title;
    private String content;
    private String authorName;
    private String authorEmail;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime modificationDate;

    public PostDetails(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.authorName = post.getAuthor().getName();
        this.authorEmail = post.getAuthor().getUsername();
        this.status = post.getStatus().getName();
        this.createdAt = post.getCreatedAt();
        this.modificationDate = post.getModificationDate();
    }
}
