package gr.ioannis.user.management.app.server.controller;

import gr.ioannis.user.management.app.server.config.security.JWTProvider;
import gr.ioannis.user.management.app.server.dto.LoginUserDTO;
import gr.ioannis.user.management.app.server.dto.TokenDTO;
import gr.ioannis.user.management.app.server.dto.UserDTO;
import gr.ioannis.user.management.app.server.mapper.UserMapper;
import gr.ioannis.user.management.app.server.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  private final UserMapper userMapper;

  private final AuthenticationManager authenticationManager;

  private final JWTProvider jwtProvider;

  @PostMapping(value = "/authenticate")
  public ResponseEntity<?> generateToken(@RequestBody LoginUserDTO loginUser) throws AuthenticationException {

    final Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            loginUser.getUsername(),
            loginUser.getPassword()
        )
    );
    SecurityContextHolder.getContext().setAuthentication(authentication);
    final String token = jwtProvider.generateToken(authentication);
    return ResponseEntity.ok(new TokenDTO(token));
  }

  @GetMapping(value = "/exists/{username}")
  public ResponseEntity<Boolean> existsByUsername(@PathVariable String username) {
    return ResponseEntity.ok(userService.userExistsByUsername(username));
  }

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping
  public List<UserDTO> getAllUsers() {
    return userService.getAllUsers().stream().map(userMapper::convertToDTO).collect(Collectors.toList());
  }

}
