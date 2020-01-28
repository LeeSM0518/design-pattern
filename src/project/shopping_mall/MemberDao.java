package project.shopping_mall;

import java.sql.SQLException;
import java.util.List;

public interface MemberDao {

  public boolean exist(Member member) throws SQLException;
  public void insert(Member member);
  public void update(Member member);
  public void delete(Member member);

}
