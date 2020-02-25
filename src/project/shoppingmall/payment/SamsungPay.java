package project.shoppingmall.payment;

public class SamsungPay {

  private SamsungPay(){}
  private static SamsungPay samsungPay = new SamsungPay();

  public static SamsungPay getInstance() {
    return samsungPay;
  }

  public void pay() {
    System.out.println("Samsung Pay 결제중...");
    System.out.println("결제 완료.");
  }

}
