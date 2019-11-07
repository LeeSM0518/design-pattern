# 12. 팩토리 메서드 패턴

## 12.1. 여러 가지 방식의 엘리베이터 스케줄링 방법

### *구현 사항*

* 엘리베이터 내부에서 버튼(ElevatorButton 클래스)
* 건물 내부의 층에서 버튼(FloorButton 클래스)

* 여러 대의 엘리베이터 중 하나를 이동 시켜야 한다.
* 주어진 요청(목적지 층과 방향)을 받았을 때 여러 대의 엘리베이터 중 하나를 선택하는 것

<br>

### *작업 처리량(Throughput)을 기준으로 한 스케줄링에 따른 엘리베이터 관리*

<img src="../../capture/스크린샷 2019-11-03 오후 11.19.52.png">

* **ElevatorManager 클래스** : 이동 요청을 처리하는 클래스로 엘리베이터를 스케줄링(엘리베이터 선택)하기 위한 ThroughputScheduler 객체를 갖는다. 그리고 각 엘리베이터의 이동을 책임지는 ElevatorController 객체를 복수 개 갖는다.
  * **requestElevator 메서드** : 요청(목적지 층, 방향)을 받았을 때 우선 ThroughputScheduler 클래스의 selectElevator 메서드를 호출해 적절한 엘리베이터를 선택한다. 그리고 선택된 엘리베이터에 해당하는 ElevatorController 객체의 gotoFloor 메서드를 호출해 엘리베이터를 이동시킨다.

<br>

### *코드*

* **ElevatorManager**

  ```java
  package factory_method_pattern;
  
  import template_method_pattern.unused_pattern.Direction;
  
  import java.util.ArrayList;
  import java.util.List;
  
  public class ElevatorManager {
  
    private List<ElevatorController> controllers;
    private ThroughputScheduler scheduler;
  
    public ElevatorManager(int controllerCount) {
      controllers = new ArrayList<>(controllerCount);
      for (int i = 0; i < controllerCount; i++) {
        ElevatorController controller = new ElevatorController(i);
        controllers.add(controller);
      }
      scheduler = new ThroughputScheduler();
    }
  
    void requestElevator(int destination, Direction direction) {
      // ThroughputScheduler 를 이용해 엘리베이터를 선택함
      int selectedElevator = scheduler.selectElevator(this, destination, direction);
      
      // 선택된 엘리베이터를 이동시킴
      controllers.get(selectedElevator).gotoFloor(destination);
    }
  
  }
  ```

* **ElevatorController**

  ```java
  package factory_method_pattern;
  
  public class ElevatorController {
  
    private int id;         // 엘리베이터 ID
    private int curFloor;   // 현재 층
  
    public ElevatorController(int id) {
      this.id = id;
      curFloor = 1;
    }
  
    public void gotoFloor(int destination) {
      System.out.println("Elevator [" + id + "] Floor: " + curFloor);
  
      // 현재 층 갱신, 즉 주어진 목적지 층(destination)으로 엘리베이터가 이동
      curFloor = destination;
      System.out.println(" ==> " + curFloor);
    }
  
  }
  ```

* **ThroughputScheduler**

  ```java
  package factory_method_pattern;
  
  import template_method_pattern.unused_pattern.Direction;
  
  public class ThroughputScheduler {
  
    public int selectElevator(ElevatorManager manager,
                              int destination, Direction direction) {
      // 임의로 선택함
      return 0;
    }
  
  }
  ```

<br>

## 12.2. 문제점

* 현재 ElevatorManager 클래스는 ThroughputSheduler 클래스를 이용한다. 즉, 엘리베이터의 처리량을 최대화시키는 전략을 사용한다는 의미다. **만약 다른 스케줄링 전략을 사용해야 한다면?** 예를 들어 사용자의 대기 시간을 최소화하는 엘리베이터 선택 전략을 사용해야 한다면?
* 프로그램 실행 중에 스케줄링 전략을 변경, 즉 **동적 스케줄링을 지원해야 한다면?** 예를 들어 오전에는 대기 시간 최소화 전략을 사용하고, 오후에는 처리량 최대화 전략을 사용해야 한다면?

