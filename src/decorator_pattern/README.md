# 10. 데커레이터 패턴

# 10.1. 도로 표시 방법 조합하기

도로를 표시하는 기능을 제공할 때는 기본 도로 표시 기능을 제공하는 **RoadDisplay 클래스와** 기본 도로 표시에 추가적으로 차선을 표시하는 **RoadDisplayWithLane 클래스를** 설계할 수 있다. 이때 RoadDisplayWithLane 클래스 역시 도로 표시 기능을 제공하므로 **RoadDisplay 클래스의 하위 클래스로 설계한다.**

<br>

### *기본 도로 및 차선을 표시하는 RoadDisplay와 RoadDisplayWithLane 클래스의 설계*

<img src="../../capture/스크린샷 2019-09-29 오후 4.52.15.png" width=300>

<br>

**코드**

* **RoadDisplay class**

  ```java
  public class RoadDisplay {  // 기본 도로 표시 클래스
  
    public void draw() {
      System.out.println("기본 도로 표시");
    }
  
  }
  ```

* **RoadDisplayWithLane class**

  ```java
  // 기본 도로 표시 + 차선 표시 클래스
  public class RoadDisplayWithLane extends RoadDisplay {
  
    public void draw() {
      super.draw();  // 상위 클래스의 draw 메서드 호출
      drawLane();    // 추가적으로 차선 표시
    }
  
    private void drawLane() {
      System.out.println("차선 표시");
    }
  
  }
  ```

* **Client class**

  ```java
  public class Client {
  
    public static void main(String[] args) {
      RoadDisplay road = new RoadDisplay();
      road.draw();  // 기본 도로만 표시
  
      RoadDisplay roadWithLane = new RoadDisplayWithLane();
      roadWithLane.draw();  // 기본 도로 + 차선 표시
    }
  
  }
  ```

* **실행 결과**

  ```
  기본 도로 표시
  기본 도로 표시
  차선 표시
  ```

  RoadDisplayWithLane 클래스에서 기본 도로 표시 기능은 상위 클래스, 즉 **RoadDisplay 클래스의 draw 메서드를 호출함으로써 구현하고, 차선을 표시하는 추가 기능은 drawLane 메서드를 호출함으로써 구현한다.**

<br>

# 10.2. 문제점

* 또다른 도로 표시 기능을 추가로 구현하고 싶다면 어떻게 해야 하는가?
* 여러 가지 추가 기능을 조합해 제공하고 싶다면 어떻게 해야 하는가?

<br>

## 10.2.1. 또다른 도로 표시 기능을 추가로 구현하는 경우

**RoadDisplay 클래스를 상속받아** 교통량을 도로에 표시하는 클래스를 새로 정의할 수 있다.

<br>

### *기본 도로 및 교통량을 표시하는 RoadDisplayWithTraffic 클래스의 설계*

<img src="../../capture/스크린샷 2019-09-29 오후 5.41.01.png" width=700>

<br>

**코드**

* **RoadDisplayWithTraffic class**

  ```java
  // 기본 도로 표시 + 교통량 표시 클래스
  public class RoadDisplayWithTraffic extends RoadDisplay {
  
    public void draw() {
      super.draw();   // 상위 클래스의 draw 메서드를 호출해서 기본 도로를 표시
      drawTraffic();  // 추가적으로 교통량을 표시
    }
  
    private void drawTraffic() {
      System.out.println("교통량 표시");
    }
  
  }
  ```

<br>

## 10.2.2. 여러 가지 추가 기능을 조합해야 하는 경우

다양한 기능의 조합을 고려해야 하는 경우 상속을 통한 기능의 확장은 **각 기능별로 클래스를 추가해야 한다는 단점이 있다.**

<img src="../../capture/스크린샷 2019-09-29 오후 5.44.12.png">

추가 기능의 조합을 각각 별도의 클래스로 설계해야 되므로 **코드가 중복되는 문제점이 발생한다.**

<br>

# 10.3. 해결책

조합 수가 늘어나는 문제를 해결할 수 있는 설계를 하려면 **각 추가 기능별로 개별적인 클래스를 설계하고 기능을 조합할 때 각 클래스의 객체 조합을 이용하면 된다.**

<br>

### *개선된 추가 기능 조합의 설계*

<img src="../../capture/스크린샷 2019-09-29 오후 6.01.28.png">

* 기본 기능만 이용할 때는 RoadDisplay 클래스의 객체를 생성하면 충분하다.
* 차선을 표시하는 기능이 추가적으로 필요하다면 LaneDecorator 클래스의 객체가 필요하다.
* LaneDecorator 클래스는 RoadDisplay 객체에 대한 참조가 필요한데, 이는 LaneDecorator 클래스의 상위 클래스인 DisplayDecorator 클래스에서 Display 클래스의 **컴포지션(composition) 관계를** 통해 표현되고 있다.

<br>

### *코드*

* **Display abstract class**

  ```java
  public abstract class Display {
  
    public abstract void draw();
  
  }
  ```

* **RoadDisplay class**

  ```java
  // 기본 도로 표시 클래스
  public class RoadDisplay extends Display {
  
    @Override
    public void draw() {
      System.out.println("기본 도로 표시");
    }
  
  }
  ```

* **DisplayDecorator abstract class**

  ```java
  // 다양한 추가 기능에 대한 공통 클래스
  public class DisplayDecorator extends Display {
  
    private Display decoratedDisplay;
  
    public DisplayDecorator(Display decoratedDisplay) {
      this.decoratedDisplay = decoratedDisplay;
    }
  
    @Override
    public void draw() {
      decoratedDisplay.draw();
    }
  
  }
  ```

