package project.shoppingmall.dao.basket;

import project.shoppingmall.dto.Basket;
import project.shoppingmall.dto.Member;
import project.shoppingmall.dto.Product;

import java.util.List;

public interface BasketDao {

  int insert(Basket basket);
  List<Product> selectForProductInBasket(Basket basket);
  int deleteOne(Basket basket);
  int deleteAll(Basket basket);

}
