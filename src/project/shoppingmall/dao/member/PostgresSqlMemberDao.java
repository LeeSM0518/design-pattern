package project.shoppingmall.dao.member;

import project.shoppingmall.dto.Member;
import project.shoppingmall.db.DBConnector;
import project.shoppingmall.db.PostgresSqlDBConnector;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PostgresSqlMemberDao implements MemberDao {

  DBConnector conn;

  public PostgresSqlMemberDao(DBConnector dbConnector){
    conn = dbConnector;
  }

  @Override
  public Member selectOne(Member member) {
    conn.select("select * from MEMBER where username=? and password=?",
        preparedStatement -> {
          try {
            preparedStatement.setString(1, member.getUsername());
            preparedStatement.setString(2, member.getPassword());
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
              member.setId(rs.getInt("member_id"));
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
  public int insert(Member member) {
    return conn.insert("insert into MEMBER values" +
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