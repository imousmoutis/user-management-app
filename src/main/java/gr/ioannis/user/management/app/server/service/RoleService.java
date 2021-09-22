package gr.ioannis.user.management.app.server.service;

import gr.ioannis.user.management.app.server.model.Role;
import gr.ioannis.user.management.app.server.model.User;
import gr.ioannis.user.management.app.server.repository.RoleRepository;
import gr.ioannis.user.management.app.server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {

  private final RoleRepository roleRepository;

  public Role getRoleByName(String name){
    return roleRepository.findByName(name);
  }

}
