package com.quorum.quorumapi.models;

public enum StatusCode {
  ACTIVE("Active"),
  HIDE("Hide"),
  DELETED("Deleted"),
  RESOLVED("Resolved");

  private String name;

  StatusCode(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return name;
  }
}
