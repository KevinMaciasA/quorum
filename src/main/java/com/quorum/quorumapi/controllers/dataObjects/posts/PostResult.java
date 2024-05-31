package com.quorum.quorumapi.controllers.dataObjects.posts;

import java.time.LocalDateTime;

public record PostResult(Integer id, String title, LocalDateTime createdAt) {

}
