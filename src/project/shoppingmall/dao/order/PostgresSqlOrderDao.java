package project.shoppingmall.dao.order;

import project.shoppingmall.db.DBConnector;
import project.shoppingmall.dto.Member;
import project.shoppingmall.dto.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PostgresSqlOrderDao implements OrderDao {

  DBConnector connector;

  public PostgresSqlOrderDao(DBConnector dbConnector) {
    connector = dbConnector;
  }

  @Override
  public int insert(Status status, Member member, Product product) {
    return connector.insert("insert into ORDER_PRODUCT values " +
        "(DEFAULT, ?, ?, ?)", preparedStatement -> {
      try {
        preparedStatement.setString(1, status.getMessage());
        preparedStatement.setInt(2, member.getId());
        preparedStatement.setInt(3, product.getId());
        preparedStatement.addBatch();
        preparedStatement.clearParameters();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    });
  }

  @Override
  public List<Product> selectList(Member member) {
    List<Product> products = new ArrayList<>();
    connector.select("select o.order_id, o.delivery_status, p.product_id, p.name, p.price " +
        "from ORDER_PRODUCT o, MEMBER m, PRODUCT p " +
        "where m.member_id = o.member_id and " +
        "p.product_id = o.product_id and " +
        "m.member_id = ?", preparedStatement -> {
      try {
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
          Product product = new Product();

        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    });
    return products;
  }

  @Override
  public int update(Member member, Product product, Status status) {
    return 0;
  }

}