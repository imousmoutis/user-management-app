package gr.ioannis.user.management.app.server.builder;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import gr.ioannis.user.management.app.server.model.QRole;
import gr.ioannis.user.management.app.server.model.QUser;
import org.springframework.stereotype.Component;

@Component
public class UserSearchBuilder {

  private static final QUser qUser = QUser.user;

  private static final QRole qRole = QRole.role;

  public BooleanBuilder buildSearchPredicate(String searchValue) {
    BooleanBuilder booleanBuilder = new BooleanBuilder();

    if ((searchValue != null) && (!searchValue.isEmpty())) {
      booleanBuilder.or(qUser.firstName.containsIgnoreCase(searchValue));
      booleanBuilder.or(qUser.lastName.containsIgnoreCase(searchValue));
      booleanBuilder.or(qUser.username.containsIgnoreCase(searchValue));
    }

    booleanBuilder.and(qRole.name.notEqualsIgnoreCase("ADMIN"));

    return booleanBuilder;
  }

  public OrderSpecifier<?> buildOrder(String sortColumn, String sortOrder) {
    OrderSpecifier<?> orderSpecifier;

    if (sortColumn.equals(UserSearchSortColumn.FIRST_NAME.getColumnName())) {
      if (sortOrder.equalsIgnoreCase("DESC")) {
        orderSpecifier = qUser.firstName.desc();
      } else {
        orderSpecifier = qUser.firstName.asc();
      }
    } else if (sortColumn.equals(UserSearchSortColumn.LAST_NAME.getColumnName())) {
      if (sortOrder.equalsIgnoreCase("DESC")) {
        orderSpecifier = qUser.lastName.desc();
      } else {
        orderSpecifier = qUser.lastName.asc();
      }
    } else {
      if (sortOrder.equalsIgnoreCase("DESC")) {
        orderSpecifier = qUser.username.desc();
      } else {
        orderSpecifier = qUser.username.asc();
      }
    }

    return orderSpecifier;
  }

}
