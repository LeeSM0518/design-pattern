package project.shoppingmall.payment;

public class PaymentService {

  private Command command;

  public PaymentService(final Command command) {
    setCommand(command);
  }

  public void setCommand(final Command command) {
    this.command = command;
  }

  public void service() {
    command.execute();
  }

}
