package object_oriented_principle;

public class ArrayStack {

  public int top;
  public int[] itemArray;
  public int stackSize;

  public ArrayStack(int stackSize) {
    itemArray = new int[stackSize];
    top = -1;
    this.stackSize = stackSize;
  }

  public boolean isEmpty() {
    return (top == -1);
  }

  public boolean isFull() {
    return (top == this.stackSize - 1);
  }

  public void push(int item) {
    if (isFull()) {
      System.out.println("Inserting fail! Array Stack is full!");
    } else {
      itemArray[++top] = item;
      System.out.println("Inserted Item : " + item);
    }
  }

  public int pop() {
    if (isEmpty()) {
      System.out.println("Deleting fail! Array Stack is empty!");
      return -1;
    } else {
      return itemArray[top--];
    }
  }

  public int peek() {
    if (isEmpty()) {
      System.out.println("Peeking fail! Array Stack is empty");
      return -1;
    } else {
      return itemArray[top];
    }
  }

}