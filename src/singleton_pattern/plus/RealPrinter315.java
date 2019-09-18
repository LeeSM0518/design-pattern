package singleton_pattern.plus;

public class RealPrinter315 implements Printer{

  private static Printer printer = null;

  private RealPrinter315() {}

  public synchronized static Printer getPrinter() {
    if (printer == null) {
      printer = new RealPrinter315();
    }
    return printer;
  }

  @Override
  public void print(String str) {
    System.out.println(str);
  }

}
