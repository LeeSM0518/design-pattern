# SOLID 원칙



# 3.1. 단일 책임 원칙

* **SRP(Single Responsibility Principle)** : 소프트웨어 설계 첫 번째 원칙으로 단일 책임 원칙이다. 간단하게 단 하나의 책임만을 가져야 한다는 의미이다.



## 3.1.1. 책임의 의미

* **책임** : 객체를 지칭한다.
  * 즉, 객체는 **단 하나의 책임만을 가져야 한다.**
  * 해야 하는것
  * 할 수 있는 것
  * 해야 하는 것을 잘 할 수 있는 것



* **예시) 학생 클래스**

  ```java
  public class Student {
    public void getCourses(){...}
    public void addCourse(Course c){...}
    public void save(){...}
    public Student load(){...}
    ...
  }
  ```

  * Student 클래스 안에 데이터베이스에 객체 정보를 저장하거나 읽는 작업과, 수강 과목을 추가하고 조회하는 기능 등이 정의되어 있다. 
  * 이 Student 클래스는 **너무나 많은 책임을 수행해야 한다.**
  * Student 클래스가 가장 잘할 수 있는 것은 수강 과목을 추가하고 조회하는 일 이므로 나머지 책임들은 따로 클래스로 빼는 것이 좋다.



## 3.1.2. 변경

SRP를 따르는 실효성 있는 설계가 되려면 **책임을 좀 더 현실적인 개념으로 파악할 필요가 있다.**



> **책임 = 변경 이유**



책임을 많이 질수록 클래스 내부에서 서로 다른 역할을 수행하는 코드끼리 강하게 결합될 가능성이 높다.



## 3.1.3. 책임 분리

한 클래스에서 여러 책임을 수행하면 그 클래스의 도움을 필요로 하는 코드도 많을 수 밖에 없다.

 그러면 변경사항이 생기면 여러 책임을 수행하는 클래스를 직접 또는 간접적으로 사용하는 모든 코드를 다시 테스트해야한다.

* 어떤 변화가 있을 때 해당 변화가 기존 시스템의 영향을 주는지 평가하는 테스트를 **회귀(regression) 테스트라** 한다. 효율적인 회귀 테스트를 하기 위해서는 변경사항에 대해 유지 보수 할때, **영향을 받는 부분을 적게 하는 것이다.**



* **책임 분리 = 단 하나의 책임만 수행하도록 함**



* **예시) 학생 클래스**

  *  학생 클래스의 책임을 나눈다.

  <img src="../../capture/스크린샷 2019-08-05 오후 9.19.32.png" width=500>

  > **학생 ADO** : 학생 인스턴스를 데이터베이스에 저장하거나 읽어들이는 역할
  >
  > **성적표, 출석부** : 출력을 담당한다.



## 3.1.4. 산탄총 수술

**산탄총 수술(shotgun surgery)** : 하나의 총알에 여러 개의 산탄이 들어 있어 총을 쏘면 산탄이 사방으로 퍼지면서 날아가는 현상. 소프트웨어적으로 설명하자면, <u>어떤 변경이 있을 때 하나가 아닌 여러 클래스를 변경해야 한다는 점에 착안.</u>

* **해결 방안** : 부가 기능을 별개의 클래스로 분리해 책임을 담당하게 하는 것이다. <u>즉, 여러 곳에 흩어진 공통 책임을 한 곳에 모으면서 응집도를 높인다.</u>



## 3.1.5. 관심지향 프로그래밍과 횡단 관심 문제

* **관심지향 프로그래밍(Aspect-Oriented Programming, AOP)** : 횡단 관심을 수행하는 코드를 애스펙트(aspect)라는 특별한 객체로 모듈화하고 위빙(weaving)이라는 작업을 통해 모듈화한 코드를 핵심 기능에 끼워넣을 수 있도록 한다. 이를 통해 <u>기존의 코드를 전혀 변경하지 않고도 시스템 핵심 기능에서 필요한 부가 기능을 효과적으로 이용할 수 있다.</u>
* **횡단 관심(cross-cutting concern)** : 하나의 책임이 여러 개의 클래스로 분리되어 있는 구조. 예) 로깅, 보안, 트랜잭션



한 클래스로 하여금 단일 책임을 갖게 하는 SRP에 따른 설계를 하면 **응집도는 높아지고 더불어 결합도는 낮아진다.**

* **높은 응집도** => 재사용과 유지 보수가 쉬워진다.
* **낮은 결합도** => 한 클래스와 연관된 부분들이 적어 요구사항을 받았을 때, 코드 변경을 거의 하지 않아도 되고 회귀 테스트를 적게 해도 된다.



* **AOP 관련 용어들**

| 용어       | 내용                                                         |
| ---------- | ------------------------------------------------------------ |
| 조인포인트 | 애플리케이션 실행 중의 특정한 지점을 의미한다. ex) 메소드 호출, 실행과 클래스 초기화, 객체 생성 시점 |
| 어드바이스 | 특정 조인포인트에 실행하는 코드                              |
| 포인트컷   | 여러 조인포인트의 집합체로, 언제 어드바이스를 실행할지 정의할 때 사용 |
| 애스펙트   | 어드바이스와 포인트컷을 조합한 조합물. 즉, 애플리케이션의 로직과 실행 지점 |
| 위빙       | 애플리케이션 코드의 해당 지점에 애스펙트를 실제로 주입하는 과정 |



# 3.2. 개방-폐쇄 원칙

**개방-폐쇄 원칙(Open-Closed Principle, OCP)** : 기존의 코드를 변경하지 않으면서 기능을 추가할 수 있도록 설계가 되어야 한다는 뜻이다.



