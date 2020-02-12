package project.shoppingmall.dao.product;

import project.shoppingmall.dto.Product;

import java.util.List;

public interface ProductDao {

  List<Product> selectList();
  int updateToReduceStock(List<Product> products);

}
