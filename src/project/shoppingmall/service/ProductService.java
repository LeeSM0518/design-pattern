package project.shoppingmall.service;

import project.shoppingmall.dao.product.ProductDao;
import project.shoppingmall.dto.Product;

import java.util.List;
import java.util.Map;

public class ProductService {

  private ProductDao productDao;

  public ProductService(ProductDao productDao) {
    this.productDao = productDao;
  }

  public void setProductStock(Map<String, Object> map) {
    List<Product> productList = (List<Product>) map.get("productList");
    productDao.update(productList);
  }

  public void getProductList(Map<String, Object> map) {
    map.put("products", productDao.selectList());
  }

}