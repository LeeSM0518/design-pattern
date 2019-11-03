package factory_method_pattern;

import template_method_pattern.unused_pattern.Direction;

public class ResponseTimeScheduler implements ElevatorScheduler {
  @Override
  public int selectElevator(ElevatorManager manager, int destination,
                            Direction direction) {
    // 임의로 선택
    return 0;
  }
}
