package com.quorum.quorumapi.controllers.dataObjects;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterData(
        @Email String email,
        @NotBlank String password,
        @NotBlank String username) {

}
