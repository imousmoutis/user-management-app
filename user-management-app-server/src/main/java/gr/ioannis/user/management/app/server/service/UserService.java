package gr.ioannis.user.management.app.server.service;

import gr.ioannis.user.management.app.server.dto.UserDTO;
import gr.ioannis.user.management.app.server.mapper.UserMapper;
import gr.ioannis.user.management.app.server.model.Role;
import gr.ioannis.user.management.app.server.model.User;
import gr.ioannis.user.management.app.server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

  private final UserRepository userRepository;

  private final UserMapper userMapper;

  private final RoleService roleService;

  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByUsername(username);
    if (user == null) {
      throw new UsernameNotFoundException("Invalid username or password.");
    }
    return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
        getAuthority(user));
  }

  public void saveUser(UserDTO userDTO) {
    User user = userMapper.convert(userDTO);

    Role role = roleService.getRoleByName("VIEWER");
    Set<Role> roleSet = new HashSet<>();
    roleSet.add(role);

    user.setRoles(roleSet);
    user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

    userRepository.save(user);
  }

  public UserDTO getUser(String username) {
    return userMapper.convertToDTO(userRepository.findByUsername(username));
  }

  public Boolean userExistsByUsername(String username) {
    return userRepository.findByUsername(username) != null;
  }

  private Set<SimpleGrantedAuthority> getAuthority(User user) {
    Set<SimpleGrantedAuthority> authorities = new HashSet<>();
    user.getRoles().forEach(role -> {
      authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
    });
    return authorities;
  }
}
