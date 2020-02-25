package project.shoppingmall.payment;

public class SamsungCommand implements Command{

  private SamsungPay samsungPay;

  public SamsungCommand(SamsungPay samsungPay) {
    this.samsungPay = samsungPay;
  }

  @Override
  public void execute() {
    samsungPay.pay();
  }

}