* **예시) 성적표나 출석부에 학생을 출력하는 기능을 사용**

  <img src="../../capture/스크린샷 2019-08-05 오후 9.51.52.png" width=500>

  > 이 설계는 OCP를 위반한다. OCP를 위반하지 않은 설계를 할 때 가장 중요한 것은 무엇이 변하는 것인지, 무엇이 변하지 않는 것인지를 구분해야 한다.  **변해야 하는 것은 쉽게 변할 수 있게 하고, 변하지 않아야 할 것은 변하는 것에 영향을 받지 않게 해야 한다.**



* **OCP를 만족하는 설계**

  <img src="../../capture/스크린샷 2019-08-05 오후 9.58.42.png" width=650>

  > 새로운 출력 매체를 표현하는 클래스를 추가하게 하고 이러한 변경이 있더라도 기존의 클래스(SomeClient 클래스)가 영향을 받지 않게 하려면 **인터페이스에서 구체적인 출력 매체를 캡슐화해 처리하도록 해야 한다.**



* **클래스 = 변화의 단위**



OCP를 보는 또 하나의 관점은 **클래스를 변경하지 않고도 대상 클래스의 환경을 변경할 수 있는** 설계가 되어야 한다는 것이다. 이는 특히 **단위 테스트를** 수행할 때 매우 중요하다.

* **예시) 상속을 통한 다형성으로 다른 클래스에게 영향을 주지 않고 동작을 확장할 수 있도록 한다.**

  <img src="../../capture/스크린샷 2019-08-05 오후 10.11.15.png" width=600>

  

* **단위 테스트 = 빠른 테스트**

* **모의 객체 = 테스트용 가짜 객체**



* **예제) 로켓의 연로 탱크를 검사하는 클래스를 OCP를 이용해 구현**

  변경 전

  ```java
  public class FuelTankMonitoring. {
    ...
    public void checkAndWarn() {
      ...
      if (checkFuelTank(...)) {
        giveWarningSignal(...);
      }
    }
    private boolean checkFuelTank(...) {...}
    private void giveWarningSignal(...) {...}
  }
  ```

  변경 후

  ```java
  public class FuelTankMonitoring {
    ...
    public void checkAndWarn() {
      ...
      if (checkFuelTank(...)) {
        giveWarningSignal(...);
      }
    }
    protected boolean checkFuelTank(...){...}   // default 방식
    protected void giveWarningSignal(...){...} 	// default 방식
  }
  ```

  > checkFuelTank 와 giveWarningSignal 메소드들을 protected 접근 제어자로 변경하여 자식 클래스에서 사용할 수 있도록 해준다.



* **모의 객체의 테스트 더블**

| 용어          | 내용                                                         |
| ------------- | ------------------------------------------------------------ |
| 더미 객체     | 더미 객체의 메소드가 호출되는 경우에는 정상 동작을 실행하지 않고 예외가 발생한다. |
| 테스트 스텁   | 객체의 특정 상태를 가정해서 작성하며 특정한 값을 반환하거나 특정한 메시지를 출력하게 한다. |
| 테스트 스파이 | 주로 테스트 대상 클래스가 의존하는 클래스로 출력을 검증하는데 사용. |
| 가짜 객체     | 실제 의존 클래스의 기능 중 전체나 일부를 훨씬 단순하게 구현한다. |
| 목 객체       | 미리 정의한 기대 값과 실제 호출을 단언문으로 비교해 문제가 있으면 테스트 메소드를 대신해 모의 객체가 테스트를 실패하게 한다. |

* **예제) MP3 클래스를 OCP를 적용해 문제를 해결하는 코드 작성**

  * 해결 전

  ```java
  import java.util.Calendar;
  
  public class TimeReminder {
    private MP3 m;
    
    public void reminder() {
      Calendar calendar = Calendar.getInstance();
      m = new MP3();
      int hour = calendar.get(Calendar.HOUR_OF_DAY);
      
      if (hour >= 22) {
        m.playSong();
      }
    } 
  }
  ```

  * 해결 후
  
  <img src="../../capture/스크린샷 2019-08-07 오전 9.03.21.png" width=500>
  
  **TimeProvider 인터페이스**
  
  ```java
  package solid_rule;
  
  public interface TimeProvider {
  
    public void setHours(int hours);
    public int getTime();
  
  }
  ```
  
  **FakeTimeProvider 클래스**
  
  ```java
  package solid_rule;
  
  import java.util.Calendar;
  
  // 테스트 스텁
  public class FakeTimeProvider implements TimeProvider {
  
    private Calendar calendar;
  
    public FakeTimeProvider() {
      this.calendar = Calendar.getInstance();
    }
  
    public FakeTimeProvider(int hours) {
      this.calendar = Calendar.getInstance();
      setHours(hours);
    }
  
    @Override
    public void setHours(int hours) {
      this.calendar.set(Calendar.HOUR_OF_DAY, hours);
    }
  
    @Override
    public int getTime() {
      return calendar.get(Calendar.HOUR_OF_DAY);
    }
  
  }
  ```
  
  **TimeReminder 클래스**
  
  ```java
  package solid_rule;
  
  public class TimeReminder {
  
    TimeProvider timeProvider;
    MP3 mp3 = new MP3();
  
    public void setTimeProvider(TimeProvider timeProvider) {
      this.timeProvider = timeProvider;
    }
  
    public void reminder() {
      int hours = timeProvider.getTime();
      if (hours >= 22) {
        mp3.playSong();
      }
    }
  
  }
  ```
  
  > 