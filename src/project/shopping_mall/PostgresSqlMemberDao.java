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
  public Member selectOne(Member member) {
    String username = member.getUsername();
    String password = member.getPassword();
    ResultSet resultSet = conn.select("select * from MEMBER where username=? and password=?",
            preparedStatement -> {
              try {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
              } catch (SQLException e) {
                e.printStackTrace();
              }
            });
    try {
      if (resultSet.next()) {
        member.setName(resultSet.getString("name"));
        member.setPhoneNumber(resultSet.getString("phone_number"));
        member.setAddress(resultSet.getString("address"));
      } else {
        return null;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return member;
  }

  @Override
  public void insert(Member member) {
    conn.insert("insert into MEMBER values" +
            "(DEFAULT, ?, ?, ?, ?, ?)", preparedStatement -> {
      try {
        preparedStatement.setString(1, member.getName());
        preparedStatement.setString(2, member.getUsername());
        preparedStatement.setString(3, member.getPassword());
        preparedStatement.setString(4, member.getPhoneNumber());
        preparedStatement.setString(5, member.getAddress());
        preparedStatement.addBatch();
        preparedStatement.clearParameters();
      } catch (Exception e) {
        e.printStackTrace();
      }
    });
  }

}