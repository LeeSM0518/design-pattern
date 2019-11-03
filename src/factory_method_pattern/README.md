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
  
      int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
  
      if (hour < 12)
        scheduler = new ResponseTimeScheduler();
      else
        scheduler = new ThroughputScheduler();
  
      int selectedElevator = scheduler.selectElevator(this, destination, direction);
      controllers.get(selectedElevator).gotoFloor(destination);
    }
  
  }
  ```

  