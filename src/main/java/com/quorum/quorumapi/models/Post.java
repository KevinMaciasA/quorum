package com.quorum.quorumapi.models;

import java.time.LocalDateTime;
import java.util.List;

import com.quorum.quorumapi.controllers.dataObjects.posts.PostData;
import com.quorum.quorumapi.controllers.dataObjects.posts.PostDetails;
import com.quorum.quorumapi.controllers.dataObjects.posts.UpdatePostData;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "posts")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Post {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @Column(nullable = false)
  private String title;
  @Column(length = 5000)
  private String content;
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
  @JoinColumn(name = "status_id", nullable = false)
  private Status status;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "author_id", nullable = false)
  private User author;
  @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<Comment> comments;
  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;
  @Column(name = "modification_date")
  private LocalDateTime modificationDate;

  @PrePersist
  protected void onCreate() {
    this.createdAt = LocalDateTime.now();
    this.status = Status.of(StatusCode.ACTIVE);
  }

  public Post(PostData data, User author) {
    this.title = data.title();
    this.content = data.content();
    this.author = author;
  }

  public PostDetails details() {
    return new PostDetails(this);
  }

  public void update(UpdatePostData data) {
    boolean somethingChange = false;
    somethingChange |= updateTitle(data);
    somethingChange |= updateContent(data);
    somethingChange |= updateStatus(data);

    if (somethingChange)
      modificationDate = LocalDateTime.now();

  }

  private boolean updateTitle(UpdatePostData data) {
    if (data.title() == null
        || data.title().isBlank()
        || data.title().equals(this.title))
      return false;
    title = data.title();
    return true;
  }

  private boolean updateContent(UpdatePostData data) {
    if (data.content() == null
        || data.content().isBlank()
        || data.content().equals(this.content))
      return false;
    content = data.content();
    return true;
  }

  private boolean updateStatus(UpdatePostData data) {
    if (data.status() == null || status.getCode().equals(data.status()))
      return false;
    status = new Status(data.status());
    return true;
  }
}
