package com.quorum.quorumapi.controllers.dataObjects.login;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginData(
        @Email String email,
        @NotBlank String password) {

}
