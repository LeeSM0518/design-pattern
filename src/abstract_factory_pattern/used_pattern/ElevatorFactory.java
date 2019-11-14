package abstract_factory_pattern.used_pattern;

import abstract_factory_pattern.unused_pattern.Door;
import abstract_factory_pattern.unused_pattern.Motor;

public abstract class ElevatorFactory {

  public abstract Motor createMotor();
  public abstract Door createDoor();

}
