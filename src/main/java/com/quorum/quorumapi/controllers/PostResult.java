package com.quorum.quorumapi.controllers;

import java.time.LocalDateTime;

public record PostResult(String title, LocalDateTime createdAt) {

}
