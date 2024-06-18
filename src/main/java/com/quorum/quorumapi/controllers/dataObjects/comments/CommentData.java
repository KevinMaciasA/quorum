package com.quorum.quorumapi.controllers.dataObjects.comments;

import jakarta.validation.constraints.NotBlank;

public record CommentData(@NotBlank String content) {

}
