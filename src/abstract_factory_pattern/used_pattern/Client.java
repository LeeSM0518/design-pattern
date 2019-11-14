package abstract_factory_pattern.used_pattern;

import abstract_factory_pattern.unused_pattern.Door;
import abstract_factory_pattern.unused_pattern.Motor;
import template_method_pattern.unused_pattern.Direction;

public class Client {

  public static void main(String[] args) {
    ElevatorFactory factory = new HyundaiElevatorFactory();
    Door door = factory.createDoor();
    Motor motor = factory.createMotor();
    motor.setDoor(door);

    door.open();
    motor.move(Direction.UP);
  }

}
