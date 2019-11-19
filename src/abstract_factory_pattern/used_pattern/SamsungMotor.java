package abstract_factory_pattern.used_pattern;

import abstract_factory_pattern.unused_pattern.Motor;
import template_method_pattern.unused_pattern.Direction;

public class SamsungMotor extends Motor {

  @Override
  protected void moveMotor(Direction direction) {
    System.out.println("move Samsung Motor");
  }

}
