package project.shoppingmall.observer;

import project.shoppingmall.Client;
import project.shoppingmall.UserInterface;
import project.shoppingmall.dto.Product;

import java.util.List;

public class BasketView implements observer_pattern.Observer {

  private Client client;
  private UserInterface ui;

  public BasketView(Client client, UserInterface ui) {
    this.client = client;
    this.ui = ui;
  }

  @Override
  public void update() {
    List<Product> productList = (List<Product>) client.getMap().get("productList");
    ui.printBasketList(productList);
  }



}
