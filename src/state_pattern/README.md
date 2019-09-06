# 7. 스테이트 패턴

# 7.1. 상태 머신 다이어그램

실세계의 많은 시스템은 다양한 상태가 있고 상태에 따라 다른 행위를 한다.

UML에서 이러한 상태와 상태 변화를 모델링하는 도구로 **상태 머신 다이어그램(State Machine Diagram)이** 있다. 

* **선풍기 상태 머신 다이어그램**

  <img src="https://camo.githubusercontent.com/8dc105ba96354c9ab640e7f46b115a56dae2f1fd/687474703a2f2f7777772e706c616e74756d6c2e636f6d2f706c616e74756d6c2f706e672f597a51414c54306a425432724b5f3174532d43324f574e54656970715a31414757392d32624b396e554d50395361507956627671474e76765162357951614c63534b636957687632494d62484e64775558594f4e4b39626b4a6366513253473839327266514a3162514d624754496476764e62305455372d47547755566b76306a4b4d62554b31352d474d66484f6162674b313543316454713032753271686f3275316759616b42327242704b66334c3436346830303030">

  * 모서리가 둥근 사각형을 **상태(state)를** 나타내고, 상태 사이에 화살표를 사용해 상태 **전이(state transition)를** 나타낸다.
  * **상태란** 객체가 시스템에 존재하는 동안, 즉 객체의 **라이프 타임 동안 객체가 가질 수 있는 어떤 조건이나 상황을 표현한다.**

<br>

* **UML 상태 머신 다이어그램의 의사 상태(pseudo state)**
  * **시작(initial)** : 검은 동그라미
  * **종료(final)** 
  * **히스토리(history)**
  * **선택(choice)**
  * **교차(junction)**
  * **포크(fork)**
  * **조인(join)**
  * **진입점(entry point)**
  * **진출점(exit point)**

<br>

* **이벤트** : <u>'이벤트 (인자 리스트)[조건] / 액션'</u> 으로 명세할 수 있다. '/' 다음에 진입이 이루어지면서 수행되어야 하는 액션을 기술한다.

<br>

# 7.2. 형광등 만들기

* **형광등의 상태 머신 다이어그램**

  <img src="https://camo.githubusercontent.com/4ae9a4dc12c24dbae01a7afc20044b7b374d7f6c/687474703a2f2f7777772e706c616e74756d6c2e636f6d2f706c616e74756d6c2f706e672f597a51414c54306a425432724b5f3174532d43324f5748466d4b6558466f716a466763716632475f427877576a33575a44474d5938594d5f46302d616e572d6139307a5458487958387530415345613630303030">

  * OFF와 ON은 형광등의 상태를 나타내고 off_button_pushed와 on_button_pushed는 각각 형광등 Off 버튼과 On 버튼이 눌러졌음을 나타낸다.

<br>

* **형광등의 상태를 표현하는 상수**

  ```java
  private static final int ON = 0;
  private static final int OFF = 1;
  ```

* **형광등의 상태를 저장하는 변수**

  ```java
  private int state;
  ```

<br>

이제 형광등에 있을 수 있는 모든 행위와 그에 따른 각 상태에 맞도록 프로그램 코드를 작성한다.

* **Light 클래스**

  ```java
  package state_pattern;
  
  public class Light {
  
    private static final int ON = 0;    // 형광등이 켜진 상태
    private static final int OFF = 1;   // 형광등이 꺼진 상태
    private int state;                  // 형광등의 현재 상태
  
    public Light() {
      state = OFF;    // 형광등 초기 상태는 꺼져 있는 상태이다.
    }
  
    public void onButtonPushed() {
      if (state == ON) {
        System.out.println("반응 없음");
      } else {  // 형광등이 꺼져 있을 때 On 버튼을 누르면 켜진 상태로 전환됨
        System.out.println("Light On!");
        state = ON;
      }
    }
  
    public void offButtonPushed() {
      if (state == OFF) {
        System.out.println("반응 없음");
      } else {  // 형광등이 켜져 있을 때 Off 버튼을 누르면 꺼진 상태로 전환됨
        System.out.println("Light Off!");
        state = OFF;
      }
    }
  
  }
  ```

* **Client 클래스**

  ```java
  package state_pattern;
  
  public class Client {
  
    public static void main(String[] args) {
      Light light = new Light();
      light.onButtonPushed();
      light.offButtonPushed();
    }
  
  }
  ```

<br>

# 7.3. 문제점