<br>

1. 우선 대기 시간을 최소화하는 전략을 수행하기 위한 스케줄링 클래스를 구현한다(ResponseTimeScheduler 클래스).
2. 기존에는 ElevatorManager 클래스에서 ThroughputScheduler 객체를 이용했지만 이제는 주어진 전략에 따라 또는 실행 중에 전략이 변경될 수 있어야 한다.

<br>

### *코드*

* **ElevatorManager**

  ```java
  package factory_method_pattern;
  
  import template_method_pattern.unused_pattern.Direction;
  
  import java.util.ArrayList;
  import java.util.Calendar;
  import java.util.List;
  
  public class ElevatorManager {
  
    private List<ElevatorController> controllers;
  
    public ElevatorManager(int controllerCount) {
      controllers = new ArrayList<>(controllerCount);
      for (int i = 0; i < controllerCount; i++) {
        ElevatorController controller = new ElevatorController(i);
        controllers.add(controller);
      }
    }
  
    void requestElevator(int destination, Direction direction) {
      ElevatorScheduler scheduler;
  
      // 0 ~ 23
      int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
  
      if (hour < 12)	// 오전에는 ResponseTimeScheduler 이용
        scheduler = new ResponseTimeScheduler();
      else            // 오후에는 ThroughoutScheduler 이용
        scheduler = new ThroughputScheduler();
  
      int selectedElevator = scheduler.selectElevator(this, destination, direction);
      controllers.get(selectedElevator).gotoFloor(destination);
    }
  
  }
  ```

* **ElevatorScheduler**

  ```java
  package factory_method_pattern;
  
  import template_method_pattern.unused_pattern.Direction;
  
  public interface ElevatorScheduler {
  
    int selectElevator(ElevatorManager manager,
                       int destination, Direction direction);
  
  }
  ```

* **ResponseTimeScheduler**

  ````java
  package factory_method_pattern;
  
  import template_method_pattern.unused_pattern.Direction;
  
  public class ResponseTimeScheduler implements ElevatorScheduler {
    @Override
    public int selectElevator(ElevatorManager manager, int destination,
                              Direction direction) {
      // 임의로 선택
      return 0;
    }
  }
  ````

* **ThroughputScheduler**

  ```java
  package factory_method_pattern;
  
  import template_method_pattern.unused_pattern.Direction;
  
  public class ThroughputScheduler implements ElevatorScheduler {
  
    public int selectElevator(ElevatorManager manager,
                              int destination, Direction direction) {
      // 임의로 선택함
      return 0;
    }
  
  }
  ```

오전에는 대기 시간 최소화 전략을, 오후에는 처리량 최대화 전략을 사용하는 동적 스케줄링을 해야 하므로 requestElevator 메서드가 실행될 때마다 **현재 시간에 따라 적절한 스케줄링 객체를 생성해야 한다.**

<br>

ElevatorManager 클래스는 ThroughputScheduler 객체와 ResponseTimeSheduler 객체를 생성한 후, 이를 각각의 클래스가 아니라 ElevatorScheduler라는 인터페이스를 통해 사용한다. 이는 결과적으로 **스트래티지 패턴을 활용해 엘리베이터 스케줄링 전략을 설계한 것이다.**

### *스트래티지 패턴을 활용한 엘리베이터 스케줄링 전략 설계*

<img src="../../capture/스크린샷 2019-11-04 오전 12.11.54.png">

* 스트래티지 패턴을 사용해도 문제는 여전히 남아 있다. **ElevatorManager 클래스는 엘리베이터 스케줄링 전략이 변경될 때 requestElevator 메서드도 수정되어야 하기 때문이다.**

<br>

## 12.3. 해결책

