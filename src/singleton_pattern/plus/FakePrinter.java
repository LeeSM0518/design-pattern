package singleton_pattern.plus;

public class FakePrinter implements Printer{

  @Override
  public void print(String str) {
    System.out.println("Test print.");
  }

}
