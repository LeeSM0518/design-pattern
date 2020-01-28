package project.shopping_mall;

import java.sql.SQLException;
import java.util.List;

public interface MemberDao {

  public Member selectOne(Member member);
  public void insert(Member member);

}