위와 같은 문제를 해결하려면 **주어진 기능을 실제로 제공하는 적절한 클래스 생성 작업을 별도의 클래스/메서드로 분리시키는 편이 좋다.** 예를 들어 엘리베이터 스케줄링 전략에 일치하는 클래스를 생성하는 코드를 requestElevator 메서드에서 분리해 별도의 클래스/메서드를 정의하면 된다.

<br>

### *SchedulerFactory를 이용한 스케줄링 전략 객체의 생성*

<img src="../../capture/스크린샷 2019-11-04 오전 12.36.53.png">

* ElevatorManager 클래스가 지금처럼 직접 ThroughputScheduler 객체와 ResponseTimeScheduler 객체를 생성하는 대신 **SchedulerFactory 클래스가 이들 객체를 생성하도록 변경할 설계다.**

<br>

### *코드*

* **SchedulingStrategyID**

  ```java
  package factory_method_pattern;
  
  public enum SchedulingStrategyID {
    RESPONSE_TIME, THROUGHPUT, DYNAMIC
  }
  ```

* **SchedulerFactory**

  ```java
  package factory_method_pattern;
  
  import java.util.Calendar;
  
  public class SchedulerFactory {
  
    public static ElevatorScheduler getScheduler(SchedulingStrategyID strategyID) {
      ElevatorScheduler scheduler = null;
      switch (strategyID) {
        case RESPONSE_TIME:
          scheduler = new ResponseTimeScheduler();
          break;
        case THROUGHPUT:
          scheduler = new ThroughputScheduler();
          break;
        case DYNAMIC:
          int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
          if (hour < 12)
            scheduler = new ResponseTimeScheduler();
          else
            scheduler = new ThroughputScheduler();
          break;
      }
      return scheduler;
    }
  
  }
  ```

  * **getScheduler 메서드** : 인자로 주어진 SchedulingPolicyID에 따라 적절한 스케줄링 객체를 생성한다.

* **ElevatorManager**

  ```java
  package factory_method_pattern;
  
  import template_method_pattern.unused_pattern.Direction;
  
  import java.util.ArrayList;
  import java.util.List;
  
  public class ElevatorManager {
  
    private List<ElevatorController> controllers;
    private SchedulingStrategyID strategyID;
  
    public ElevatorManager(int controllerCount, SchedulingStrategyID strategyID) {
      controllers = new ArrayList<>(controllerCount);
      for (int i = 0; i < controllerCount; i++) {
        ElevatorController controller = new ElevatorController(i);
        controllers.add(controller);
      }
      this.strategyID = strategyID;
    }
  
    public void setStrategyID(SchedulingStrategyID strategyID) {
      this.strategyID = strategyID;
    }
  
    void requestElevator(int destination, Direction direction) {
      ElevatorScheduler scheduler = SchedulerFactory.getScheduler(strategyID);
      System.out.println(scheduler);
      int selectedElevator = scheduler.selectElevator(this, destination, direction);
      controllers.get(selectedElevator).gotoFloor(destination);
    }
  
  }
  ```

  * **requestElevator 메소드** : 직접 스케줄링 클래스를 생성하는 대신 SchedulerFactory 클래스의 getScheduler 메서드를 호출하면 된다.
  * ElevatorManager 클래스의 생성자에 SchedulingStrategyID 인자를 명시함으로써 해당 ElevatorManager 클래스의 스케줄링 전략을 지정하도록 했다.

* **Client**

  ```java
  package factory_method_pattern;
  
  import template_method_pattern.unused_pattern.Direction;
  
  public class Client {
  
    public static void main(String[] args) {
      ElevatorManager emWithResponseTimeScheduler =
          new ElevatorManager(2, SchedulingStrategyID.RESPONSE_TIME);
      emWithResponseTimeScheduler.requestElevator(10 , Direction.UP);
  
      ElevatorManager emWithThroughoutScheduler =
          new ElevatorManager(2, SchedulingStrategyID.THROUGHPUT);
      emWithThroughoutScheduler.requestElevator(10, Direction.UP);
  
      ElevatorManager emWithDynamicScheduler =
          new ElevatorManager(2, SchedulingStrategyID.DYNAMIC);
      emWithDynamicScheduler.requestElevator(10, Direction.UP);
    }
  
  }
  ```

