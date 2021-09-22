package gr.ioannis.user.management.app.server.repository;

import gr.ioannis.user.management.app.server.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {

  Role findByName(String name);

}
