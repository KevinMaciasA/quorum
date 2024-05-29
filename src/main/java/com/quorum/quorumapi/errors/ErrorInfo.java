package com.quorum.quorumapi.errors;

import org.springframework.http.HttpStatus;

public record ErrorInfo(HttpStatus ErrorCode, String errorMessage) {

}
