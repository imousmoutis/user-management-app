package gr.ioannis.user.management.app.server.controller;

import gr.ioannis.user.management.app.server.dto.UserDTO;
import gr.ioannis.user.management.app.server.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @GetMapping(value = "/exists/{username}")
  public ResponseEntity<Boolean> existsByUsername(@PathVariable String username) {
    return ResponseEntity.ok(userService.userExistsByUsername(username));
  }

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping
  public List<UserDTO> getAllUsers() {
    return null;
    //return userService.getAllUsers().stream().map(userMapper::convertToDTO).collect(Collectors.toList());
  }

}
