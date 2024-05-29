package com.quorum.quorumapi.controllers;

import java.time.LocalDateTime;

public record PostDetails(
    String title,
    String content,
    String authorName,
    String authorEmail,
    String status,
    LocalDateTime createdAt) {

}
