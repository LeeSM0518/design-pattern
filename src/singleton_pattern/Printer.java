package singleton_pattern;

public class Printer {

  private static int counter = 0;

  public synchronized static void print(String str) {
      counter++;
      System.out.println(str + counter);
  }

}
