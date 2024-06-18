package com.quorum.quorumapi.errors;

public class ValidationError extends Error {
  public ValidationError(String error) {
    super(error);
  }
}
