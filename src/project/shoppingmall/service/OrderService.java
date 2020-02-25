package project.shoppingmall.service;

import project.shoppingmall.dao.order.OrderDao;

public class OrderService {

  private OrderDao orderDao;

  public OrderService(OrderDao orderDao) {
    this.orderDao = orderDao;
  }



}
