package template_method_pattern.unused_pattern_another;

import template_method_pattern.unused_pattern.Direction;
import template_method_pattern.unused_pattern.Door;
import template_method_pattern.unused_pattern.DoorStatus;
import template_method_pattern.unused_pattern.MotorStatus;

public class LGMotor extends Motor {

  public LGMotor(Door door) {
    super(door);
  }

  private void moveLGMotor(Direction direction) {
    if (direction == Direction.UP) {
      System.out.println("위로 올라갑니다(LG 모터).");
    } else {
      System.out.println("아래로 내려갑니다(LG 모터).");
    }
  }

  public void move(Direction direction) {
    MotorStatus motorStatus = getMotorStatus();
    if (motorStatus == MotorStatus.MOVING)
      return;

    DoorStatus doorStatus = door.getDoorStatus();
    if (doorStatus == DoorStatus.OPENED)
      door.close();

    moveLGMotor(direction);
    setMotorStatus(MotorStatus.MOVING);
  }

}
