package singleton_pattern.plus;

public class UsePrinter {

  public void doSomething(Printer printer) {
    String str = "Real print.";
    printer.print(str);
  }

  public static void main(String[] args) {
    UsePrinter user = new UsePrinter();
    System.out.println("실제 프린터 사용");
    user.doSomething(RealPrinter315.getPrinter());

    System.out.println("\n테스트용 프린터 사용");
    FakePrinter fakePrinter = new FakePrinter();
    user.doSomething(fakePrinter);
  }

}
