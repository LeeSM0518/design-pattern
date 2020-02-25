package project.shoppingmall.payment;

public class CellphoneCommand implements Command {

  private CellPhonePay cellPhonePay;

  public CellphoneCommand(CellPhonePay cellPhonePay) {
    this.cellPhonePay = cellPhonePay;
  }

  @Override
  public void execute() {
    cellPhonePay.pay();
  }

}
