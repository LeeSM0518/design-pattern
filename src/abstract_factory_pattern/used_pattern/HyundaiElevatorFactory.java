package abstract_factory_pattern.used_pattern;

import abstract_factory_pattern.unused_pattern.Door;
import abstract_factory_pattern.unused_pattern.HyundaiDoor;
import abstract_factory_pattern.unused_pattern.HyundaiMotor;
import abstract_factory_pattern.unused_pattern.Motor;

public class HyundaiElevatorFactory extends ElevatorFactory {

  @Override
  public Motor createMotor() {
    return new HyundaiMotor();
  }

  @Override
  public Door createDoor() {
    return new HyundaiDoor();
  }

}
