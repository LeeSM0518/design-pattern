package state_pattern;

public interface State {

  public void onButtonPushed(Light light);
  public void offButtonPushed(Light light);

}
