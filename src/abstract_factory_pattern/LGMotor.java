package abstract_factory_pattern;

import template_method_pattern.unused_pattern.Direction;

public class LGMotor extends Motor {

  @Override
  protected void moveMotor(Direction direction) {
    System.out.println("run LG motor");
  }

}
