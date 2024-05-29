package com.quorum.quorumapi.security.services;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthUtilsService {
  public UserDetails getCurrentUser() {
    var authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null)
      return null;

    var principal = authentication.getPrincipal();

    var isUser = principal instanceof UserDetails;

    if (!isUser)
      return null;

    return (UserDetails) principal;
  }
}
