package template_method_pattern.unused_pattern;

public class HyundaiMotor {

  private Door door;
  private MotorStatus motorStatus;

  public HyundaiMotor(Door door) {
    this.door = door;
    motorStatus = MotorStatus.STOPPED;  // 초기에는 멈춘 상태
  }

  private void moveHyundaiMotor(Direction direction) {
    if (direction == Direction.UP) {
      System.out.println("위로 올라갑니다(현대 모터).");
    } else {
      System.out.println("아래로 내려갑니다(현대 모터).");
    }
  }

  public MotorStatus getMotorStatus() {
    return motorStatus;
  }

  public void setMotorStatus(MotorStatus motorStatus) {
    this.motorStatus = motorStatus;
  }

  public void move(Direction direction) {
    MotorStatus motorStatus = getMotorStatus();
    if (motorStatus == MotorStatus.MOVING)
      return;

    DoorStatus doorStatus = door.getDoorStatus();
    if (doorStatus == DoorStatus.OPENED)
      door.close();

    moveHyundaiMotor(direction);
    setMotorStatus(MotorStatus.MOVING);
  }

}
