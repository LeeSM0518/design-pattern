package abstract_factory_pattern.used_pattern;

import abstract_factory_pattern.unused_pattern.Door;
import abstract_factory_pattern.unused_pattern.Motor;

public class SamsungElevatorFactory extends ElevatorFactory {

  private static ElevatorFactory factory;
  private SamsungElevatorFactory() {}

  public static ElevatorFactory getInstance() {
    if (factory == null)
      factory = new SamsungElevatorFactory();

    return factory;
  }

  @Override
  public Motor createMotor() {
    return new SamsungMotor();
  }

  @Override
  public Door createDoor() {
    return new SamsungDoor();
  }

}
