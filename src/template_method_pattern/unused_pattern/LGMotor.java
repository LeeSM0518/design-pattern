package template_method_pattern.unused_pattern;

public class LGMotor {

  private Door door;
  private MotorStatus motorStatus;

  public LGMotor(Door door) {
    this.door = door;
    motorStatus = MotorStatus.STOPPED;
  }

  private void moveLGMotor(Direction direction) {
    if (direction == Direction.UP) {
      System.out.println("위로 올라갑니다(LG 모터).");
    } else {
      System.out.println("아래로 내려갑니다(LG 모터).");
    }
  }

  public MotorStatus getMotorStatus() {
    return motorStatus;
  }

  private void setMotorStatus(MotorStatus motorStatus) {
    this.motorStatus = motorStatus;
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
