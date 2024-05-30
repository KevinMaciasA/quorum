package com.quorum.quorumapi.controllers.dataObjects.posts;

import java.time.LocalDateTime;

public record PostResult(String title, LocalDateTime createdAt) {

}
