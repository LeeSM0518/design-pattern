package project.shoppingmall.dao.product;

import project.shoppingmall.db.DBConnector;
import project.shoppingmall.db.PostgresSqlDBConnector;
import project.shoppingmall.dto.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PostgresSqlProductDao implements ProductDao {

  DBConnector connector = PostgresSqlDBConnector.getInstance();
  private static PostgresSqlProductDao productDao = new PostgresSqlProductDao();

  private PostgresSqlProductDao() {
  }

  public static PostgresSqlProductDao getInstance() {
    return productDao;
  }

  @Override
  public List<Product> selectList() {
    List<Product> products = new ArrayList<>();
    connector.select("select * from PRODUCT",
        preparedStatement -> {
          try {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
              Product product = new Product();
              product.setId(rs.getInt("product_id"));
              product.setName(rs.getString("name"));
              product.setPrice(rs.getInt("price"));
              product.setStock(rs.getInt("stock"));
              products.add(product);
            }
          } catch (SQLException e) {
            e.printStackTrace();
          }
        });
    return products;
  }

  @Override
  public int updateToReduceStock(List<Product> products) {
    return connector.update("update PRODUCT set stock = stock - 1 where product_id = ?",
        preparedStatement -> {
          for (Product product : products) {
            try {
              preparedStatement.setInt(1, product.getId());
              preparedStatement.addBatch();
              preparedStatement.clearParameters();
            } catch (SQLException e) {
              e.printStackTrace();
            }
          }
        });
  }

}
