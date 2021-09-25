package gr.ioannis.user.management.app.server.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

  private String id;

  private String firstName;

  private String lastName;

  private String username;

  private String password;

  public UserDTO(String id, String firstName, String lastName, String username) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.username = username;
  }
}
