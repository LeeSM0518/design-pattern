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

  * 팩토리 메서드 패턴을 적용해 주어진 제조 업체에 따라 LGMotor와 HyundaiMotor 중에서 해당 Motor 클래스를 생성하는 MotorFacotry 클래스

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

  * 팩토리 메서드 패턴을 적용해 주어진 제조 업체에 따라 LGDoor와 HyundaiDoor 중에서 해당 Door 클래스를 생성하는 DoorFacotry 클래스

* **Client**

  ```java
  public class Client {
  
    public static void main(String[] args) {
      Door lgDoor = DoorFactory.createDoor(VendorID.LG);
      Motor lgMotor = MotorFactory.createMotor(VendorID.LG);
      lgMotor.setDoor(lgDoor);
  
      lgDoor.open();
      lgMotor.move(Direction.UP);
    }
  
  }
  ```

* **실행 결과**

  ```
  open LG Door
  close LG Door
  run LG motor
  ```

<br>

# 13.2. 문제점

* 만약 다른 제조 업체의 부품을 사용해야 한다면?
* 만약 새로운 제조 업체의 부품을 지원해야 한다면?

<br>

## 13.2.1. 다른 제조 업체의 부품을 사용해야 하는 경우

이전의 코드와 같은 구조가 아니라 만약 엘리베이터가 모터와 문은 물론이고 세 종류의 램프, 두 종류의 센서, 스피커, 두 종류의 버튼 등 총 10개의 부품을 사용해야 한다면 **각 Factory 클래스를 구현하고 이들의 Factory 객체를 각각 생성해야 한다.**

* **10개의 붚품을 현대 부품으로 사용하도록 수정한 Client 클래스**

  ```java
  public class Client {
    public static void main(String[] args) {
      Door hyundaiDoor = DoorFactory.createDoor(VendorID.HYUNDAI);
      Motor hyundaiMotor = MotorFactory.createMotor(VendorID.HYUNDAI);
      hyndaiMotor.setDoor(hyundaiDoor);
      ArrivalSensor hyundaiArrivalSensor =
        ArrivalSensorFactory.createArrivalSensor(VendorID.HYUNDAI);
  		WeightSensor hyundaiWeightSensor =
        WeightSensorFactory.createWeightSensor(VendorID.HYUNDAI);
      ... // 나머지 6개의 부품들을 적용하는 코드
      hyundaiDoor.open();
      hyndaiMotor.move(Direction.UP);
    }
  }
  ```

  * 부품의 수가 많아지면 특정 업체별 부품을 생성하는 코드의 길이가 길어지고 복잡해진다.

<br>

## 13.2.2. 새로운 제조 업체의 부품을 지원해야 하는 경우

삼성의 엘리베이터 부품을 지원해야 한다면 SamsungMotor와 SamsungDoor 클래스를 MotorFactory와 DoorFactory 클래스에서 지원이 되어야 한다.

* **DoorFactory**

  ```java
  public class DoorFactory {
  
    public static Door createDoor(VendorID vendorID) {
      Door door = null;
      switch (vendorID) {
        case LG:
          door = new LGDoor();
          break;
        case HYUNDAI:
          door = new HyundaiDoor();
          break;
        case SAMSUNG:
          door = new SamgsungDoor();
          break;
      }
      return door;
    }
  
  }
  ```

  * 하지만 DoorFactory 뿐만 아니라 부품이 10가지 이상이면 부품과 연관된 모든 Factory 클래스에서도 마찬가지로 삼성의 부품을 생성하도록 변경할 필요가 있다. 즉, **여러 개의 클래스의 수정이 필요하기 때문에** 잘못된 설계이다.

<br>

결론적으로 지금까지 언급한 문제점을 요약하면 기존의 팩토리 메서드 패턴을 이용한 객체 생성은 **관련 있는 여러 개의 객체를 일관성 있는 방식으로 생성하는 경우에 많은 코드 변경이 발생하게 된다.** 

<br>

# 13.3. 해결책

여러 종류의 객체를 생성할 때 객체들 사이의 관련성이 있는 경우라면 각 종류별로 별도의 Factory 클래스를 사용하는 대신 관련 **객체들을 일관성 있게 생성하는 Factory 클래스를 사용하는 것이 편리할 수가 있다.**

예를 들어 MotorFactory, DoorFactory 클래스와 같이 부품별로 Factory 클래스를 만드는 대신 LGElevatorFactory나 HyundaiElevatorFactory 클래스와 같이 **제조 업체별로 Factory 클래스를 만들 수도 있다.**

<br>

### *LGElavtorFactory와 HyundaiElevatorFactory 클래스를 이용한 설계*

<img src="../../capture/스크린샷 2019-11-14 오후 9.13.21.png">

* LGElevatorFactory 와 HyundaiElevatorFactory 클래스는 createMotor 메서드와 createDoor 메서드를 통해 각 제조사에 맞는 Motor와 Door 객체를 생성한다.
* 즉, 2개의 ElevatorFactory 클래스는 모두 똑같은 createMotor 와 createDoor 메서드를 제공하기 때문에 **두 팩토리 클래스를 일반화한 상위 클래스를 정의할 수 있다.**

<br>

### *LGElevatorFactory와 HyundaiEleavtorFactory 클래스의 일반화*

<img src="../../capture/스크린샷 2019-11-14 오후 9.32.47.png" width=500>

* ElevatorFactory는 **추상 클래스고** createMotor와 createDoor도 **추상 메서드로** 정의된다.

<br>

### *코드*

* **ElevatorFactory**

  ```java
  public abstract class ElevatorFactory {
  
    public abstract Motor createMotor();
    public abstract Door createDoor();
  
  }
  ```

* **HyundaiElevatorFactory**

  ```java
  public class HyundaiElevatorFactory extends ElevatorFactory {
  
    @Override
    public Motor createMotor() {
      return new HyundaiMotor();
    }
  
    @Override
    public Door createDoor() {
      return new HyundaiDoor();
    }
  
  }
  ```

* **LGElevatorFactory**

  ```java
  public class LGElevatorFactory extends ElevatorFactory {
  
    @Override
    public Motor createMotor() {
      return new LGMotor();
    }
  
    @Override
    public Door createDoor() {
      return new LGDoor();
    }
  
  }
  ```

* **Client**

  ```java
  public class Client {
  
    public static void main(String[] args) {
      ElevatorFactory factory = new HyundaiElevatorFactory();
      Door door = factory.createDoor();
      Motor motor = factory.createMotor();
      motor.setDoor(door);
  
      door.open();
      motor.move(Direction.UP);
    }
  
  }
  ```

  * 제조 업체별로 Factory 클래스를 정의했으므로 제조 업체별 부품 객체를 아주 간단히 생성할 수 있다.

* **실행 결과**

  ```
  open Hyundai Door
  close Hyundai Door
  run Hyundai motor
  ```

<br>

### *새로운*

새로운 제조 업체의 부품을 지원하는 경우 이저에는 삼성 부품의 객체를 생성하도록 부품별 Factory 클래스를 수정해줘야 했지만, 

