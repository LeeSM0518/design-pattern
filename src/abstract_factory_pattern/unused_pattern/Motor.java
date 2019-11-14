package abstract_factory_pattern.unused_pattern;

import template_method_pattern.unused_pattern.Direction;
import template_method_pattern.unused_pattern.DoorStatus;
import template_method_pattern.unused_pattern.MotorStatus;

public abstract class Motor {

  private MotorStatus motorStatus;
  private Door door;

  public Motor() {
    motorStatus = MotorStatus.STOPPED;
  }

  public MotorStatus getMotorStatus() {
    return motorStatus;
  }

  private void setMotorStatus(MotorStatus motorStatus) {
    this.motorStatus = motorStatus;
  }

  public void move(Direction direction) {
     if (motorStatus == MotorStatus.MOVING) return;

     if (door.getDoorStatus() == DoorStatus.OPENED)
       door.doClose();

     moveMotor(direction);
     motorStatus = MotorStatus.MOVING;
  }

  protected abstract void moveMotor(Direction direction);

  public void setDoor(Door door) {
    this.door = door;
  }
}
