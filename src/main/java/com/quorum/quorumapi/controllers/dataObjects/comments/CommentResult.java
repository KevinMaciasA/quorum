package com.quorum.quorumapi.controllers.dataObjects.comments;

import java.time.LocalDateTime;

public record CommentResult(Integer id, String authorId, LocalDateTime createdAt) {

}
