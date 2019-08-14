package strategy_pattern;

public class WalkingStrategy implements MovingStrategy {

  @Override
  public void movie() {
    System.out.println("I can only walk");
  }

}
