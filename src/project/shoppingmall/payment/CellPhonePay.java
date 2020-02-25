package project.shoppingmall.payment;

public class CellPhonePay {

  private CellPhonePay(){}
  private static CellPhonePay cellPhonePay = new CellPhonePay();

  public static CellPhonePay getInstance() {
    return cellPhonePay;
  }

  public void pay() {
    System.out.println("휴대폰 결제중...");
    System.out.println("결제 완료.");
  }

}
