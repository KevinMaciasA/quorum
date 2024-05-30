package com.quorum.quorumapi.controllers.dataObjects.posts;

import jakarta.validation.constraints.NotBlank;

public record PostData(
        @NotBlank String title,
        @NotBlank String content) {

}
