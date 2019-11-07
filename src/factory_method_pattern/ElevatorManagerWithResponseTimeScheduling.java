package factory_method_pattern;

public class ElevatorManagerWithResponseTimeScheduling extends ElevatorManager {

  public ElevatorManagerWithResponseTimeScheduling(int controllerCount) {
    super(controllerCount);
  }

  // 처리량 최대화 전략을 사용함
  @Override
  protected ElevatorScheduler getScheduler() {
    ElevatorScheduler scheduler = ResponseTimeScheduler.getInstance();
    return scheduler;
  }
}