* 형광등에 새로운 상태를 추가할 때, 가령 형광등에 '취침등' 상태를 추가하려면?

<br>

* **'취침등' 상태를 추가한 상태 머신 다이어그램**

  <img src="https://camo.githubusercontent.com/7603fa9c94d5957952e8a26431e43a1fe4d32547/687474703a2f2f7777772e706c616e74756d6c2e636f6d2f706c616e74756d6c2f706e672f597a51414c54306a6f617046414436724b5f3174532d43324f574e54715738384a793541384a796a424a77666a414761466f2d2d6542477538704d35417a5a37724a4e3071792d5451364c6d755f7230326c464772303535544e4449717130417535676d41474576416f4d5f46302d6c4756585331303030">

<br>

1. 우선 취침등 상태를 나타내는 상수인 SLEEPING를 추가한다.

   ```java
   private static final int SLEEPING = 2;
   ```

2. 추가된 취침등 상태 값에서 on_button_pushed 상태와 off_button_pushed 상태를 처리해준다.

   ```java
   public void onButtonPushed() {
     if (state == ON) {  // 형광등이 켜져 있는 경우에 On 버튼을 누르면 취침등 상태로 전환
       System.out.println("취침등 상태");
       state = SLEEPING;
     }  else if (state == SLEEPING) {    // 형광등이 취침등 상태에 있는 경우
       System.out.println("Light On!");  // On 버튼을 누르면 켜진 상태로 전환됨
       state = ON;
     } else {  // 형광등이 꺼져 있을 때 On 버튼을 누르면 켜진 상태로 전환됨
       System.out.println("Light On!");
       state = ON;
     }
   }
   ```

3. 전체 수정 코드

   ```java
   package state_pattern;
   
   public class Light {
   
     private static final int ON = 0;    // 형광등이 켜진 상태
     private static final int OFF = 1;   // 형광등이 꺼진 상태
     private static final int SLEEPING = 2;
     private int state;                  // 형광등의 현재 상태
   
     public Light() {
       state = OFF;    // 형광등 초기 상태는 꺼져 있는 상태이다.
     }
   
     public void onButtonPushed() {
       if (state == ON) {  // 형광등이 켜져 있는 경우에 On 버튼을 누르면 취침등 상태로 전환
         System.out.println("취침등 상태");
         state = SLEEPING;
       }  else if (state == SLEEPING) {    // 형광등이 취침등 상태에 있는 경우
         System.out.println("Light On!");  // On 버튼을 누르면 켜진 상태로 전환됨
         state = ON;
       } else {  // 형광등이 꺼져 있을 때 On 버튼을 누르면 켜진 상태로 전환됨
         System.out.println("Light On!");
         state = ON;
       }
     }
   
     public void offButtonPushed() {
       if (state == OFF) { // 형광등이 꺼져 있는 경우에 OFF 버튼을 누르면 상태 전환 없음
         System.out.println("반응 없음"); 
       } else if (state == SLEEPING) {       // 형광등이 취침등 상태에 있는 경우
         System.out.println("Light OFF!");   // Off 버튼을 누르면 OFF로 전환
         state = OFF;
       } else {  // 형광등이 켜져 있을 때 Off 버튼을 누르면 꺼진 상태로 전환됨
         System.out.println("Light Off!");
         state = OFF;
       }
     }
   
   }
   ```

위와 같이 복잡한 조건문에 상태 변화가 숨어 있는 경우 상태 변화가 어떻게 이루어지는지 **이해하기 어렵고 새로운 상태 추가에 맞춰 모든 메서드를 수정해야 한다.**

<br>

# 7.4. 해결책

**변하는 부분을 찾아서 이를 캡슐화하여,** 현재 시스템이 어떤 상태에 있는지와 상관없게 구성하고 상태 변화에도 독립적이도록 코드를 수정해야 한다.

이를 위해서는 **상태를 클래스로 분리해 캡슐화하도록 한다.** 또한 **상태에 의존적인 행위들도 상태 클래스에 같이 두어** 특정 상태에 따른 행위를 구현하도록 바꾼다.

<br>

* **스테이트 패턴으로 구현한 형광등 상태 머신 다이어그램**

  <img src="../../capture/스크린샷 2019-09-05 오후 5.26.05.png">

  * **Light 클래스에서 구체적인 상태 클래스가 아닌 추상화된 State 인터페이스만 참조하므로** 현재 어떤 상태에 있는지와 무관하게 코드를 작성할 수 있다.

<br>

**스테이트 패턴을 적용한 코드**

