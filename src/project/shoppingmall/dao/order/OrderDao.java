package project.shoppingmall.dao.order;

import project.shoppingmall.dto.Member;
import project.shoppingmall.dto.Product;

import java.util.List;

public interface OrderDao {

  int insert(Status status, Member member, Product product);
  List<Product> selectList(Member member);
  int update(Member member, Product product, Status status);

}
