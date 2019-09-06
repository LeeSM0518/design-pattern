package state_pattern;

public class OFF implements State {

  private static OFF off = new OFF();   // OFF 클래스의 인스턴스로 초기화됨
  private OFF() {}

  public static OFF getInstance() {     // 초기화된 OFF 클래스의 인스턴스를 반환함
    return off;
  }

  @Override
  public void onButtonPushed(Light light) {
    System.out.println("Light On!");
    light.setState(ON.getInstance());
  }

  @Override
  public void offButtonPushed(Light light) {
    System.out.println("반응 없음");
  }

}