* **State Interface**

  ```java
  public interface State {
  
    public void onButtonPushed(Light light);
    public void offButtonPushed(Light light);
  
  }
  ```

* **ON Class**

  ```java
  public class ON implements State {
  
    @Override
    public void onButtonPushed(Light light) {
      System.out.println("반응 없음");
    }
  
    @Override
    public void offButtonPushed(Light light) {
      System.out.println("Light Off!");
      light.setState(new OFF());
    }
  
  }
  ```

* **OFF Class**

  ```java
  public class OFF implements State {
  
    @Override
    public void onButtonPushed(Light light) {
      System.out.println("Light On!");
      light.setState(new ON());
    }
  
    @Override
    public void offButtonPushed(Light light) {
      System.out.println("반응 없음");
    }
  
  }
  ```

* **Light Class**

  ```java
  public class Light {
  
    private State state;  // 형광등의 현재 상태
  
    public Light() {
      state = new OFF();
    }
  
    public void setState(State state) {
      this.state = state;
    }
  
    public void onButtonPushed() {
      state.onButtonPushed(this);
    }
  
    public void offButtonPushed() {
      state.offButtonPushed(this);
    }
  
  }
  ```

이로써, **Light 클래스 코드는 구체적인 상태를 나타내는 객체를 참조하지 않는다.** 즉, Light 클래스는 시스템이 어떤 상태에 있는지와 무관한 상태가 된다.

하지만 이 코드는 상태 변화가 생길 때마다 새로운 상태 객체를 생성하므로 **메모리 낭비와 성능 저하를 가져올 수 있기 때문에** 개선할 필요가 있다.

<br>

**싱글턴 패턴을 적용한 코드**

* **ON Class**

  ```java
  public class ON implements State {
    
    private static ON on = new ON();    // ON 클래스의 인스턴스로 초기화됨
    private ON() {}
    
    public static ON getInstance() {    // 초기화된 ON 클래스의 인스턴스로 반환
      return on;
    }
    
    @Override
    public void onButtonPushed(Light light) {
      System.out.println("반응 없음");
    }
  
    @Override
    public void offButtonPushed(Light light) {
      System.out.println("Light Off!");
      light.setState(new OFF());
    }
  
  }
  ```

* **OFF Class**

  ```java
  public class OFF implements State {
  
    private static OFF off = new OFF();   // OFF 클래스의 인스턴스로 초기화됨
    private OFF() {}
  
    public static OFF getInstance() {     // 초기화된 OFF 클래스의 인스턴스를 반환함
      return off;
    }
  
    @Override
    public void onButtonPushed(Light light) {
      System.out.println("Light On!");
      light.setState(new ON());
    }
  
    @Override
    public void offButtonPushed(Light light) {
      System.out.println("반응 없음");
    }
  
  }
  ```

<br>

# 7.5. 스테이트 패턴

**스테이트 패턴(State Pattern)은** 어떤 행위를 수행할 때 상태에 행위를 수행하도록 위임한다. 이를 위해 스테이트 패턴에서는 시스템의 **각 상태를 클래스로 분리해 표현하고, 각 클래스에서 수행하는 행위들을 메서드로 구현한다.** 그리고 이러한 상태들을 외부로부터 캡슐화하기 위해 **인터페이스를 만들어 시스템의 각 상태를 나타내는 클래스로 하여금 실체화하게 한다.**

* **스테이트 패턴의 컬레보레이션**

  <img src="../../capture/스크린샷 2019-09-05 오후 6.49.52.png">

  * **State** : 시스템의 모든 상태에 공통의 인터페이스를 제공한다. 따라서 이 인터페이스를 실체화한 어떤 상태 클래스도 기존 상태 클래스를 대신해 교체해서 사용할 수 있다.
  * **State1, State2** : Context 객체가 요청한 작업을 자신의 방식으로 실제 실행한다. 대부분의 경우 다음 상태를 결정해 상태 변경을 Context 객체에 요청하는 역할도 수행한다.
  * **Context** : State를 이용하는 역할을 수행한다. 현재 시스템의 상태를 나타내는 상태 변수(state)와 실제 시스템의 상태를 구성하는 여러 가지 변수가 있다. 또한 각 상태 클래스에서 상태 변경을 요청해 상태를 바꿀 수 있도록 하는 메서드(setState)가 제공된다. Context 요소를 구현한 클래스의 request 메서드는 실제 행위를 실행하는 대신 해당 상태 객체에 행위 실행을 위임한다.