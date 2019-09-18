package state_pattern;

public class Light {

  private State state;                  // 형광등의 현재 상태

  public Light() {
//    state = new OFF();
  }

  public void setState(State state) {
    this.state = state;
  }

  public void onButtonPushed() {
    state.onButtonPushed(this);
  }

  public void offButtonPushed() {
    state.offButtonPushed(this);
  }

}
