package gr.ioannis.user.management.app.server.repository;

import gr.ioannis.user.management.app.server.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

  User findByUsername(String username);

}
