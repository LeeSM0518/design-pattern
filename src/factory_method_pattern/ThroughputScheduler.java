package factory_method_pattern;

import template_method_pattern.unused_pattern.Direction;

public class ThroughputScheduler implements ElevatorScheduler {

  private static ElevatorScheduler scheduler;
  private ThroughputScheduler(){}

  public static ElevatorScheduler getInstance() {
    if (scheduler == null)
      scheduler = new ThroughputScheduler();

    return scheduler;
  }

  public int selectElevator(ElevatorManager manager,
                            int destination, Direction direction) {
    // 임의로 선택함
    return 0;
  }

}

