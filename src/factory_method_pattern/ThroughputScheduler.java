package factory_method_pattern;

import template_method_pattern.unused_pattern.Direction;

public class ThroughputScheduler implements ElevatorScheduler {

  public int selectElevator(ElevatorManager manager,
                            int destination, Direction direction) {
    // 임의로 선택함
    return 0;
  }

}
