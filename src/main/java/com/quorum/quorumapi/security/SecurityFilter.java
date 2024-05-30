package com.quorum.quorumapi.security;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.quorum.quorumapi.repositories.UsersRepository;
import com.quorum.quorumapi.security.services.TokenService;

@Component
public class SecurityFilter extends OncePerRequestFilter {

  @Autowired
  private TokenService tokenService;
  @Autowired
  private UsersRepository usersRepository;
  private List<String> ignorePaths = List.of("/login", "/register", "/test");

  @Override
  protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain)
      throws ServletException, IOException {

    if (!ignorePaths.contains(request.getServletPath())) {
      try {
        var token = request.getHeader("Authorization");
        UserDetails user = checkAuthUser(token);

        if (user != null) {
          var authToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
          SecurityContextHolder.getContext().setAuthentication(authToken);
        }
      } catch (Exception e) {
        handleException(response, e);
        return;
      }
    }

    filterChain.doFilter(request, response);
  }

  private void handleException(HttpServletResponse response, Exception e) throws IOException {
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    response.setContentType("application/json");
    response.getWriter().write("""
        {
          error: %s
        }""".formatted(e.getMessage()));
  }

  private UserDetails checkAuthUser(String token) throws JWTVerificationException {
    if (token == null)
      return null;

    final String prefix = "Bearer ";

    if (!token.startsWith(prefix))
      return null;

    var email = tokenService.validateTokenAndGetEmail(
        token.substring(prefix.length()));

    UserDetails user = usersRepository.findByEmail(email);

    if (user == null)
      throw new UsernameNotFoundException("User not found");

    return user;
  }
}
