package template_method_pattern.unused_pattern_another;

import template_method_pattern.unused_pattern.Door;
import template_method_pattern.unused_pattern.MotorStatus;

public abstract class Motor {

  protected Door door;
  private MotorStatus motorStatus;

  public Motor(Door door) {
    this.door = door;
    motorStatus = MotorStatus.STOPPED;
  }

  public MotorStatus getMotorStatus() {
    return motorStatus;
  }

  protected void setMotorStatus(MotorStatus motorStatus) {
    this.motorStatus = motorStatus;
  }

}
