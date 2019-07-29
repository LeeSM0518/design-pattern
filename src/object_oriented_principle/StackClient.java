package object_oriented_principle;

public class StackClient {
  public static void main(String[] args) {
    ArrayStack arrayStack = new ArrayStack(10);
    arrayStack.itemArray[++arrayStack.top] = 20;
    System.out.println(arrayStack.itemArray[arrayStack.top]);
  }
}
