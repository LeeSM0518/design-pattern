package project.shopping_mall.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.function.Consumer;

public interface DBConnector {

  public int insert(String query, Consumer<PreparedStatement> consumer);
  public ResultSet select(String query, Consumer<PreparedStatement> consumer);
  public int update(String query, Consumer<PreparedStatement> consumer);
  public int delete(String query, Consumer<PreparedStatement> consumer);

}