* **LaneDecorator class**

  ```java
  // 차선 표시를 추가하는 클래스
  public class LaneDecorator extends DisplayDecorator {
  
    // 기존 표시 클래스의 설정
    public LaneDecorator(Display decoratedDisplay) {
      super(decoratedDisplay);
    }
  
    @Override
    public void draw() {
      super.draw();      // 설정된 기존 표시 기능을 수행
      drawLane();        // 추가적으로 차선을 표시
    }
  
    // 교통량 표시를 추가하는 클래스
    private void drawLane() {
      // 기존 표시 클래스의 설정
      System.out.println("\t차선 표시");
    }
  
  }
  ```

* **TrafficDecorator class**

  ```java
  // 교통량 표시를 추가하는 클래스
  public class TrafficDecorator extends DisplayDecorator {
  
    // 기존 표시 클래스의 설정
    public TrafficDecorator(Display decoratedDisplay) {
      super(decoratedDisplay);
    }
  
    public void draw() {
      super.draw();      // 설정된 기존 표시 기능을 수행
      drawTraffic();     // 추가적으로 교통량을 표시
    }
  
    private void drawTraffic() {
      System.out.println("\t교통량 표시");
    }
  
  }
  ```

* **Client class**

  ```java
  public class Client {
  
    public static void main(String[] args) {
      Display road = new RoadDisplay();
      road.draw();  // 기본 도로 표시
  
      Display roadWithLane = new LaneDecorator(new RoadDisplay());
      roadWithLane.draw();  // 기본 도로 표시 + 차선 표시
  
      Display roadWithTraffic = new TrafficDecorator(new RoadDisplay());
      roadWithTraffic.draw();  // 기본 도로 표시 + 교통량 표시
    }
  
  }
  ```

* **실행 결과**

  ```
  기본 도로 표시
  기본 도로 표시
  	차선 표시
  기본 도로 표시
  	교통량 표시
  ```

주목할 점은 road, roadWithLane, roadWithTraffic 객체의 접근이 **모두 Display 클래스를 통해 이루어진다는 것이다.** 

Display 클래스만을 통해 일관성 있는 방식으로 도로 정보를 표시할 수 있다.

<br>

### *roadWithLane 객체의 draw 메서드 동작*

<img src="../../capture/스크린샷 2019-09-29 오후 9.39.05.png">

<br>

### *기본 도로 표시 기능에 추가적으로 차선도 표시하고 교통량도 표시하는 코드*

```java
public class Client {

  public static void main(String[] args) {
    Display roadWithLaneAndTraffic =
        new TrafficDecorator(new LaneDecorator(new RoadDisplay()));
    roadWithLaneAndTraffic.draw();;
  }

}
```

실행 결과

```
기본 도로 표시
	차선 표시
	교통량 표시
```

<br>

### *교차로 표시 기능 추가*

* **CrossingDecorator class**

  ```java
  public class CrossingDecorator extends DisplayDecorator {
  
    public CrossingDecorator(Display decoratedDisplay) {
      super(decoratedDisplay);
    }
  
    public void draw() {
      super.draw();
      drawCrossing();
    }
  
    private void drawCrossing() {
      System.out.println("\t교차로 표시");
    }
  
  }
  ```

* **Client class**

  ```java
  public class Client {
  
    public static void main(String[] args) {
      Display roadWithCrossingAndLaneAndTraffic =
          new LaneDecorator(new TrafficDecorator(
            new CrossingDecorator(new RoadDisplay())));
      roadWithCrossingAndLaneAndTraffic.draw();
    }
  
  }
  ```

* **실행 결과**

  ```
  기본 도로 표시
  	교차로 표시
  	교통량 표시
  	차선 표시
  ```

  <br>

# 10.4. 데커레이터 패턴

**데커레이터 패턴(Decorator Pattern)은** 기본 기능에 추가할 수 있는 기능의 종류가 많은 경우에 **각 추가 기능을 Decorator 클래스로 정의한 후 필요한 Decorator 객체를 조합함으로써 추가 기능의 조합을 설계하는 방식이다.**

<br>

### *프로그램 인자를 통해 명시된 추가 기능을 동적으로 생성하는 예*

* **Client class**

  ```java
  public class Client {
  
    public static void main(String[] args) {
      Display road = new RoadDisplay();
      for (String decoratorName : args) {
        if (decoratorName.equalsIgnoreCase("Lane"))
          road = new LaneDecorator(road);     // 차선 표시 기능을 동적으로 추가
        if (decoratorName.equalsIgnoreCase("Traffic"))
          road = new TrafficDecorator(road);  // 교통량 표시 기능을 동적으로 추가
        if (decoratorName.equalsIgnoreCase("Crossing"))
          road = new CrossingDecorator(road); // 교차로 표시 기능을 동적으로 추가
      }
      road.draw();
    }
  
  }
  ```

  * 주어진 인자에 따라 생성된 Decorator 객체를 바탕으로 draw 메서드가 실행된 결과를 보여준다.

<br>

### *데커레이터 패턴의 컬레보레이션*

<img src="../../capture/스크린샷 2019-09-29 오후 10.09.34.png">

* **Component** : 기본 기능을 뜻하는 ConcreteComponent와 추가 기능을 뜻하는 Decorator의 공통 기능을 정의한다.
* **ConcreteComponent** : 기본 기능을 구현하는 클래스다.
* **Decorator** : 많은 수가 존재하는 구체적인 Decorator의 공통 기능을 제공한다.
* **ConcreteDecoratorA, ConcreteDecoratorB** : Decorator의 하위 클래스로 기본 기능에 추가되는 개별적인 기능을 뜻한다.