package gr.ioannis.user.management.app.server.controller;

import gr.ioannis.user.management.app.server.builder.UserSearchSortColumn;
import gr.ioannis.user.management.app.server.dto.UserDTO;
import gr.ioannis.user.management.app.server.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @GetMapping(value = "/exists/{username}")
  public ResponseEntity<Boolean> existsByUsername(@PathVariable String username) {
    return ResponseEntity.ok(userService.userExistsByUsername(username));
  }

  @GetMapping
  public ResponseEntity<UserDTO> getUser(Principal principal) {
    return ResponseEntity.ok(userService.getUser(principal.getName()));
  }

  //@PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/search")
  public Page<UserDTO> findUsers(@RequestParam(name = "value", required = false) String value,
      @RequestParam(name = "sortColumn", required = false) String sortColumn,
      @RequestParam(name = "sortOrder", required = false) String sortOrder,
      @RequestParam(name = "page", required = false, defaultValue = "0") String page, @RequestParam(name = "size",
      required = false, defaultValue = "10") String size) {

    if ((sortColumn == null) || (sortColumn.isEmpty())) {
      sortColumn = UserSearchSortColumn.USERNAME.getColumnName();
    }
    if ((sortOrder == null) || (sortOrder.isEmpty())) {
      sortOrder = "DESC";
    }
    return userService.findUsers(value, sortColumn, sortOrder, Long.valueOf(page), Long.valueOf(size));
  }

}
