package state_pattern;

public class ON implements State {

  private static ON on = new ON();    // ON 클래스의 인스턴스로 초기화됨
  private ON() {}

  public static ON getInstance() {    // 초기화된 ON 클래스의 인스턴스로 반환
    return on;
  }

  @Override
  public void onButtonPushed(Light light) {
    System.out.println("반응 없음");
  }

  @Override
  public void offButtonPushed(Light light) {
    System.out.println("Light Off!");
    light.setState(OFF.getInstance());
  }

}
