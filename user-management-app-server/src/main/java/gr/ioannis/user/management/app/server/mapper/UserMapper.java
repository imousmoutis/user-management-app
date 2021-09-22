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

  public UserDTO convertToDTO(User user) {
    UserDTO userDTO = modelMapper.map(user, UserDTO.class);
    return userDTO;
  }

}
