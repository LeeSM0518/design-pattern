package abstract_factory_pattern.unused_pattern;

import template_method_pattern.unused_pattern.Direction;

public class HyundaiMotor extends Motor {

  @Override
  protected void moveMotor(Direction direction) {
    System.out.println("run Hyundai motor");
  }

}
