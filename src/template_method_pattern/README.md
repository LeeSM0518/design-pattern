# 11. 템플릿 메서드 패턴

# 11.1. 여러 회사의 모터 지원하기

엘레베이터 제어 시스템에서 모터를 구동시키는 기능을 구현해보자.

<br>

### *현대 모터를 구동시키는 HyundaiMotor 클래스 설계*

<img src="../../capture/스크린샷 2019-10-07 오전 11.16.10.png">

<br>

### *Enumeration 인터페이스인 MotorStatus, DoorStatus, Direction의 설계*

* 각각 모터의 상태(정지 중, 이동 중)

  <img src="../../capture/스크린샷 2019-10-07 오전 1.31.22.png" width=200>

* 문의 상태(닫혀 있는 중, 열려 있는 중)

  <img src="../../capture/스크린샷 2019-10-07 오전 1.31.55.png" width=200>

* 이동 방향(위로, 아래로)

  <img src="../../capture/스크린샷 2019-10-07 오전 1.32.20.png" width=200>

<br>

<img src="https://leetaehyun94.github.io/assets/Design_Pattern/53.PNG">

<br>

### *코드*

DoorStatus

```java
public enum DoorStatus {
  CLOSED, OPENED
}
```

MotorStatus

```java
public enum MotorStatus {
  MOVING, STOPPED
}
```

Direction

```java
public enum Direction {
  UP, DOWN
}
```

Door

```java
public class Door {

  private DoorStatus doorStatus;

  public Door() {
    doorStatus = DoorStatus.CLOSED;
  }

  public DoorStatus getDoorStatus() {
    return doorStatus;
  }

  public void close() {
    doorStatus = DoorStatus.CLOSED;
  }

  public void open() {
    doorStatus = DoorStatus.OPENED;
  }

}
```

HyundaiMotor

```java
package template_method_pattern;

import template_method_pattern.unused_pattern.Direction;public class HyundaiMotor {
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
```

Client

```java
public class Client {

  public static void main(String[] args) {
    Door door = new Door();
    HyundaiMotor hyundaiMotor = new HyundaiMotor(door);
    hyundaiMotor.move(Direction.UP);
    hyundaiMotor.setMotorStatus(MotorStatus.STOPPED);
    hyundaiMotor.move(Direction.DOWN);
  }

}
```

실행 결과

```
위로 올라갑니다(현대 모터).
아래로 내려갑니다(현대 모터).
```

<br>

# 11.2. 문제점

* **HyundaiMotor 클래스는 현대 모터를 구동시킨다. 만약 다른 회사의 모터를 제어해야 한다면?**
  * LG 모터를 구동하는 것은 현대 모터를 구동하는 것과 다르기 때문에 코드를 수정할 필요가 있다.

<br>

### *LGMotor 클래스 구현*

```java
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
```

* LGMotor 클래스와 HyundaiMotor 클래스는 여러 개의 메서드가 동일하여 많은 중복 코드를 가지는 것을 알 수 있다. 즉, 코드 중복은 유지보수성을 약화시킨다.
* 2개 이상의 클래스가 유사한 기능을 제공하면서 중복된 코드가 있는 경우에는 **상속을 이용해서** 코드 중복 문제를 해결할 수 있다.

<br>

### *HyundaiMotor 와 LGMotor 클래스의 상위 클래스인 Motor의 정의*

<img src="../../capture/스크린샷 2019-10-07 오전 11.36.01.png">

<br>

### *코드*

Motor

```java
// HyundaiMotor 와 LGMotor 에 공통적인 기능을 구현하는 클래스
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
```

HyundaiMotor

```java
// Motor를 상속받아 HyundaiMotor를 구현함
public class HyundaiMotor extends Motor {

  public HyundaiMotor(Door door) {
    super(door);
  }

  private void moveHyundaiMotor(Direction direction) {
    if (direction == Direction.UP) {
      System.out.println("위로 올라갑니다(현대 모터).");
    } else {
      System.out.println("아래로 내려갑니다(현대 모터).");
    }
  }

  // move 메서드는 LGMotor와 상이하므로 여기서 구현한다.
  public void move(Direction direction) {
    MotorStatus motorStatus = getMotorStatus();
    if (motorStatus == MotorStatus.MOVING)
      return;

    DoorStatus doorStatus = door.getDoorStatus();
    if (doorStatus == DoorStatus.OPENED)
      door.close();

    // move 메서드는 이 구문을 제외하면 LGMotor와 동일하다
    moveHyundaiMotor(direction);
    setMotorStatus(MotorStatus.MOVING);
  }

}
```

