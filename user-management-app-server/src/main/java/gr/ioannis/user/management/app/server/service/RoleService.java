package gr.ioannis.user.management.app.server.service;

import gr.ioannis.user.management.app.server.model.Role;
import gr.ioannis.user.management.app.server.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {

  private final RoleRepository roleRepository;

  public Role getRoleByName(String name) {
    return roleRepository.findByName(name);
  }

}
