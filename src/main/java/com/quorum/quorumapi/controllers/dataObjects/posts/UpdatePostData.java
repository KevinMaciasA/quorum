package com.quorum.quorumapi.controllers.dataObjects.posts;

import com.quorum.quorumapi.models.StatusCode;

public record UpdatePostData(String title, String content, StatusCode status) {

}
