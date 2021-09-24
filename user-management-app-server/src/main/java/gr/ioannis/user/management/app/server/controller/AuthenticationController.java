package gr.ioannis.user.management.app.server.controller;

import gr.ioannis.user.management.app.server.config.security.JWTProvider;
import gr.ioannis.user.management.app.server.dto.LoginUserDTO;
import gr.ioannis.user.management.app.server.dto.TokenDTO;
import gr.ioannis.user.management.app.server.dto.UserDTO;
import gr.ioannis.user.management.app.server.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

  private final UserService userService;

  private final AuthenticationManager authenticationManager;

  private final JWTProvider jwtProvider;

  @PostMapping(value = "/authenticate")
  public ResponseEntity<TokenDTO> generateToken(@RequestBody LoginUserDTO loginUser) throws AuthenticationException {
    return ResponseEntity.ok(new TokenDTO(performAuthentication(loginUser)));
  }

  @PostMapping(value = "/register")
  public ResponseEntity<TokenDTO> registerUser(@RequestBody UserDTO userDTO) {
    userService.saveUser(userDTO);
    return ResponseEntity.ok(new TokenDTO(performAuthentication(new LoginUserDTO(userDTO.getUsername(),
        userDTO.getPassword()))));
  }

  private String performAuthentication(LoginUserDTO loginUser) {
    final Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            loginUser.getUsername(),
            loginUser.getPassword()
        )
    );
    SecurityContextHolder.getContext().setAuthentication(authentication);
    return jwtProvider.generateToken(authentication);
  }

}
