package state_pattern;

public class Client {

  public static void main(String[] args) {
    Light light = new Light();
    light.onButtonPushed();
    light.offButtonPushed();
  }

}
