package composite_pattern.used_pattern;

public class Body extends ComputerDevice {

  public Body(int price, int power) {
    this.price = price;
    this.power = power;
  }

  private int price;
  private int power;

  @Override
  public int getPrice() {
    return price;
  }

  @Override
  public int getPower() {
    return power;
  }

}
