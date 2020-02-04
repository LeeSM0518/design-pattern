package composite_pattern.used_pattern;

public class Client {

  public static void main(String[] args) {
    Body body = new Body(100, 70);
    Keyboard keyboard = new Keyboard(5, 2);
    Monitor monitor = new Monitor(20, 30);

    Computer computer = new Computer();
    computer.addComponent(body);
    computer.addComponent(monitor);
    computer.addComponent(keyboard);

    int computerPrice = computer.getPrice();
    int computerPower = computer.getPower();
    System.out.println("Computer Power: " + computerPower);
    System.out.println("Computer Price: " + computerPrice);
  }

}
