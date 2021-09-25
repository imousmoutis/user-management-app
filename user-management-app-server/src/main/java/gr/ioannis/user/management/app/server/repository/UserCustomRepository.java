package gr.ioannis.user.management.app.server.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import gr.ioannis.user.management.app.server.dto.UserDTO;
import gr.ioannis.user.management.app.server.model.QRole;
import gr.ioannis.user.management.app.server.model.QUser;
import gr.ioannis.user.management.app.server.model.User;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface UserCustomRepository {

  Page<UserDTO> findUsers(Predicate predicate, OrderSpecifier<?> orderSpecifier, Long page,
      Long size);

}

class UserCustomRepositoryImpl implements UserCustomRepository {

  private static final QUser qUser = QUser.user;

  private static final QRole qRole = QRole.role;

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public Page<UserDTO> findUsers(Predicate predicate, OrderSpecifier<?> orderSpecifier, Long page,
      Long size) {
    JPAQuery<UserDTO> query = new JPAQuery<User>(entityManager)
        .select(Projections
            .constructor(UserDTO.class, qUser.id, qUser.firstName, qUser.lastName,
                qUser.username))
        .from(qUser)
        .join(qUser.roles, qRole)
        .where(predicate)
        .offset(page * size)
        .limit(size)
        .orderBy(orderSpecifier);

    long total = query.fetchCount();
    List<UserDTO> userDTOS = query.fetch();

    return new PageImpl<>(userDTOS, PageRequest.of(Math.toIntExact(page), Math.toIntExact(size)), total);
  }
}