LGMotor

```java
// Motor를 상속받아 LGMotor를 구현함
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

  // move 메서드는 HyundaiMotor와 상이하므로 여기서 구현
  public void move(Direction direction) {
    MotorStatus motorStatus = getMotorStatus();
    if (motorStatus == MotorStatus.MOVING)
      return;

    DoorStatus doorStatus = door.getDoorStatus();
    if (doorStatus == DoorStatus.OPENED)
      door.close();

    // move 메서드는 이 구문을 제외하면 HyundaiMotor와 동일
    moveLGMotor(direction);
    
    setMotorStatus(MotorStatus.MOVING);
  }

}
```

* LGMotor 클래스와 HyundaiMotor 클래스의 move 메서드를 비교해보면 대부분이 비슷하다. 즉, 여전히 코드 중복 문제가 있다.

<br>

# 11.3.  해결책

완전히 중복되지는 않지만 부분적으로 중복되는 경우에도 상속을 활용해 코드 중복을 피할 수 있다.

<br>

### *move 메서드의 중복 코드를 최소화한 설계*

<img src="../../capture/스크린샷 2019-10-07 오후 1.11.36.png">

<br>

### *코드*

Motor

```java
public abstract class Motor {

  private Door door;
  private MotorStatus motorStatus;

  public Motor(Door door) {
    this.door = door;
    motorStatus = MotorStatus.STOPPED;
  }

  public MotorStatus getMotorStatus() {
    return motorStatus;
  }

  private void setMotorStatus(MotorStatus motorStatus) {
    this.motorStatus = motorStatus;
  }

  public void move(Direction direction) {
    MotorStatus motorStatus = getMotorStatus();
    if (motorStatus == MotorStatus.MOVING) return;

    DoorStatus doorStatus = door.getDoorStatus();
    if (doorStatus == DoorStatus.OPENED) door.close();

    moveMotor(direction);
    setMotorStatus(MotorStatus.MOVING);
  }

  protected abstract void moveMotor(Direction direction);

}
```

HyundaiMotor

```java
public class HyundaiMotor extends Motor {

  public HyundaiMotor(Door door) {
    super(door);
  }

  @Override
  protected void moveMotor(Direction direction) {
    if (direction == Direction.UP) {
      System.out.println("위로 올라갑니다(현대 모터).");
    } else {
      System.out.println("아래로 내려갑니다(현대 모터).");
    }
  }

}
```

LGMotor

```java
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
```

<br>

# 11.4. 템플릿 메서드 패턴

템플릿 메서드 패턴(Template Method Pattern)은 **전체적으로는 동일하면서 부분적으로는 다른 구문으로 구성된 메서드의 코드 중복을 최소화할 때** 유용하다.

위의 예시에서 Motor 클래스의 **move 메서드를 템플릿 메서드** 라고 부르고, move 메서드에서 호출되면서 **하위 클래스에서 오버라이드될 필요가 있는 moveMotor 메서드를 primitive 또는 hook 메서드라 부른다.**

* **템플릿 메서드의 개념**

  ```java
  // 템플릿 메서드
  public void move(Direction direction) {
    MotorStatus motorStatus = getMotorStatus();
    if (motorStatus == MotorStatus.MOVING) return;
  
    DoorStatus doorStatus = door.getDoorStatus();
    if (doorStatus == DoorStatus.OPENED) door.close();
  
    // Primitive 메서드 또는 hook 메서드
    moveMotor(direction);
    setMotorStatus(MotorStatus.MOVING);
  }
  ```

<br>

### *템플릿 메서드 패턴의 컬레보레이션*

<img src="../../capture/스크린샷 2019-10-07 오후 2.31.58.png" width=500>

* **AbstractClass** : <u>템플릿 메서드를 정의하는 클래스.</u> 하위 클래스에 공통 알고리즘을 정의하고 하위 클래스에서 구현될 기능을 primitive 메서드 또는 hook 메서드로 정의하는 클래스다.
* **ConcreteClass** : <u>물려받은 primitive 메서드나 hook 메서드를 구현하는 클래스.</u> 상위 클래스에 구현된 템플릿 메서드의 일반적인 알고리즘에서 하위 클래스에 적합하게 primitive 메서드나 hook 메서드를 오버라이드하는 클래스다.