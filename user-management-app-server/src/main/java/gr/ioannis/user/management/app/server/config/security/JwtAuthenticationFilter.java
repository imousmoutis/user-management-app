package gr.ioannis.user.management.app.server.config.security;

import gr.ioannis.user.management.app.server.service.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final UserService userService;
  private final JWTProvider jwtProvider;
  @Value("${jwt.header.string}")
  public String HEADER_STRING;
  @Value("${jwt.token.prefix}")
  public String TOKEN_PREFIX;

  @Override
  protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
      throws IOException, ServletException {
    String header = req.getHeader(HEADER_STRING);
    String username = null;
    String authToken = null;
    if (header != null && header.startsWith(TOKEN_PREFIX)) {
      authToken = header.replace(TOKEN_PREFIX, "");
      try {
        username = jwtProvider.getUsernameFromToken(authToken);
      } catch (IllegalArgumentException e) {
        log.error("An error occurred while fetching Username from Token", e);
      } catch (ExpiredJwtException e) {
        log.warn("The token has expired", e);
      } catch (SignatureException e) {
        log.error("Authentication Failed. Username or Password not valid.");
      }
    }
    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

      UserDetails userDetails = userService.loadUserByUsername(username);

      if (jwtProvider.validateToken(authToken, userDetails)) {
        UsernamePasswordAuthenticationToken authentication = jwtProvider.getAuthenticationToken(authToken, userDetails);
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
        log.info("authenticated user " + username + ", setting security context");
        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    }

    chain.doFilter(req, res);
  }
}
