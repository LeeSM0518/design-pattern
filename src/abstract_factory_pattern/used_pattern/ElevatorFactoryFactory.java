package abstract_factory_pattern.used_pattern;

import abstract_factory_pattern.unused_pattern.VendorID;

public class ElevatorFactoryFactory {

  public static ElevatorFactory getFactory(VendorID vendorID) {
    ElevatorFactory factory = null;
    switch (vendorID) {
      case LG:
        factory = LGElevatorFactory.getInstance();
        break;
      case HYUNDAI:
        factory = HyundaiElevatorFactory.getInstance();
        break;
      case SAMSUNG:
        factory = SamsungElevatorFactory.getInstance();
        break;
    }
    return factory;
  }

}