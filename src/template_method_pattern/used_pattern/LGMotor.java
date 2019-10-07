package template_method_pattern.used_pattern;

import template_method_pattern.unused_pattern.Direction;
import template_method_pattern.unused_pattern.Door;

public class LGMotor extends Motor {

  public LGMotor(Door door) {
    super(door);
  }

  @Override
  protected void moveMotor(Direction direction) {
    if (direction == Direction.UP) {
      System.out.println("위로 올라갑니다(LG 모터).");
    } else {
      System.out.println("아래로 내려갑니다(LG 모터).");
    }
  }

}
