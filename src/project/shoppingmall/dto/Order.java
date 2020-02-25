package project.shoppingmall.dto;

import project.shoppingmall.dao.order.Status;

public class Order {

  private int id;
  private Status status;
  private Member member;
  private Product product;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public Member getMember() {
    return member;
  }

  public void setMember(Member member) {
    this.member = member;
  }

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }
}
