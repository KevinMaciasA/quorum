package com.quorum.quorumapi.models;

import java.time.LocalDateTime;

public record UserData(Integer id, String email, String username, LocalDateTime createdAt) {

}
