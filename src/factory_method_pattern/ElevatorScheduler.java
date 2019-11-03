package factory_method_pattern;

import template_method_pattern.unused_pattern.Direction;

public interface ElevatorScheduler {

  int selectElevator(ElevatorManager manager,
                     int destination, Direction direction);

}
