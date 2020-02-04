package project.shopping_mall;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PostgresSqlMemberDao implements MemberDao {

  DBConnector conn = PostgresSqlDBConnector.getInstance();
  private static PostgresSqlMemberDao memberDao = new PostgresSqlMemberDao();

  private PostgresSqlMemberDao(){}
  public static PostgresSqlMemberDao getInstance() {
    return memberDao;
  }

  @Override
  public Member selectOne(Member member) {
    String username = member.getUsername();
    String password = member.getPassword();
    conn.select("select * from MEMBER where username=? and password=?",
        preparedStatement -> {
          try {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
              member.setName(rs.getString("name"));
              member.setPhoneNumber(rs.getString("phone_number"));
              member.setAddress(rs.getString("address"));
            }
          } catch (SQLException e) {
            e.printStackTrace();
          }
        });
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