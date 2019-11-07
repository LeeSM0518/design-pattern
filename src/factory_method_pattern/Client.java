package factory_method_pattern;

import template_method_pattern.unused_pattern.Direction;

public class Client {

  public static void main(String[] args) {
    ElevatorManager emWithResponseTimeScheduler =
        new ElevatorManager(2, SchedulingStrategyID.RESPONSE_TIME);
    emWithResponseTimeScheduler.requestElevator(10 , Direction.UP);

    ElevatorManager emWithThroughoutScheduler =
        new ElevatorManager(2, SchedulingStrategyID.THROUGHPUT);
    emWithThroughoutScheduler.requestElevator(10, Direction.UP);

    ElevatorManager emWithDynamicScheduler =
        new ElevatorManager(2, SchedulingStrategyID.DYNAMIC);
    emWithDynamicScheduler.requestElevator(10, Direction.UP);
  }

}