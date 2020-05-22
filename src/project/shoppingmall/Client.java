package project.shoppingmall;

import observer_pattern.Subject;
import project.shoppingmall.dao.basket.PostgresSqlBasketDao;
import project.shoppingmall.dao.member.PostgresSqlMemberDao;
import project.shoppingmall.dao.order.PostgresSqlOrderDao;
import project.shoppingmall.dao.product.PostgresSqlProductDao;
import project.shoppingmall.db.PostgresSqlDBConnector;
import project.shoppingmall.dto.Member;
import project.shoppingmall.observer.BasketView;
import project.shoppingmall.payment.*;
import project.shoppingmall.service.BasketService;
import project.shoppingmall.service.MemberService;
import project.shoppingmall.service.OrderService;
import project.shoppingmall.service.ProductService;

import java.util.HashMap;
import java.util.Map;

public class Client extends Subject {

  private Map<String, Object> map = new HashMap<>();
  private UserInterface ui = new UserInterface();
  private MemberService memberService = new MemberService(new PostgresSqlMemberDao(new PostgresSqlDBConnector()));
  private BasketService basketService = new BasketService(new PostgresSqlBasketDao(new PostgresSqlDBConnector()));
  private OrderService orderService = new OrderService(new PostgresSqlOrderDao(new PostgresSqlDBConnector()));
  private ProductService productService = new ProductService(new PostgresSqlProductDao(new PostgresSqlDBConnector()));


  private Member logIn() {
    ui.logIn(map);

    Member member = new Member();
    member.setUsername((String) map.get("username"));
    member.setPassword((String) map.get("password"));

    memberService.signIn(map);
    return (Member) map.get("accessMember");
  }

  private void logOut() {
    map.clear();
  }

  private void join() {
    ui.join(map);
    memberService.signUp(map);
  }

  private void myInfo() {
    ui.myInfo(map);
  }

  private void addProductToBasket() {
    map.put("productId", Integer.parseInt(ui.addProduct()));
    basketService.addProductToBasket(map);
    basketService.getProductListInBasket(map);
    notifyObservers();
  }

  private void payAllProduct(Payment payment) {
    PaymentService paymentService;
    switch (payment) {
      case SAMSUNG:
        paymentService = new PaymentService(new SamsungCommand(SamsungPay.getInstance()));
        break;
      case CELLPHONE:
        paymentService = new PaymentService(new CellphoneCommand(CellPhonePay.getInstance()));
        break;
      default:
        throw new IllegalStateException("Unexpected value: " + payment);
    }
    paymentService.service();
    productService.setProductStock(map);
    basketService.deleteAllProductInBasket(map);
  }

  private void cancelProduct() {
    map.put("productId", Integer.parseInt(ui.printCancelProduct()));
    basketService.deleteProductInBasket(map);
  }

  private void viewOrder() {

  }

  private void shoppingMenu() {
    productService.getProductList(map);
    while (true) {
      String shoppingMenu = ui.printShoppingMenu(map);
      switch (shoppingMenu) {
        case "1":
          addProductToBasket();
          break;
        case "2":
          return;
        default:
          System.out.println("잘못된 입력입니다.");
      }
    }
  }

  private void paymentMenu() {
    String menu = ui.printPaymentMenu();
    while (true) {
      switch (menu) {
        case "1":
          payAllProduct(Payment.SAMSUNG);
          return;
        case "2":
          payAllProduct(Payment.CELLPHONE);
          return;
        case "3":
          return;
        default:
          System.out.println("잘못된 입력입니다.");
      }
    }
  }

  private void basketMenu() {
    while (true) {
      basketService.getProductListInBasket(map);
      String menu = ui.printBasketMenu(map);
      switch (menu) {
        case "1":
          paymentMenu();
          break;
        case "2":
          cancelProduct();
          break;
        case "3":
          return;
        default:
          System.out.println("잘못된 입력입니다.");
      }
    }
  }

  private void memberMenu() {
    while (true) {
      String memberChoice = ui.printMemberMenu();
      switch (memberChoice) {
        case "1":
          myInfo();
          break;
        case "2":
          logOut();
          return;
        case "3":
          shoppingMenu();
          break;
        case "4":
          basketMenu();
          break;
        case "5":
          // TODO 주문확인
          break;
        default:
          System.out.println("잘못된 입력입니다.");
          break;
      }
    }
  }

  public void run() {
    attach(new BasketView(this, new UserInterface()));
    while (true) {
      String choice = ui.printIntroMenu();
      switch (choice) {
        case "1":
          Member accessMember = logIn();
          if (accessMember != null) {
            memberMenu();
          } else {
            System.out.println("로그인 실패");
          }
          break;
        case "2":
          join();
          break;
        case "3":
          return;
        default:
          System.out.println("잘못된 입력입니다.");
          break;
      }
    }
  }

  public Map<String, Object> getMap() {
    return map;
  }

  public static void main(String[] args) {
    Client client = new Client();
    client.run();
  }

}
