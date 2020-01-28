package project.shopping_mall.db;

import java.sql.ResultSet;
import java.util.List;

public interface DBConnector {

  public int insert(String query);
  public ResultSet select(String query);
  public int update(String query);
  public int delete(String query);

}
