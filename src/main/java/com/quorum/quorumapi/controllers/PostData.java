package com.quorum.quorumapi.controllers;

import jakarta.validation.constraints.NotBlank;

public record PostData(
                @NotBlank String title,
                @NotBlank String content) {

}
