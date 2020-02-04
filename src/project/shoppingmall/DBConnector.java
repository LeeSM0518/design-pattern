package project.shopping_mall;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.function.Consumer;

public interface DBConnector {

  int insert(String query, Consumer<PreparedStatement> consumer);
  ResultSet select(String query, Consumer<PreparedStatement> consumer);
  int update(String query, Consumer<PreparedStatement> consumer);
  int delete(String query, Consumer<PreparedStatement> consumer);

}
