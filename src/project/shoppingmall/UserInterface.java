package project.shoppingmall;

import project.shoppingmall.dto.Member;
import project.shoppingmall.dto.Product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Consumer;

public class UserInterface {

  private Scanner scanner = new Scanner(System.in);

  private void borderPrint(final Consumer<Scanner> consumer) {
    System.out.println("=============================");
    consumer.accept(scanner);
    System.out.println("=============================");
  }

  private void border() {
    System.out.println("=============================");
  }

  public void logIn(final Map<String, Object> map) {
    borderPrint(sc -> {
      System.out.print("아이디 >> ");
      String username = scanner.nextLine();
      System.out.print("비밀번호 >> ");
      String password = scanner.nextLine();
      map.put("username", username);
      map.put("password", password);
    });
  }

  public void join(final Map<String, Object> map) {
    borderPrint(sc -> {
      System.out.print("이름 >> ");
      String name = scanner.nextLine();
      System.out.print("아이디 >> ");
      String username = scanner.nextLine();
      System.out.print("비밀번호 >> ");
      String password = scanner.nextLine();
      System.out.print("전화번호 >> ");
      String phoneNumber = scanner.nextLine();
      System.out.print("주소 >> ");
      String address = scanner.nextLine();
      map.put("name", name);
      map.put("username", username);
      map.put("password", password);
      map.put("phoneNumber", phoneNumber);
      map.put("address", address);
    });
  }

  public void myInfo(final Map<String, Object> map) {
    Member accessMember = (Member) map.get("accessMember");
    borderPrint(sc -> {
      System.out.println("이름 >> " + accessMember.getName());
      System.out.println("아이디 >> " + accessMember.getUsername());
      System.out.println("전화번호 >> " + accessMember.getPhoneNumber());
      System.out.println("주소 >> " + accessMember.getAddress());
    });
  }


  public String printIntroMenu() {
    borderPrint(sc -> {
      System.out.println("메뉴를 선택하세요.");
      System.out.println("1. 로그인");
      System.out.println("2. 회원가입");
      System.out.println("3. 종료");
    });
    System.out.print(">>>> ");
    return scanner.nextLine();
  }

  public String printMemberMenu() {
    borderPrint(sc -> {
      System.out.println("메뉴를 선택하세요.");
      System.out.println("1. 내정보 보기");
      System.out.println("2. 로그아웃");
      System.out.println("3. 쇼핑");
      System.out.println("4. 장바구니");
      System.out.println("5. 주문 확인");
    });
    System.out.print(">>>> ");
    return scanner.nextLine();
  }

  public String printShoppingMenu(final Map<String, Object> map) {
    List<Product> products = (List<Product>) map.get("products");
    borderPrint(sc -> {
      System.out.println("상품 리스트");
      border();
      printProductList(products);
      border();
      System.out.println("메뉴를 입력해주세요.");
      System.out.println("1. 상품 담기");
      System.out.println("2. 이전으로");
      border();
    });
    System.out.print(">>>> ");
    return scanner.nextLine();
  }

  public String addProduct() {
    borderPrint(sc -> {
      System.out.println("상품 번호를 입력해주세요.");
    });
    System.out.print(">>>> ");
    return scanner.nextLine();
  }

  public String printBasketMenu(Map<String, Object> map) {
    List<Product> products = (List<Product>) map.get("productList");
    printBasketList(products);
    borderPrint(sc -> {
      System.out.println("메뉴를 입력해주세요.");
      System.out.println("1. 전체 구매하기");
      System.out.println("2. 상품 취소하기");
      System.out.println("3. 이전으로");
    });
    System.out.print(">>>> ");
    return scanner.nextLine();
  }

  public void printBasketList(final List<Product> products) {
    borderPrint(sc -> {
      System.out.println("장바구니 리스트");
      border();
      printProductList(products);
    });
  }

  public void printProductList(final List<Product> products) {
    products.forEach(product -> {
      System.out.println("상품 번호: " + product.getId());
      System.out.println("상품 이름: " + product.getName());
      System.out.println("상품 가격: " + product.getPrice());
      System.out.println("상품 재고: " + product.getStock());
      System.out.println("-----------------------------");
    });
  }

  public String printPaymentMenu() {
    borderPrint(sc -> {
      System.out.println("결제 API 연결 완료");
      border();
      System.out.println("결제하실 방법을 선택해주세요.");
      System.out.println("1. Samsung Pay");
      System.out.println("2. 휴대폰 결제");
      System.out.println("3. 이전으로");
    });
    System.out.print(">>>> ");
    return scanner.nextLine();
  }

  public String printCancelProduct() {
    borderPrint(sc -> {
      System.out.println("취소할 상품 번호를 입력해주세요.");
    });
    System.out.print(">>>> ");
    return scanner.nextLine();
  }

}
