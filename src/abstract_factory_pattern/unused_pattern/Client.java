package abstract_factory_pattern.unused_pattern;

import template_method_pattern.unused_pattern.Direction;

public class Client {

  public static void main(String[] args) {
    Door lgDoor = DoorFactory.createDoor(VendorID.LG);
    Motor lgMotor = MotorFactory.createMotor(VendorID.LG);
    lgMotor.setDoor(lgDoor);

    lgDoor.open();
    lgMotor.move(Direction.UP);
  }

}
