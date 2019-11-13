# 13. 추상 팩토리 패턴

# 13.1. 엘리베이터 부품 업체 변경하기

엘리베이터를 구현해야 하는데, 엘리베이터를 구성하는 많은 부품 중에서 모터와 문을 구현해야 한다고 가정하자.

<br>

### *구현 사항*

* **모터**
  * LG 모터
  * 현대 모터
* **문**
  * LG 문
  * 현대 문

<br>

### *LG와 현대의 모터와 문 클래스 다이어그램*

<img src="../../capture/스크린샷 2019-11-13 오후 9.36.44.png">

* LG 모터와 현대 모터는 구체적인 제어 방식은 다르지만 엘리베이터 입장에서는 모터를 구동해 엘리베이터를 이동시킨다는 면에서는 동일하다. 그러므로 **추상 클래스로 Motor를 정의하고 LGMotor와 HyundaiMotor를 하위 클래스로 정의한다.** 
* 문도 위와 같은 이유로 **추상 클래스로 Door를 정의하고 LGDoor와 HyundaiDoor를 하위 클래스로 정의한다.**
* Motor 클래스는 이동하기 전에 문을 닫아야 하기 때문에, **Motor 클래스에서 Door 클래스로의 연관 관계를 정의한다.**

<br>

### *템플릿 메서드 패턴을 적용한 코드*

* **Door**

  ```java
  public abstract class Door {
  
    private DoorStatus doorStatus;
  
    public Door() {
      doorStatus = DoorStatus.CLOSED;
    }
  
    public DoorStatus getDoorStatus() {
      return doorStatus;
    }
  
    public void close() {
      if (doorStatus == DoorStatus.CLOSED)
        return;
  
      doClose();
      doorStatus = DoorStatus.CLOSED;
    }
  
    protected abstract void doClose();
  
    public void open() {
      if (doorStatus == DoorStatus.OPENED)
        return;
  
      doOpen();
      doorStatus = DoorStatus.OPENED;
    }
  
    protected abstract void doOpen();
  
  }
  ```

* **LGDoor**

  ```java
  public class LGDoor extends Door{
  
    @Override
    protected void doClose() {
      System.out.println("close LG Door");
    }
  
    @Override
    protected void doOpen() {
      System.out.println("open LG Door");
    }
  
  }
  ```

* **HyundaiDoor**

  ```java
  public class HyundaiDoor extends Door {
  
    @Override
    protected void doClose() {
      System.out.println("close Hyundai Door");
    }
  
    @Override
    protected void doOpen() {
      System.out.println("open Hyundai Door");
    }
  
  }
  ```

* **Motor**

  ```java
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
  ```

* **LGMotor**

  ```java
  public class LGMotor extends Motor {
  
    @Override
    protected void moveMotor(Direction direction) {
      System.out.println("run LG motor");
    }
  
  }
  ```

* **HyundaiMotor**

  ```java
  public class HyundaiMotor extends Motor {
  
    @Override
    protected void moveMotor(Direction direction) {
      System.out.println("run Hyundai motor");
    }
  
  }
  ```

> 엘리베이터 입장에서는 특정 제조 업체의 모터와 문을 제어하는 클래스가 필요하다.
>
> 팩토리 메서드 패턴을 통해 모터와 문 객체의 생성 방식을 구현해보자.

<br>

### *모터 객체 생성을 위한 MotorFactory*

<img src="../../capture/스크린샷 2019-11-13 오후 11.27.30.png">

* MotorFactory 클래스의 createMotor 메서드는 인자로 주어진 **VendorID에 따라 LGMotor 객체 또는 HyundaiMotor 객체를 생성한다.**

<br>

### *Factory 클래스 코드*

* **VendorID**

  ```java
  public enum  VendorID {
    LG, HYUNDAI
  }
  ```

* **MotorFactory**

  ```java
  // 팩토리 메서드 패턴을 사용
  public class MotorFactory {
  
    // vendorID에 따라 LGMotor 또는 HyundaiMotor 객체를 생성
    public static Motor createMotor(VendorID vendorID) {
      Motor motor = null;
      switch (vendorID) {
        case LG:
          motor = new LGMotor();
          break;
        case HYUNDAI:
          motor = new HyundaiMotor();
          break;
      }
      return motor;
    }
  
  }
  ```

* **DoorFactory**

  ```java
  // 팩토리 메서드 패턴을 사용
  public class DoorFactory {
  
    // vendorID에 따라 LGDoor 또는 HyundaiDoor 객체를 생성
    public static Door createDoor(VendorID vendorID) {
      Door door = null;
      switch (vendorID) {
        case LG:
          door = new LGDoor();
          break;
        case HYUNDAI:
          door = new HyundaiDoor();
          break;
      }
      return door;
    }
  
  }
  ```

  