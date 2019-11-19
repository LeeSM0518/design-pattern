package abstract_factory_pattern.used_pattern;

import abstract_factory_pattern.unused_pattern.Door;
import abstract_factory_pattern.unused_pattern.Motor;
import abstract_factory_pattern.unused_pattern.VendorID;
import template_method_pattern.unused_pattern.Direction;

public class Client {

  public static void main(String[] args) {
    String vendorName = args[0];
    VendorID vendorID;

    if (vendorName.equalsIgnoreCase("Samsung"))
      vendorID = VendorID.SAMSUNG;
    else if (vendorName.equalsIgnoreCase("LG"))
      vendorID = VendorID.LG;
    else
      vendorID = VendorID.HYUNDAI;

    ElevatorFactory factory = ElevatorFactoryFactory.getFactory(vendorID);

    Door door = factory.createDoor();
    Motor motor = factory.createMotor();
    motor.setDoor(door);

    door.open();
    motor.move(Direction.UP);
  }

}