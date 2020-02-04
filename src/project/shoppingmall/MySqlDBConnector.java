package project.shoppingmall;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.function.Consumer;

public class MySqlDBConnector implements DBConnector {
  @Override
  public int insert(String query, Consumer<PreparedStatement> consumer) {
    return 0;
  }

  @Override
  public ResultSet select(String query, Consumer<PreparedStatement> consumer) {
    return null;
  }

  @Override
  public int update(String query, Consumer<PreparedStatement> consumer) {
    return 0;
  }

  @Override
  public int delete(String query, Consumer<PreparedStatement> consumer) {
    return 0;
  }
}
