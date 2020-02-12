package project.shoppingmall.dao.basket;

import project.shoppingmall.db.DBConnector;
import project.shoppingmall.db.PostgresSqlDBConnector;
import project.shoppingmall.dto.Basket;
import project.shoppingmall.dto.Member;
import project.shoppingmall.dto.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PostgresSqlBasketDao implements BasketDao {

  DBConnector connector = PostgresSqlDBConnector.getInstance();
  private static PostgresSqlBasketDao basketDao = new PostgresSqlBasketDao();

  private PostgresSqlBasketDao() {
  }

  public static PostgresSqlBasketDao getInstance() {
    return basketDao;
  }

  @Override
  public int insert(Basket basket) {
    return connector.insert("insert into BASKET values " +
        "(?, ?)", preparedStatement -> {
      try {
        preparedStatement.setInt(1, basket.getMember().getId());
        preparedStatement.setInt(2, basket.getProduct().getId());
        preparedStatement.addBatch();
        preparedStatement.clearParameters();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    });
  }

  @Override
  public List<Product> selectForProductInBasket(Basket basket) {
    List<Product> products = new ArrayList<>();
    connector.select("select p.product_id, p.name, p.price, p.stock " +
        "from MEMBER m, PRODUCT p, BASKET b " +
        "where m.member_id = b.member_id and " +
        "p.product_id = b.product_id and " +
        "m.member_id = ?", preparedStatement -> {
      try {
        preparedStatement.setInt(1, basket.getMember().getId());
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
          Product product = new Product();
          product.setId(resultSet.getInt(1));
          product.setName(resultSet.getString(2));
          product.setPrice(resultSet.getInt(3));
          product.setStock(resultSet.getInt(4));
          products.add(product);
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    });
    return products;
  }

  @Override
  public int deleteOne(Basket basket) {
    return connector.delete("delete from BASKET where member_id = ? and " +
        "product_id = ?", preparedStatement -> {
      try {
        preparedStatement.setInt(1, basket.getMember().getId());
        preparedStatement.setInt(2, basket.getProduct().getId());
        preparedStatement.addBatch();
        preparedStatement.clearParameters();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    });
  }

  @Override
  public int deleteAll(Basket basket) {
    return 0;
  }

}