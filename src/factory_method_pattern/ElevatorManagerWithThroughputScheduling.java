package factory_method_pattern;

public class ElevatorManagerWithThroughputScheduling extends ElevatorManager {

  public ElevatorManagerWithThroughputScheduling(int controllerCount) {
    super(controllerCount);
  }

  // 대기 시간 최소화 전략을 사용함
  @Override
  protected ElevatorScheduler getScheduler() {
    ElevatorScheduler scheduler = ThroughputScheduler.getInstance();
    return scheduler;
  }

}
