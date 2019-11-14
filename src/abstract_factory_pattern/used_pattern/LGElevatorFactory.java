package abstract_factory_pattern.used_pattern;

import abstract_factory_pattern.unused_pattern.Door;
import abstract_factory_pattern.unused_pattern.LGDoor;
import abstract_factory_pattern.unused_pattern.LGMotor;
import abstract_factory_pattern.unused_pattern.Motor;

public class LGElevatorFactory extends ElevatorFactory {

  @Override
  public Motor createMotor() {
    return new LGMotor();
  }

  @Override
  public Door createDoor() {
    return new LGDoor();
  }

}