> 스케줄의 방식이 동일하다면 여러 번 스케줄링 객체를 생성하지 않고 한 번 생성한 것을 계속해서 사용하는 것이 바람직할 수 있다. 따라서 스케줄링 기능을 제공하는 클래스들은 오직 하나의 객체만 생성해서 사용해도 충분하다( **싱글턴 패턴** ).

<br>

### *싱글턴 패턴을 적용한 스케줄링 전략 클래스의 설계*

<img src="../../capture/스크린샷 2019-11-07 오후 1.01.10.png">

* 생성자를 **private으로** 정의했다. 대신 getInstance라는 정적 메서드로 객체 생성을 구현했다.

<br>

### *코드*

* **ThroughputScheduler**

  ```java
  public class ThroughputScheduler implements ElevatorScheduler {
  
    private static ElevatorScheduler scheduler;
    private ThroughputScheduler(){}
  
    public static ElevatorScheduler getInstance() {
      if (scheduler == null)
        scheduler = new ThroughputScheduler();
  
      return scheduler;
    }
  
    public int selectElevator(ElevatorManager manager,
                              int destination, Direction direction) {
      // 임의로 선택함
      return 0;
    }
  
  }
  ```

  > 싱글턴 패턴 구현

* **ResponseTimeScheduler**

  ```java
  public class ResponseTimeScheduler implements ElevatorScheduler {
  
    private static ElevatorScheduler scheduler;
    private ResponseTimeScheduler(){}
  
    public static ElevatorScheduler getInstance() {
      if (scheduler == null)
        scheduler = new ResponseTimeScheduler();
  
      return scheduler;
    }
  
    @Override
    public int selectElevator(ElevatorManager manager, int destination,
                              Direction direction) {
      // 임의로 선택
      return 0;
    }
  }
  ```

  > 싱글턴 패턴 구현

<br>

## 12.4. 팩토리 메서드 패턴

**팩토리 메서드 패턴(Factory Method Pattern)** : 객체의 생성 코드를 별도의 클래스/메서드로 분리함으로써 객체 생성의 변화에 대비하는데 유용하다.

<br>

* **팩토리 메서드 패턴의 개념**

  <img src="../../capture/스크린샷 2019-11-07 오후 3.49.46.png">

  * 팩토리 메서드 패턴을 사용하면 객체 생성 기능을 제공하는 Factory 클래스를 정의하고 이를 활용하는 방식으로 설계하면 된다.
  * 이렇게 설계하면 X1과 X2의 생성 방식이 변경되거나 X3를 추가해야 할 때 Factory 클래스만 변경하고 클래스 A, 클래스 Z 등은 변경할 필요가 없게 된다.

<br>

또한, 팩토리 메서드 패턴은 객체 생성을 전달하는 별도의 클래스를 두는 대신 **하위 클래스에서 적합한 클래스의 객체를 생성하는 방식으로도 적용할 수 있다.** 

### *상속을 이용한 팩토리 메서드 패턴의 적용*

<img src="../../capture/스크린샷 2019-11-07 오후 4.03.54.png">

* **ElevatorManager 클래스는** 아직 구체적인 스케줄링 전략이 결정되지 않았으므로 **getScheduler 메서드를** 추상 메서드로 정의
* 3개의 하위 클래스에서는 getScheduler 메서드를 오버라이드함으로써 구체적인 스케줄링 전략 객체를 생성하도록 했다.

<br>

### *코드*

