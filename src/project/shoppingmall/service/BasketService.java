package project.shoppingmall.service;

import project.shoppingmall.dao.basket.BasketDao;
import project.shoppingmall.dto.Basket;
import project.shoppingmall.dto.Member;
import project.shoppingmall.dto.Product;

import java.util.List;
import java.util.Map;

public class BasketService {

  private BasketDao basketDao;

  public BasketService(BasketDao basketDao) {
    this.basketDao = basketDao;
  }

  public void addProductToBasket(Map<String, Object> map) {
    Basket basket = new Basket();
    Member member = (Member) map.get("accessMember");
    Product product = new Product();
    product.setId((Integer) map.get("productId"));
    basket.setMember(member);
    basket.setProduct(product);
    basketDao.insert(basket);
  }

  public void getProductListInBasket(Map<String, Object> map) {
    Basket basket = new Basket();
    Member member = (Member) map.get("accessMember");
    basket.setMember(member);
    List<Product> productList = basketDao.selectForProductInBasket(basket);
    map.put("productList", productList);
  }

  public void deleteAllProductInBasket(Map<String, Object> map) {
    Basket basket = new Basket();
    Member member = (Member) map.get("accessMember");
    basket.setMember(member);
    basketDao.deleteAll(basket);
  }

  public void deleteProductInBasket(Map<String, Object> map) {
    Basket basket = new Basket();
    Member member = (Member) map.get("accessMember");
    Product product = new Product();
    product.setId((Integer) map.get("productId"));
    basket.setMember(member);
    basket.setProduct(product);
    basketDao.deleteOne(basket);
  }

}
