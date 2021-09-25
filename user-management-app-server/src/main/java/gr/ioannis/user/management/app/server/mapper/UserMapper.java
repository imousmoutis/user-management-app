package gr.ioannis.user.management.app.server.mapper;

import gr.ioannis.user.management.app.server.dto.UserDTO;
import gr.ioannis.user.management.app.server.model.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

  private final ModelMapper modelMapper;

  public User convert(UserDTO userDTO) {
    User user = modelMapper.map(userDTO, User.class);
    return user;
  }

  public UserDTO convertToDTO(User user) {
    UserDTO userDTO = modelMapper.map(user, UserDTO.class);
    userDTO.setPassword(null);
    return userDTO;
  }

}
