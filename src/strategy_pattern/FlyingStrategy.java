package strategy_pattern;

public class FlyingStrategy implements MovingStrategy {

  @Override
  public void movie() {
    System.out.println("I can fly");
  }

}
