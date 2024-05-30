package com.quorum.quorumapi.errors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostNotFoundError extends Error {
  private Integer id;
}
