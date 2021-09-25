package gr.ioannis.user.management.app.server.builder;

public enum UserSearchSortColumn {

  FIRST_NAME("firstName"),
  LAST_NAME("lastName"),
  USERNAME("username");

  private final String columnName;

  UserSearchSortColumn(String columnName) {
    this.columnName = columnName;
  }

  public String getColumnName() {
    return columnName;
  }

}
