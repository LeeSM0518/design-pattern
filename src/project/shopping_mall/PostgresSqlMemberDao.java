package project.shopping_mall;

import project.shopping_mall.db.DBConnector;
import project.shopping_mall.db.PostgresSqlDBConnector;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PostgresSqlMemberDao implements MemberDao {

  DBConnector conn = PostgresSqlDBConnector.getInstance();
  private static PostgresSqlMemberDao memberDao = new PostgresSqlMemberDao();

  public static PostgresSqlMemberDao getInstance() {
    return memberDao;
  }

  @Override
  public boolean exist(Member member) throws SQLException {
    String username = member.getUsername();
    String password = member.getPassword();
    ResultSet resultSet = conn.select("select * from MEMBER where username = " + username +
        " and password = " + password);
    if (resultSet == null) throw new SQLException();
    return resultSet.next();
  }

  @Override
  public void insert(Member member) {
    conn.insert("")
  }

  @Override
  public void update(Member member) {

  }

  @Override
  public void delete(Member member) {

  }

}