* **ElevatorManager**

  ```java
  public abstract class ElevatorManager {
  
    private List<ElevatorController> controllers;
  
    public ElevatorManager(int controllerCount) {
      controllers = new ArrayList<>(controllerCount);
      for (int i = 0; i < controllerCount; i++) {
        ElevatorController controller = new ElevatorController(i + 1);
        controllers.add(controller);
      }
    }
  
    // primitive 또는 hook 메서드
    protected abstract ElevatorScheduler getScheduler();
  
    // 템플릿 메서드
    void requestElevator(int destination, Direction direction) {
      // 히위 클래스에서 오버라이드된 getScheduler 호출
      ElevatorScheduler scheduler = getScheduler();
      int selectedElevator = scheduler.selectElevator(this, destination, direction);
      controllers.get(selectedElevator).gotoFloor(destination);
    }
  
  }
  ```

  * **requestElevator 메서드는** getScheduler 메서드를 추상 메서드로 정의
  * ElevatorManager 클래스의 하위 클래스는 **getScheduler 메서드를** 오버라이드하는 방식으로 설계. 이때 getScheduler 메서드는 스케줄링 전략 객체를 생성하는 기능 제공하므로 팩토리 메서드 이다.

* **ElevatorManagerWithThroughputScheduling**

  ```java
  public class ElevatorManagerWithThroughputScheduling extends ElevatorManager {
  
    public ElevatorManagerWithThroughputScheduling(int controllerCount) {
      super(controllerCount);
    }
  
    // 대기 시간 최소화 전략을 사용함
    @Override
    protected ElevatorScheduler getScheduler() {
      ElevatorScheduler scheduler = ThroughputScheduler.getInstance();
      return scheduler;
    }
  
  }
  ```

* **ElevatorManagerWithResponseTimeScheduling**

  ```java
  public class ElevatorManagerWithResponseTimeScheduling extends ElevatorManager {
  
    public ElevatorManagerWithResponseTimeScheduling(int controllerCount) {
      super(controllerCount);
    }
  
    // 처리량 최대화 전략을 사용함
    @Override
    protected ElevatorScheduler getScheduler() {
      ElevatorScheduler scheduler = ResponseTimeScheduler.getInstance();
      return scheduler;
    }
  }
  ```

* **ElevatorManagerWithDynamicScheduling**

  ```java
  public class ElevatorManagerWithDynamicScheduling extends ElevatorManager {
    public ElevatorManagerWithDynamicScheduling(int controllerCount) {
      super(controllerCount);
    }
  
    // 동적 스케줄링을 사용함
    @Override
    protected ElevatorScheduler getScheduler() {
      ElevatorScheduler scheduler = null;
      int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
      if (hour < 12)  // 오전
        scheduler = ResponseTimeScheduler.getInstance();
      else            // 오후
        scheduler = ThroughputScheduler.getInstance();
  
      return scheduler;
    }
  }
  ```

하위 클래스에서 공통 기능(스케줄링 전략 객체 생성, 엘리베이터 선택, 엘리베이터 이동)의 일반 로직은 동일하지만 스케줄링 전략 객체 생성은 하위 클래스에서 오버라이드되었다. 그러므로 **requestElevator 메서드는 템플릿 메서드에 해당된다.**

<br>

이와 같이 상속 관계를 이용해 팩토리 메서드 패턴을 설계하는 경우, 팩토리 메서드를 이용해 구체적인 클래스의 객체를 생성하는 기능은 일반적으로 하위 클래스에서 오버라이드되게 한다. 그러므로 **팩토리 메서드를 호출하는 상위 클래스의 메서드는 템플릿 메서드가 된다.**

<br>

### *팩토리 메서드 패턴의 컬레보레이션*

<img src="../../capture/스크린샷 2019-11-07 오후 5.02.05.png" width=500>

* **Product** : 팩토리 메서드로 생성될 객체의 공통 인터페이스
* **ConcreteProduct** : 구체적으로 객체가 생성되는 클래스
* **Creator** : 팩토리 메서드를 갖는 클래스
* **ConcreteCreator** : 팩토리 메서드를 구현하는 클래스로 ConcreteProduct 객체를 생성

<br>