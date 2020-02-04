package project.shopping_mall;

import java.sql.SQLException;
import java.util.List;

public interface MemberDao {

  Member selectOne(Member member);
  void insert(Member member);

}
