package project.shoppingmall.dao.product;

import project.shoppingmall.db.DBConnector;
import project.shoppingmall.dto.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PostgresSqlProductDao implements ProductDao {

  DBConnector connector;

  public PostgresSqlProductDao(DBConnector dbConnector) {
    connector = dbConnector;
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

  private void updateToReduceStock(Product product) {
    connector.update("update PRODUCT set stock = stock - 1 where product_id = "
            + product.getId(),
        preparedStatement -> {
          try {
            preparedStatement.addBatch();
            preparedStatement.clearParameters();
          } catch (SQLException e) {
            e.printStackTrace();
          }
        });
  }

  @Override
  public void update(List<Product> products) {
    for (Product product : products) {
      updateToReduceStock(product);
    }
  }

}
